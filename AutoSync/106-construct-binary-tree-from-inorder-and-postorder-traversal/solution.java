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
    public TreeNode buildTree(int[] inorder, int[] postorder) {
         if (inorder.length == 0 || postorder.length == 0) {
        return null;
    }
        int n = postorder.length;
        int root = postorder[n-1];
        int idx = indexOf(inorder,root);
        int []inorder1 = Arrays.copyOfRange(inorder,0,idx);
        int []inorder2 = Arrays.copyOfRange(inorder,idx+1,n);
        int []postorder1 = Arrays.copyOfRange(postorder,0,idx);
        int []postorder2 = Arrays.copyOfRange(postorder,idx,n-1);
        TreeNode tree = new TreeNode();
        tree.val = root;
        tree.left = buildTree(inorder1,postorder1);
        tree.right=buildTree(inorder2,postorder2);
        return tree;
    }
    public int indexOf(int [] num,int x){
        for(int i = 0 ; i < num.length;i++){
            if(x == num[i]){
                return i;
            }
        }
        return 0;
    }
}
