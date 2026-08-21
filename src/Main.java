import java.io.File;
import java.util.Scanner;

public class Main {
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    public static void main(String[] args) throws Exception {

        System.out.println(ANSI_CYAN +
                "                      _         \n" +
                "   ___ _ __ _   _ ___| |_ __ _  \n" +
                "  / __| '__| | | / __| __/ _` | \n" +
                " | (__| |  | |_| \\__ \\ || (_| | \n" +
                "  \\___|_|   \\__,_|___/\\__\\__,_| \n" +
                "                                "
        + ANSI_RESET);
        System.out.println("Crusta Interactive Shell \n" +
                " - rick\n" +
                "Hope you like it. (or something like that)");
        Scanner scanner = new Scanner(System.in);
        String cwd =  System.getProperty("user.dir");
        while (true) {
            System.out.print(ANSI_GREEN + "> " + ANSI_RESET);
            System.out.flush();
            String userInput = scanner.nextLine();
            String[] tokens = userInput.trim().split("\\s+");
            if (userInput.trim().equals("")) {
                continue;
            }
            // command : exit
            if (tokens[0].equals("exit")) {
                exit();
                continue;
            }
            // command : echo
            if (tokens[0].equals("echo")) {
                echo(userInput.substring(5));
                continue;
            }
            // command : type
            if (tokens[0].equals("type")) {
                type(userInput.substring(5), cwd);
                continue;
            }
            // command : pwd -> so you don't get lost in the scary alleyways
            if (tokens[0].equals("pwd")) {
                System.out.println(cwd);
                continue;
            }
            // command : running executables
            if (locateExecutable(tokens[0]) != null) {
                runExecutable(tokens, cwd);
                continue;
            }
            // command : cd
            if (tokens[0].equals("cd")) {
                if(userInput.length() > 2) cwd = changeDirectory(userInput.substring(3), cwd);
                continue;
            }
            System.out.println(tokens[0] + " command not found");
            continue;
        }
    }
    // exit : well...
    private static void exit () {
        System.exit(0);
    }
    // echo : prints out whatever you write after it (thank god this isn't a notebook)
    private static void echo(String command) {
        System.out.println(command);
    }
    // type : lets you know if the command you just conjured happens to actually be anything of value
    private static boolean isBuiltin (String command) {
        String[] keywords = {
                "exit",
                "echo",
                "type",
                "cd",
                "pwd"
        };
        for (String keyword : keywords) {
            if (command.startsWith(keyword)) {
                return true;
            }
        }
        return false;
    }
    // private static File locateExecutable (String command) {
    //     String path = System.getenv("PATH");
    //     String[] pathDir =  path.split(File.pathSeparator);
    //     for (String dir : pathDir) {
    //         File file = new File(dir, command);
    //         if (file.isFile() &&  file.canExecute()) {
    //             return file;
    //         }
    //         // Useful on Windows when user enters, for example, "git"
    //         if (System.getProperty("os.name").toLowerCase().contains("win")) {
    //             File exeFile = new File(dir, command + ".exe");
    //             if (exeFile.isFile()) {
    //                 return exeFile;
    //             }
    //         }
    //     }
    //     return null;
    // }
    // private static void type(String command) {
    //     if (isBuiltin(command)) {
    //         System.out.println(command + " is a shell builtin");
    //     } else if (locateExecutable(command) != null) {
    //         System.out.println(command + " is " + locateExecutable(command).getAbsolutePath());
    //     } else {
    //         System.out.println(command + " : not found");
    //     }
    //     return;
    // }

        private static File locateExecutable(String command) {
        return locateExecutable(command, System.getProperty("user.dir"));
    }

    private static File locateExecutable(String command, String cwd) {
        if (command == null || command.isBlank()) return null;
        File f = new File(command);
        // Absolute path provided
        if (f.isAbsolute()) {
            if (f.isFile() && f.canExecute()) return f;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                File exe = new File(command + ".exe");
                if (exe.isFile()) return exe;
                File bat = new File(command + ".bat");
                if (bat.isFile()) return bat;
                File cmd = new File(command + ".cmd");
                if (cmd.isFile()) return cmd;
            }
            return null;
        }
        // Relative path (contains separator or starts with dot)
        if (command.contains(File.separator) || command.startsWith(".")) {
            File rel = new File(cwd, command);
            if (rel.isFile() && rel.canExecute()) return rel;
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                File exe = new File(rel.getPath() + ".exe");
                if (exe.isFile()) return exe;
                File bat = new File(rel.getPath() + ".bat");
                if (bat.isFile()) return bat;
                File cmd = new File(rel.getPath() + ".cmd");
                if (cmd.isFile()) return cmd;
            }
            return null;
        }
        // Otherwise search PATH
        String path = System.getenv("PATH");
        if (path == null) return null;
        String[] pathDir = path.split(File.pathSeparator);
        for (String dir : pathDir) {
            File file = new File(dir, command);
            if (file.isFile() && file.canExecute()) {
                return file;
            }
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                File exeFile = new File(dir, command + ".exe");
                if (exeFile.isFile()) return exeFile;
                File bat = new File(dir, command + ".bat");
                if (bat.isFile()) return bat;
                File cmd = new File(dir, command + ".cmd");
                if (cmd.isFile()) return cmd;
            }
        }
        return null;
    }
    private static void type(String command, String cwd) {
        if (isBuiltin(command)) {
            System.out.println(command + " is a shell builtin");
        } else {
            File f = locateExecutable(command, cwd);
            if (f != null) {
                System.out.println(command + " is " + f.getAbsolutePath());
            } else {
                System.out.println(command + " : not found");
            }
        }
        return;
    }
    // executable : well I mean otherwise what's the point of all this bs
    private static void runExecutable(String[] command, String dir) throws Exception {
        if (locateExecutable(command[0]) != null) {
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.directory(new File(dir));
            pb.redirectErrorStream(true);
            pb.inheritIO();
            Process process = pb.start();
            process.waitFor();
        }
        return;
    }
    // cd : change directory
    private static String changeDirectory(String command, String dir) throws Exception {
        if (command.isBlank()) {
            return dir;
        }
        else if (command.equals("~")) {
            return System.getProperty("user.home");
        }
        File directory = new File(command).isAbsolute() ? new File(command) : new File(dir, command);
        if (directory.isDirectory()) {
            return directory.getCanonicalPath();
        }
        System.out.println(command + ": No such file or directory");
        return dir;
    }
}