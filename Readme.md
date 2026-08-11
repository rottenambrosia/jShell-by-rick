# Java Interactive Shell

<p align="left">
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img src="https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black" />
  <img src="https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white" />
</p>

```text
                      _         
   ___ _ __ _   _ ___| |_ __ _  
  / __| '__| | | / __| __/ _` | 
 | (__| |  | |_| \__ \ || (_| | 
  \___|_|   \__,_|___/\__\__,_| 
                                                
```

This is a lightweight, interactive command-line shell written entirely in Java. It provides a custom Read-Eval-Print Loop (REPL) that executes core shell built-ins, manages environment state, and dynamically resolves external binaries from the system's `PATH`.

## Features

- Core built-in commands include `cd`, `pwd`, `echo`, `type`, and `exit`.
- Cross-platform executable resolution automatically detects and handles Windows `.exe` extensions.
- External system binaries run seamlessly using Java's `ProcessBuilder` with inherited standard I/O.
- Persistent working directory tracking ensures relative paths and executions work as expected.

## Getting Started

You only need the Java Development Kit (JDK) installed on your system to run this project. Navigate to the project directory in your terminal, compile the source file, and execute the compiled class.

```bash
javac Main.java
java Main
```

## Example Usage

Once the shell is running, it will present a `>` prompt where you can type commands just like a standard Unix or Windows terminal.

```text
> pwd
/home/user/projects/java-shell
> type git
git is /usr/bin/git
> echo System initialized.
System initialized.
> cd src
> pwd
/home/user/projects/java-shell/src
> exit
```

## 🚀 Upcoming Features

The shell is actively being developed to support more advanced POSIX-like capabilities. Planned features include:

### Navigation & Expansion
* **Advanced Directory Navigation:** Support for relative paths (`..`) and home directory expansion (`~`).
* **Parameter Expansion:** Dynamic resolution of environment variables (e.g., `$USER` or `${VAR}`).

### Advanced Execution & I/O
* **Pipelines:** Chaining multiple commands together using the `|` operator.
* **I/O Redirection:** Redirecting standard input, output, and error streams (e.g., `>`, `>>`, `<`).
* **Background Jobs:** Support for executing processes in the background using `&` and managing active jobs.
* **Quoting:** Robust parsing of single (`'`) and double (`"`) quotes to handle strings containing spaces and special characters.

### Interactive Shell Experience
* **Auto-Completion:** Support for command, filename, and programmable tab-completion to speed up navigation.
* **Command History:** In-memory tracking of previously executed commands.
* **History Persistence:** Saving and loading command history across different shell sessions.
