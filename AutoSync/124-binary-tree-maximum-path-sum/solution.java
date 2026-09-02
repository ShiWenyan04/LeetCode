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
    public int ans = Integer.MIN_VALUE;
    public int maxPathSum(TreeNode root) {
        dfs(root);
        return ans;
    }

    public int dfs(TreeNode node){
        if(node == null){
            return 0;
        }
        int sumL = dfs(node.left);
        int sumR = dfs(node.right);

        ans = Math.max(ans,sumL+sumR+node.val);

        return Math.max(Math.max(sumL,sumR)+node.val,0);

    }
}
