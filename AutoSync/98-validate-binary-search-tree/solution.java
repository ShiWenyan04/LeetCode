/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    public boolean isValidBST(TreeNode root) {
        return Method(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }
    public boolean Method(TreeNode root , long low,long upper){
        if( root == null){
            return true;
        }
        if(root.val <= low || root.val >= upper){
            return false;
        }
        return Method(root.left,low,root.val)&&Method(root.right,root.val,upper);
    }
}
