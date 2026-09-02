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
    int ans = 0;
    public int diameterOfBinaryTree(TreeNode root) {
        method(root);
        return ans;
    }
    public int method(TreeNode root){
        if(root== null){
           return -1;
        }
       int lLen = method(root.left)+1;
       int rLen = method(root.right)+1;
       ans = Math.max(ans,lLen+rLen);
       return Math.max(lLen,rLen);
    }
}
