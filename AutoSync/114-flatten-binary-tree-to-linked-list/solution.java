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
    public void flatten(TreeNode root) {
        List<TreeNode> list = new ArrayList<>();
         dfs(root,list);
         int len = list.size();
         for(int i = 1; i <len;i++){
            TreeNode node1 = list.get(i-1);
            TreeNode node2 = list.get(i);
            node1.right = node2;
            node1.left = null; 
         }
         return;
    }
    public void dfs(TreeNode root,List<TreeNode> list){
        if(root == null){
            return ;
        }
        list.add(root);
        dfs(root.left,list);
        dfs(root.right,list);
        return;
    }

}
