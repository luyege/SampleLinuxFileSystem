/**
 * @author Luye Ge
 * ID# 111857836
 *
 * Write a fully-documented class named DirectoryNode which represents a node in the file tree.
 * The DirectoryNode class should contain 3 DirectoryNode references, left, middle, and right. In addition,
 * the class should contain a String member variable name, which indicates the name of the node in the tree.
 * NOTE: The name member variable should be a full string with no spaces, tabs, or any other whitespace.
 * Since DirectoryNodes can be either a file or a folder, include a boolean member variable named isFile to
 * differentiate between the two. Note that if this
 * value is set to true, then the node is not a directory, and therefore should NOT contain any children.
 * That is, files are not allowed to have children.
 */
package com.company;

public class DirectoryNode {
    private String name;
    DirectoryNode[] children;
    private DirectoryNode parent;
    private boolean isFile;

    /**
     * get children
     * @return
     */
    public DirectoryNode[] getChildren() {
        return children;
    }
    /**
     * constructor
     * @param data
     * @param children
     * @param p
     * @param isFile
     */
    public DirectoryNode(String data, DirectoryNode[] children, DirectoryNode p, boolean isFile){
        name = data;
        this.parent = p;
        this.isFile = isFile;
//        left = null;
//        middle = null;
//        right = null;
        this.children = children;
    }

    /**
     * get parent
     * @return
     */
    public DirectoryNode getParent() {
        return parent;
    }

    /**
     * Adds newChild to any of the open child positions of this node (left, middle, or right).
     * NOTE: Children should be added to this node in left-to-right order, i.e.
     * left is filled first, middle is filled second, and right is filled last
     * @param newChild
     * @throws FullDirectoryException
     */
    public void addChild(DirectoryNode newChild) throws FullDirectoryException {
        int childCount = 0;
        for (int i = 0; i < children.length; i++){
            if (children[i] != null){
                childCount++;
            }
        }
        if(childCount == 10) throw new  FullDirectoryException();
        boolean flag = true;
        int position = 0;
        while (flag){
            if (children[position] != null &&
                    children[position].getName().equalsIgnoreCase(newChild.getName())){
                System.out.println("ERROR: '" + newChild.getName() + "' is already exists.");
                flag = false;
            } else if (children[position] == null){
                children[position] = newChild;
                flag = false;
            }
            position++;
        }

    }

    /**
     * determine the new node is a directory or a file
     * @return
     */
    public boolean isFile() {
        return isFile;
    }

    /**
     * get name
     * @return
     */
    public String getName() {
        return name;
    }

//    public DirectoryNode getLeft() {
//        return left;
//    }
//
//    public void setLeft(DirectoryNode newLeft) {
//        this.left = left;
//    }
//
//    public DirectoryNode getMiddle() {
//        return middle;
//    }
//
//    public void setMiddle(DirectoryNode newMiddle) {
//        this.middle = middle;
//    }
//
//    public DirectoryNode getRight() {
//        return right;
//    }
//
//    public void setRight(DirectoryNode newRight) {
//        this.right = right;
//    }

}
