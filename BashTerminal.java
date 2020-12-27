/**
 * @author Luye Ge
 * ID# 111857836
 *
 * Write a fully-documented class named BashTerminal. The class should contain a single main method which allows
 * a user to interact with a file system implemented by an instance of DirectoryTree using the following commands
 * (note that commands are case-sensitive and will always be lower-case):
 * Command
 * Description
 * pwd
 * Print the "present working directory" of the cursor node (e.g root/home/user/Documents).
 * ls
 * List the names of all the child directories or files of the cursor.
 * ls ­R
 * Recursive traversal of the directory tree. Prints the entire tree starting from the cursor in pre-order traversal.
 * cd {dir}
 * Moves the cursor to the child directory with the indicated name (Only consider the direct children of the cursor).
 * cd /
 * Moves the cursor to the root of the tree.
 * mkdir {name}
 * Creates a new directory with the indicated name as a child of the cursor, as long as there is room.
 * touch {name}
 * Creates a new file with the indicated name as a child of the cursor, as long as there is room.
 * exit
 * Terminates the program.
 * It should be noted that these commands should all have the same effect as if you were to execute them in
 * a live bash shell on any Unix-based operating system (linux, mac, etc.). However,
 * the command ls ­R has been modified to make the assignment easier,
 * and you are not expected to move up directories or change directories for
 * absolute and relative paths with the cd command. You are encouraged to try these commands on a live bash terminal
 * if you have access to get a feel for how they should work.
 */
package com.company;

import java.nio.file.NotDirectoryException;
import java.util.Scanner;

public class BashTerminal {
    /**
     * Runs a program which takes user input and builds a DirectoryTree using the commands indicated above.
     * @param args
     * @throws NotDirectoryException
     * @throws FullDirectoryException
     */
    public static void main(String[] args) throws NotDirectoryException, FullDirectoryException {
        System.out.println("Starting bash terminal.");
        DirectoryTree tree = new DirectoryTree("root");
        boolean flag = true;
        while(flag) {

            Scanner input = new Scanner(System.in);
            System.out.print("[LuyeGe_111857836@host]: $ ");
            String userInput = input.nextLine().toLowerCase();
            String[] splitWords = userInput.split("\\s+");
            String keyword = "";
            String keyName = "";
                if(splitWords.length > 1) {
                    if (splitWords[0].toLowerCase().equalsIgnoreCase("ls") &&
                            splitWords[1].toLowerCase().equalsIgnoreCase("-R")) {
                        keyword = "ls-r";
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("cd") &&
                            splitWords[1].toLowerCase().equalsIgnoreCase("..")) {
                        keyword = "cd..";
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("cd") &&
                            splitWords[1].toLowerCase().equalsIgnoreCase("/")) {
                        keyword = "cd/";
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("cd") &&
                            splitWords[1].toLowerCase().contains("root/")) {
                        keyword = "cdpath";
                        keyName = splitWords[1];
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("cd")){
                        keyword = "cd";
                        keyName = splitWords[1].toLowerCase();
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("mkdir")){
                        keyword = "mkdir";
                        keyName = splitWords[1];

                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("touch")){
                        keyword = "touch";
                        keyName = splitWords[1];
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("mv")){
                        keyword = "mv";
                    } else if (splitWords[0].toLowerCase().equalsIgnoreCase("find")){
                        keyword = "find";
                        keyName = splitWords[1];
                    }
                } else if(splitWords.length == 1){
                    if(splitWords[0].toLowerCase().equalsIgnoreCase("pwd")){
                        keyword = "pwd";
                    }else if(splitWords[0].toLowerCase().equalsIgnoreCase("ls")){
                        keyword = "ls";
                    }else if(splitWords[0].toLowerCase().equalsIgnoreCase("exit")){
                        keyword = "exit";
                    }
                }
                try {
                    switch (keyword) {
                        case ("pwd"):
                            System.out.println(tree.presentWorkingDirectory(tree.getCursor()));
                            break;
                        case ("ls"):
                            tree.listDirectory(tree.getCursor());
                            break;
                        case ("ls-r"):
                            tree.printDirectoryTree(tree.getCursor(), -1);
                            break;
                        case ("cd"):
                            tree.changeDirectory(keyName.toLowerCase(), tree.getCursor());
                            break;
                        case ("cd/"):
                            tree.resetCursor();
                            break;
                        case ("mkdir"):
                            tree.makeDirectory(keyName.toLowerCase());
                            break;
                        case ("touch"):
                            tree.makeFile(keyName.toLowerCase());
                            break;
                        case ("cd.."):
                            tree.moveUpToParent(tree.getCursor());
                            break;
                        case ("cdpath"):
                            tree.moveToIndicatePath(keyName);
                            break;
                        case ("mv"):
                            System.out.println(keyword + " ");
                        case ("exit"):
                            flag = false;
                            break;
                        case ("find"):
                            tree.findNode(tree.getRoot(), keyName);

                            break;
                        default:
                            System.out.println("Error: command not found");
                    }
                }catch (FullDirectoryException fde){
                    System.out.println("ERROR: Present directory is full.");
                }
        }
        System.out.println("Program terminating normally");
    }
}
