package org.gfg.bst;

import java.util.*;
import org.gfg.SortedSet;

/**
 * Implements {@link SortedSet} interface using a binary search tree. Note that this
 * implementation makes no guarantees about the balance of the tree. E.g.
 * inserting elements in sorted order will result in a degenerate binary search
 * tree - a linked list essentially. Use {@link AvlTree} instead if you need
 * balance guarantees.
 * 
 * @param <T> type of elements stored in binary search tree
 */
public class Bst<T extends Comparable<T>> implements SortedSet<T> {

    protected class BstNode {
        public T value;
        public BstNode left;
        public BstNode right;

        public BstNode(T value) {
            this.value = value;
        }
    }

    private class InorderBstIterator implements Iterator<T>{
        private Stack<BstNode> stack;
        public InorderBstIterator(){
            stack = new Stack<BstNode>();
            BstNode node = root;
            while(node != null){
                stack.push(node);
                node = node.left;
            }
        }

        @Override
        public boolean hasNext() {
            return !stack.isEmpty();
        }

        @Override
        public T next() {
            BstNode topNode = stack.pop();
            BstNode node = topNode.right;
            while(node != null){
                stack.push(node);
                node = node.left;
            }
            return topNode.value;
        }
    }

    protected BstNode root;
    protected int size;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public void add(T item) {
        root = add(item, root);
    }

    protected BstNode add(T key, BstNode root) {
        if(root == null) {
            size++;
            return new BstNode(key);
        }
        if(root.value.compareTo(key) > 0) root.left = add(key, root.left);
        else if(root.value.compareTo(key) < 0) root.right = add(key, root.right);
        return root;
    }

    @Override
    public boolean contains(T item) {
        return search(root, item) != null;
    }

    private BstNode search(BstNode root, T key){
        if (root == null) return null;
        if (root.value.compareTo(key) == 0) return root;
        if (root.value.compareTo(key) > 0) return search(root.left, key);
        return search(root.right, key);
    }

    /**
     * Returns the height of this binary search tree - the longest distance from the
     * root to some leaf.
     * @return height of this binary search tree
     */
    public int height() {
        return height(root);
    }

    protected int height(BstNode root){
        if(root == null) return -1;
        return Math.max(height(root.left), height(root.right)) + 1;
    }

    @Override
    public Iterator<T> iterator() {
        return new InorderBstIterator();
    }

    @Override
    public void remove(T item) {
        nodeDeleted = false;
        root = delete(root, item);
        if(nodeDeleted) {
            size--;
        }
    }

    protected boolean nodeDeleted;
    protected BstNode delete(BstNode root, T key){
        if(root == null) return null;
        if(root.value.compareTo(key) > 0) root.left = delete(root.left, key);
        else if(root.value.compareTo(key) < 0) root.right = delete(root.right, key);
        else{ // key to be deleted found
            nodeDeleted = true;
            if(root.left == null) return root.right;
            if(root.right == null) return root.left;
            // replace node value with the value of inorder successor
            root.value = minNode(root.right).value;
            // delete inorder successor in the right subtree
            root.right = delete(root.right, root.value);
        }
        return root;
    }

    @Override
    public T successor(T key) {
        BstNode successorNode = successor(root, null, key);
        if(successorNode != null) return successorNode.value;
        return null;
    }

    private BstNode successor(BstNode root, BstNode inorderParent, T key){
        if(root == null) return inorderParent;
        BstNode successorNode = null;
        if(root.value.compareTo(key) == 0) successorNode = minNode(root.right);
        else if(root.value.compareTo(key) > 0) successorNode = successor(root.left, root, key);
        else successorNode = successor(root.right, inorderParent, key);
        if(successorNode == null) successorNode = inorderParent;
        return successorNode;
    }

    @Override
    public T ceil(T key) {
        BstNode ceilNode = ceil(root, null, key);
        if(ceilNode != null) return ceilNode.value;
        return null;
    }

    private BstNode ceil(BstNode root, BstNode inorderParent, T key){
        if(root == null) return inorderParent;
        if(root.value.compareTo(key) == 0) return root;
        BstNode ceilNode = null;
        if(root.value.compareTo(key) > 0) ceilNode = ceil(root.left, root, key);
        else ceilNode = ceil(root.right, inorderParent, key);
        if(ceilNode == null) ceilNode = inorderParent;
        return ceilNode;
    }

    @Override
    public T predecessor(T key) {
        BstNode predecessorNode = predecessor(root, null, key);
        if(predecessorNode != null) return predecessorNode.value;
        return null;
    }

    private BstNode predecessor(BstNode root, BstNode inorderParent, T key){
        if(root == null) return inorderParent;
        BstNode predecessorNode = null;
        if(root.value.compareTo(key) == 0) predecessorNode = maxNode(root.left);
        else if(root.value.compareTo(key) > 0) predecessorNode = predecessor(root.left, inorderParent, key);
        else predecessorNode = predecessor(root.right, root, key);
        if(predecessorNode == null) predecessorNode = inorderParent;
        return predecessorNode;
    }

    @Override
    public T floor(T key) {
        BstNode floorNode = floor(root, null, key);
        if(floorNode != null) return floorNode.value;
        return null;
    }

    private BstNode floor(BstNode root, BstNode inorderParent, T key){
        if(root == null) return inorderParent;
        if(root.value.compareTo(key) == 0) return root;
        BstNode floorNode = null;
        if(root.value.compareTo(key) > 0) floorNode = floor(root.left, inorderParent, key);
        else floorNode = floor(root.right, root, key);
        if(floorNode == null) floorNode = inorderParent;
        return floorNode;
    }

    @Override
    public T min() {
        return minNode(root).value;
    }

    @Override
    public T max() {
        return maxNode(root).value;
    }

    protected BstNode minNode(BstNode root){
        if(root == null || root.left == null) return root;
        return minNode(root.left);
    }

    private BstNode maxNode(BstNode root){
        if(root == null || root.right == null) return root;
        return maxNode(root.right);
    }

    @Override
    public List<T> toList() {
        List<T> list = new ArrayList<>();
        iterator().forEachRemaining(list::add);
        return list;
    }

    /**
     * Checks for balance in this binary search tree. A binary tree is balanced
     * if for each node the absolute difference between the height of its left subtree 
     * and its right subtree is no larger than 1.
     * @return true if this binary search tree is balanced
     */
    public boolean isBalanced(){
        return isBalanced(root).isBalanced;
    }

    private class BalanceResponse{
        public int height;
        public boolean isBalanced;
        public BalanceResponse(int height, boolean isBalanced){
            this.height = height;
            this.isBalanced = isBalanced;
        }
    }
    private BalanceResponse isBalanced(BstNode root){
        if(root == null) return new BalanceResponse(-1, true);
        BalanceResponse left = isBalanced(root.left);
        BalanceResponse right = isBalanced(root.right);
        if(!left.isBalanced) return left;
        if(!right.isBalanced) return right;
        boolean isBalanced = Math.abs(left.height - right.height) <= 1;
        return new BalanceResponse(Math.max(left.height, right.height) + 1, isBalanced);
    }
}