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
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>>list = new ArrayList<>();
        Deque <TreeNode> deque = new LinkedList<>();
        if(root == null){
            return list;
        }
        deque.addFirst(root);
        int times = 0;
        while(!deque.isEmpty()){
            int size = deque.size();
            List<Integer> re = new ArrayList<>();
            for(int i = 0 ; i < size; i++){
                TreeNode node;
                if(times%2 == 0){
                     node = deque.pollFirst();
                    if(node.left != null){
                        deque.addLast(node.left);
                    }
                    if(node.right!=null){
                        deque.addLast(node.right);
                    }
                }else{
                    node = deque.pollLast();
                    if(node.right!=null){
                        deque.addFirst(node.right);
                    }
                    if(node.left != null){
                        deque.addFirst(node.left);
                    }
                }
                re.add(node.val);
            }
            list.add(re);
            times++;
        }
        return list;
    }
}
