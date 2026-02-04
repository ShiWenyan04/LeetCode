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
    public boolean isSymmetric(TreeNode root) {
        return check(root.right,root.left);
    }
    // 用两个指针，分别探查根节点的左右子树
    // 然后判断pq的值是否相同
    // 由于是镜像，所以pq代表的两个子树的左右孩子（值相同）的位置刚好相反
    // 所以只需判断check(q.right,p.left) && check (q.left,p.right)为真
    public boolean check(TreeNode p,TreeNode q){
        if(p == null && q == null){
            return true;
        }
        if(p ==null || q==null){
            return false;
        }
        return p.val == q.val && check(q.right,p.left) && check (q.left,p.right);
    }
}