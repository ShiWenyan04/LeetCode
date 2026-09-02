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
    private int ans = Integer.MAX_VALUE;
    private int pre = Integer.MIN_VALUE / 2; // 防止减法溢出
    public int getMinimumDifference(TreeNode root) {
        dfs(root);
        return ans;
    }
    public void dfs(TreeNode node){
        if(node == null){
            return;
        }

        dfs(node.left);
        ans = Math.min(ans,node.val-pre);
        pre = node.val;
        dfs(node.right);
    }
}
