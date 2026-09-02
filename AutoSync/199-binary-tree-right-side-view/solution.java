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
    public List<Integer> rightSideView(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        dfs(root,0,list);
        return list;
    }
    public static void dfs(TreeNode root, int deepth,List<Integer> list){
        if(root == null){
            return;
        }
        if(list.size() == deepth){// 这个深度首次遇到
            list.add(root.val);
        }
        dfs(root.right,deepth+1,list);
        dfs(root.left,deepth+1,list);
    }
}
