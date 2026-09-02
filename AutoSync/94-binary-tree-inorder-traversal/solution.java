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
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> list = new ArrayList<Integer>();
        midOrder(root,list);
        return list;
    }
    public static void midOrder(TreeNode root,List<Integer> list) {
        if (root == null) return;
        midOrder(root.left,list); // 左
        list.add(root.val);
        midOrder(root.right,list); // 右
    }
}
