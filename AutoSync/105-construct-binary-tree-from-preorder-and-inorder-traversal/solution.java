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

    public TreeNode buildTree(int[] preorder, int[] inorder) {
        int n = preorder.length;

        if (preorder.length == 0 || inorder.length == 0) {
            return null;
        }
        int idx = findRoot(inorder, preorder[0]);
        int[] inorder1 = Arrays.copyOfRange(inorder, 0, idx);
        int[] inorder2 = Arrays.copyOfRange(inorder, idx + 1, n);
        int len1 = inorder1.length;
        int len2 = inorder2.length;
        int[] preorder1 = Arrays.copyOfRange(preorder, 1,  1 + len1);
        int[] preorder2 = Arrays.copyOfRange(preorder,  1 + len1,  1 + len1 + len2);
        TreeNode root = new TreeNode();
        root.val = inorder[idx];
        root.left = buildTree(preorder1, inorder1);
        root.right = buildTree(preorder2, inorder2);
        return root;
    }

    public int findRoot(int[] num, int x) {
        for (int i = 0; i < num.length; i++) {
            if (num[i] == x) {
                return i;
            }
        }
        return 0;
    }
}
