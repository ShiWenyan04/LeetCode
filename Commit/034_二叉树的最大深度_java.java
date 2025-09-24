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
    public int maxDepth(TreeNode root) {
        if(root==null){
            return 0;
        }
        int leftLen = 0, rightLen = 0;
        leftLen = maxDepth(root.left);
        rightLen = maxDepth(root.right);
        return Math.max(leftLen,rightLen)+1;
    }

    
}
// public int maxDepth(TreeNode root) {
    //      if(root == null){
    //         return 0;
    //     }
    //     Queue<TreeNode> q = new LinkedList<TreeNode>();
    //     q.offer(root);
    //     int cnt = 0;
    //     while(!q.isEmpty()){
    //         int size = q.size();
    //         while(size > 0){
    //             TreeNode n = q.poll();
    //             if(n.left!= null){
    //                 q.offer(n.left);
    //             }
    //             if(n.right != null){
    //                 q.offer(n.right);
    //             }
    //             size--;
    //         }
    //         cnt++;
    //     }
    //     return cnt;
    // }