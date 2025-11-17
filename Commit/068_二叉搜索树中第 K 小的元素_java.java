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
    List < Integer> list = new ArrayList<>();
    public int kthSmallest(TreeNode root, int k) {
        
        Method(root,k);
        return list.get(k-1);
    }
    public  void Method(TreeNode root,int k){
        if(root==null){
         return;   
        }
        Method(root.left,k);
        list.add(root.val);
        Method(root.right,k);
    }
}