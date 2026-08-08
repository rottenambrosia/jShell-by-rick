import java.util.Scanner;
import java.io.File;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String cwd =  System.getProperty("user.dir");
        while (true) {
            System.out.println("> ");
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
                echo(userInput);
                continue;
            }
            // type
            if (tokens[0].equals("type")) {
                type(userInput.substring(5));
                continue;
            }

            System.out.println(tokens[0] + " command not found");
            return;
        }
    }


    // exit : well...
    private static void exit () {
        System.exit(0);
    }
    // echo : prints out whatever you write after it (thank god this isn't a notebook)
    private static void echo(String command) {
        System.out.println(command.trim().substring(5));
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
    private static File locateExecutable (String command) {
        String path = System.getenv("PATH");
        String[] pathDir =  path.split(";");
        for (String dir : pathDir) {
            File file = new File(dir, command);
            if (file.exists()) {
                return file;
            }
        }
        return null;
    }
    private static void type(String command) {
        if (isBuiltin(command)) {
            System.out.println(command + " is a shell builtin");
        } else if (locateExecutable(command) != null) {
            System.out.println(command + " is " + locateExecutable(command).getAbsolutePath());
        } else {
            System.out.println(command + " : not found");
        }
        return;
    }
}
