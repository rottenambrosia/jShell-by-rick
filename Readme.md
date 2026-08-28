# Java Interactive Shell

<p align="center">
  <img alt="Java" src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <img alt="Platform" src="https://img.shields.io/badge/Cross--Platform-Linux%20%7C%20Windows-2BAF6F?style=for-the-badge" />
</p>

```
   _ _____ _       _ _ 
  |_|   __| |_ ___| | |
  | |__   |   | -_| | |
 _| |_____|_|_|___|_|_|
|___|                  
```

A compact, educational interactive shell implemented in plain Java. It provides a minimal Read–Eval–Print Loop (REPL), a handful of core builtins, and the ability to resolve and run external executables from the system PATH. The project is intentionally small to serve as a clean foundation for experimenting with shell features (parsing, pipelines, redirection, job control, etc.).

Source: [src/Main.java](D:/jShell.worktrees/stylish-readme-with-features/src/Main.java)

---

## Implemented features

- REPL-style prompt (`>`) that reads user input in a loop.
- Builtin commands:
  - `exit` — quit the shell.
  - `echo <text>` — print text back to the console.
  - `type <command>` — report whether a token is a shell builtin or show the absolute path of an executable found in PATH.
  - `pwd` — print the current working directory.
  - `cd <path>` — change the current working directory (supports absolute and relative paths).
- Resolves external binaries by scanning `PATH` and supports Windows `.exe` discovery.
- Runs external commands via Java's `ProcessBuilder` with `inheritIO()` so child processes use the same terminal I/O.
- Keeps and updates an internal `cwd` string so subsequent commands run with the expected working directory.

## Quick start

Requirements
- Java JDK 8 or newer installed and available on PATH.

Build and run

From the project root:

```bash
cd src
javac Main.java
java Main
```

The shell shows the `>` prompt. Try commands like `pwd`, `echo Hello`, `type java`, `cd ..`, and `exit`.

## Example session

```
> pwd
C:\Users\you\projects\java-shell
> type git
git is C:\Program Files\Git\cmd\git.exe
> echo System initialized.
System initialized.
> cd src
> pwd
C:\Users\you\projects\java-shell\src
> exit
```

## Design & implementation notes

- The main REPL lives in `src/Main.java` and uses `Scanner` to read lines from standard input.
- Executable resolution reads `System.getenv("PATH")`, splits on `File.pathSeparator`, and checks each directory for the requested command file. On Windows the code also checks for `command + ".exe"`.
- External commands are executed with `ProcessBuilder`, configured with the current working directory and `inheritIO()` so interactive programs behave correctly.
- Builtin detection is currently implemented with a simple string-based lookup and the `type` builtin reuses the same resolution logic.

## Features yet to be implemented (Roadmap)

This project is intentionally minimal. The following features are planned or recommended next steps. They are listed roughly in priority order for practical incremental development:

Parsing & argument handling
- Quote-aware tokenization and escaping (support single and double quotes, escaped characters).
- Correct handling of arguments containing spaces and special characters.

I/O & process control
- Pipelines (`|`) to connect stdout of one command to stdin of the next.
- I/O redirection (`>`, `>>`, `<`, `2>`) for file-based input/output.
- Background jobs (`&`) and basic job control (jobs, fg, bg).
- Proper handling of process exit codes and chaining (`;`, `&&`, `||`).

Shell ergonomics
- Command history and persistent history file across sessions.
- Tab completion for commands and file names (e.g., via JLine integration).
- Config file support (e.g., `~/.jshrc`) and aliases.

Shell features & expansions
- Environment variable operations (export, set/unset) and parameter expansion (`$VAR`, `${VAR}`).
- Tilde expansion (`~` → home directory) and globbing (`*.txt`).
- Command substitution (`$(...)` / backticks).

Robustness & tooling
- Improved error messages, input validation, and edge-case handling.
- Unit tests for tokenization and parsing, and integration tests for execution behavior.
- CI configuration (e.g., GitHub Actions) and packaging instructions.

Security and safety
- Consider sandboxing or safe execution modes if untrusted commands will be used.

## Recommended next steps for contributors

1. Implement a quote-aware tokenizer and unit tests for it.
2. Add a small parser that supports redirection tokens and simple pipelines.
3. Implement piping by wiring `Process` streams together.
4. Add command history (start with an in-memory list, then persist to a file).

If you want help with any of these (design, code sketch, or implementation), open an issue or a pull request and include a short description of the intended approach.

## Contribution

Contributions are welcome. Suggested workflow:
- Fork the repository and create a feature branch for each change.
- Keep changes small and focused (e.g., tokenizer, pipeline, history).
- Add tests where applicable.
- Open a PR describing the change and why it helps the project.

## License

This repository currently has no LICENSE file. Add a LICENSE (for example MIT or Apache-2.0) to make the usage terms explicit.

---

Made with care — a small, practical shell to learn from and extend.
