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
    int rightCnt = 0;
    int leftCnt = 0;
    public int countNodes(TreeNode root) {
        if(root== null){
            return 0;
        }
        //左右子树最高高度
        int leftheight = hight(root.left);
        int rightheight = hight(root.right);

        int ans = 0;
        //左右子树相等时，最后一个节点在右子树上
        if(leftheight == rightheight){
             // 左子树节点数 = 2^leftHeight - 1，加上根节点，再加上右子树
            return (1 << leftheight) + countNodes(root.right);
        } else {
            // 左子树高度 > 右子树高度，说明右子树是满二叉树
            // 右子树节点数 = 2^rightHeight - 1，加上根节点，再加上左子树
            return (1 << rightheight) + countNodes(root.left);
        }
    }
    public int hight(TreeNode root){
        int h = 0;
        while(root!=null){
            h++;
            root = root.left;
        }
        return h;
    }
   
}
