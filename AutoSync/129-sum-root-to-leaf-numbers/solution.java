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
    private int ans;
    public int sumNumbers(TreeNode root) {
        dfs(root,0);
        return ans;

    }
    public void dfs(TreeNode root,int sum){
        if(root == null){
            return ;
        }
        sum=sum*10+root.val;
        if(root.right==null && root.left==null){
            ans+=sum;
            return;
        }
        dfs(root.left,sum);
        dfs(root.right,sum);
    }
}
