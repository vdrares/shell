package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static final String PRINT_WORKING_DIRECTORY_COMMAND = "pwd";
    private static final String CHANGE_DIRECTORY_COMMAND = "cd";
    private static final String HELP_COMMAND = "help";
    private static final String EXIT_COMMAND = "exit";

    private static final List<String> AVAILABLE_COMMANDS = Arrays.asList(
            PRINT_WORKING_DIRECTORY_COMMAND,
            CHANGE_DIRECTORY_COMMAND,
            HELP_COMMAND,
            EXIT_COMMAND);

    private static final String CD_DESCRIPTION = "Usage: cd <dirname>\nDescription: change working directory";
    private static final String PWD_DESCRIPTION = "Usage: pwd\nDescription: print working directory";
    private static final String HELP_DESCRIPTION = "Usage: help [command1] [command2] ...\nDescription: prints the help information for the commands given as parameters";
    private static final String EXIT_DESCRIPTION = "Usage: exit\nDescription: exits the program";

    private static String currentPath = "/";

    public static void main(String[] args) {
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        while (!input.equals(EXIT_COMMAND)) {
            System.out.print("$ ");
            try {
                input = consoleReader.readLine();
                if (!input.equals(EXIT_COMMAND)) {
                    processCommand(input);
                }
            } catch (IOException e) {
                input = EXIT_COMMAND;
                e.printStackTrace();
            }
        }
    }

    private static void processCommand(String input) {
        String[] parameters = input.split(" ");
        if (AVAILABLE_COMMANDS.contains(parameters[0])) {
            switch (parameters[0]) {
                case CHANGE_DIRECTORY_COMMAND -> processChangeDirCommand(parameters);
                case PRINT_WORKING_DIRECTORY_COMMAND -> processPWDCommand(parameters);
                case HELP_COMMAND -> processHelpCommand(parameters);
                case EXIT_COMMAND -> processExitCommand(parameters);
            }
        } else {
            System.out.println("Unknown command: " + input);
        }
    }

    private static void processExitCommand(String[] parameters) {
        if (parameters.length > 1) {
            promptInvalidParametersMessage(parameters);
        }
    }

    private static void processHelpCommand(String[] parameters) {
        for (int i = 1; i < parameters.length; i++) {
            switch (parameters[i]) {
                case CHANGE_DIRECTORY_COMMAND -> System.out.println(CD_DESCRIPTION);
                case PRINT_WORKING_DIRECTORY_COMMAND -> System.out.println(PWD_DESCRIPTION);
                case HELP_COMMAND -> System.out.println(HELP_DESCRIPTION);
                case EXIT_COMMAND -> System.out.println(EXIT_DESCRIPTION);
                default -> System.out.println("Unknown command: " + parameters[i]);
            }
        }
    }

    private static void processPWDCommand(String[] parameters) {
        if (parameters.length > 1) {
            promptInvalidParametersMessage(parameters);
        } else {
            System.out.println(currentPath);
        }
    }

    private static void processChangeDirCommand(String[] parameters) {
        if (parameters.length > 2) {
            promptInvalidParametersMessage(parameters);
        } else {
            currentPath = parameters[1];
        }
    }

    private static void promptInvalidParametersMessage(String[] parameters) {
        String errorMsg = "Invalid parameters ";
        for (int i = 1; i < parameters.length; i++) {
            errorMsg = errorMsg.concat("'" + parameters[i] + "' ");
        }
        System.out.println(errorMsg);
    }
}
