/**
 * @author Luye Ge
 * ID# 111857836
 *
 * Write a fully-documented class named DirectoryTree which implements a ternary (3-child) tree of DirectoryNodes.
 * The class should contain a reference to the root of the tree, a cursor for the present working directory,
 * and various methods for insertion and deletion.
 * The DirectoryTree class should provide an implementation for the operations defined for the system
 * (see list below and sample I/O for details). The class should contain methods
 * for moving the cursor through the file system, printing the filepath of
 * the present working directory (cursor location), listing the directories and
 * files in the present working directory, printing the entire file system, and finding a file in the file system.
 */
package com.company;

import java.nio.file.NotDirectoryException;

public class DirectoryTree {
    private DirectoryNode root;
    private DirectoryNode cursor;

    /**
     * Brief:
     * Initializes a DirectoryTree object with a single DirectoryNode named "root". Preconditions:
     * None.
     * Postconditions:
     * The tree contains a single DirectoryNode named "root", and both cursor and root reference this node.
     * NOTE: Do not confuse the name of the directory with the name of the reference variable.
     * The DirectoryNode member variable of DirectoryTree named root should reference a DirectoryNode
     * who's name is "root", i.e. root.getName().equals("root") is true.
     * @param name
     */
    public DirectoryTree(String name){
        root = new DirectoryNode(name, new DirectoryNode[10], null, false);
        cursor = root;
    }

    /**
     * Brief:
     * Initializes a DirectoryTree object with a single DirectoryNode named "root". Preconditions:
     * None.
     * Postconditions:
     * The tree contains a single DirectoryNode named "root", and both cursor and root reference this node.
     * NOTE: Do not confuse the name of the directory with the name of the reference variable.
     * The DirectoryNode member variable of DirectoryTree named root should reference a DirectoryNode
     * who's name is "root", i.e. root.getName().equals("root") is true.
     */
    public DirectoryNode getRoot() {
        return root;
    }

    /**
     * get the current cursor pointer
     * @return
     */
    public DirectoryNode getCursor() {
        return cursor;
    }

    public void setCursor(DirectoryNode cursor) {
        this.cursor = cursor;
    }

    /**
     * Brief:
     * Moves the cursor to the root node of the tree.
     * Preconditions:
     * None.
     * Postconditions:
     * The cursor now references the root node of the tree.
     */
    public void resetCursor(){ // cd /
        cursor = root;
    }

    /**
     * Brief:
     * Moves the cursor to the directory with the name indicated by name. Preconditions:
     * 'name' references a valid directory ('name' cannot reference a file).
     * Postconditions:
     * The cursor now references the directory with the name indicated by name.
     * If a child could not be found with that name, then the user is prompted to enter a different directory name.
     * If the name was not a directory, a NotADirectoryException hs been thrown
     * Throws:
     * NotADirectoryException: Thrown if the node with the indicated name is a file,
     * as files cannot be selected by the cursor, or cannot be found.
     *          Adds newChild to any of the open child positions of this node (left, middle, or right).
     * NOTE: Children should be added to this node in left-to-right order,
     * i.e. left is filled first, middle is filled second, and right is filled last
     *                        Moves the cursor to the root node of the tree.
     * NOTE: In modern operating systems, the change directory command (cd {path}) allows the user to jump from
     * a current directory to any other directory in the file system given an absolute or relative path.
     * In this assignment, you will only be required to change directory to direct children of the cursor (cd {dir}).
     * However, you may implement the more general command for absolute paths for extra credit.
     * @param name
     * @param currentCursor
     * @throws NotDirectoryException
     * @throws NullPointerException
     */
    public void changeDirectory(String name, DirectoryNode currentCursor) throws NotDirectoryException, NullPointerException {
// cd name
        try {
            DirectoryNode[] children = currentCursor.getChildren();
            boolean flag = true;
            int position = 0;
            while (flag){
                if (position == children.length) throw new NullPointerException();
                if (children[position] != null &&
                        (!children[position].isFile()) &&
                        children[position].getName().equalsIgnoreCase(name)){
                    cursor = children[position];
                    flag = false;
                }else if (children[position] != null &&
                            children[position].isFile()) throw new NotDirectoryException("");
                position++;
            }
        } catch (NullPointerException npe){
            System.out.println("ERROR: " + name + " can not be found, enter a different directory name. ");
        } catch (NotDirectoryException nde){
            System.out.println("ERROR: '" + name +"' is a file, enter a directory name.");
        }
    }

    /**
     * Moves the cursor up to its parent directory (does nothing at root).
     * @param currentCursor
     */
    // cd..
    public void moveUpToParent(DirectoryNode currentCursor){
        cursor = currentCursor.getParent();
    }

    /**
     * Finds the node in the tree with the indicated name and prints the path.
     * @param root
     * @param name
//     * @param flag
     * @return
     */
    // find
    public void findNode(DirectoryNode root, String name) throws NullPointerException {

        if(root.getName().equals(name)){
            System.out.println(presentWorkingDirectory(root));
        }

        if (root.getChildren() != null) {
            for (int i = 0; i < root.getChildren().length; i++) {
                if (root.getChildren()[i] != null){
                    findNode(root.getChildren()[i], name);
                }
            }
        }
    }


    /**
     * Moves the cursor to the directory with the indicated path.
     * (e.g. cd root/home/user/Documents)
     * @param name
     * @return
     */
    // cd path
    public void moveToIndicatePath(String name){
        String[] splitWords = name.split("/");
        String target = splitWords[splitWords.length - 1];
        findNode(root,target);

    }

    /**
     * Brief:
     * Returns a String containing the path of directory names from the root node of the tree to the cursor,
     * with each name separated by a forward slash "/".
     * e.g. root/home/user/Documents if the cursor is at Documents in the example above.
     * Preconditions:
     * None.
     * Postconditions:
     * The cursor remains at the same DirectoryNode.
     * @param currentCursor
     * @return
     */
    // pwd
    public String presentWorkingDirectory(DirectoryNode currentCursor) {
        if(currentCursor.getParent() == null){
            return currentCursor.getName();
        }
        String s = currentCursor.getName() + " " + presentWorkingDirectory(currentCursor.getParent());
        String[] splitWords = s.split("\\s+");
        String finalPath = "";
        for(int i = splitWords.length - 1; i >= 0; i--){
            finalPath += splitWords[i];
            if(i != 0){
                finalPath += "/";
            }
        }
        return finalPath.replaceAll("//","/");
    }

    /**
     * Brief:
     * Returns a String containing a space-separated list of names of all the child directories or files of the cursor.
     * e.g. dev home bin if the cursor is at root in the example above.
     * Preconditions:
     * None.
     * Postconditions:
     * The cursor remains at the same DirectoryNode. Returns:
     * A formatted String of DirectoryNode names.
     * @param currentCursor
     * @return
     */
    // ls
    public String listDirectory(DirectoryNode currentCursor){
        DirectoryNode[] children = currentCursor.getChildren();
        for (int i = 0; i < children.length; i++)
        {
            if (children[i] != null)
                System.out.print(children[i].getName() + " ");
        }
        System.out.println();
        return cursor.getName();
    }

    /**
     * Brief:
     * Prints a formatted nested list of names of all the nodes in the directory tree, starting from the cursor.
     * See sample I/O for an example.
     * Preconditions:
     * None.
     * Postconditions:
     * The cursor remains at the same DirectoryNode.
     * @param currentCursor
     * @param levelCount
     */
    // ls-r
    public void printDirectoryTree(DirectoryNode currentCursor, int levelCount){
        String sp = "";
        String spaces = "   ";
        if( currentCursor == null)
            return;
        levelCount++;
        for(int i =0; i< levelCount; i++){
            sp += spaces;
        }
        System.out.print(sp);
        if(currentCursor.isFile()){
            System.out.print(" - ");
        }else {
            System.out.print("|- ");
        }
        System.out.println(currentCursor.getName());

        DirectoryNode[] children = currentCursor.getChildren();

        for (int i = 0; i < children.length; i++)
        {
            if (children[i] != null)
                printDirectoryTree(children[i], levelCount);
        }
    }

    /**
     * Brief:
     * Parameters:
     * name The name of the directory to add. Preconditions:
     * 'name' is a legal argument (does not contain spaces " " or forward slashes "/").
     * Postconditions:
     * A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
     * Throws:
     * IllegalArgumentException: Thrown if the 'name' argument is invalid.
     * FullDirectoryException: Thrown if all child references of this directory are occupied.
     * @param name
     * @throws IllegalArgumentException
     * @throws NotDirectoryException
     * @throws FullDirectoryException
     */
    // mkdir
    public void makeDirectory(String name) throws IllegalArgumentException, NotDirectoryException, FullDirectoryException {

        if (cursor.isFile()) throw new NotDirectoryException("Not a Directory");
        DirectoryNode node = new DirectoryNode(name, new DirectoryNode[10], cursor,false);
        cursor.addChild(node);
    }

    /**
     * Brief:
     * Parameters:
     * name The name of the file to add.
     * Preconditions:
     * 'name' is a legal argument (does not contain spaces " " or forward slashes "/").
     * Postconditions:
     * A new DirectoryNode has been added to the children of the cursor, or an exception has been thrown.
     * Throws:
     * IllegalArgumentException: Thrown if the 'name' argument is invalid.
     * FullDirectoryException: Thrown if all child references of this directory are occupied.
     * @param name
     * @throws IllegalArgumentException
     * @throws FullDirectoryException
     */
    // touch
    public void makeFile(String name) throws IllegalArgumentException, FullDirectoryException {
        // touch
        DirectoryNode node = new DirectoryNode(name, new DirectoryNode[10], cursor, true);
        cursor.addChild(node);
    }
}
