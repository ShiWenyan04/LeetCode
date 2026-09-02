/*
// Definition for a Node.
class Node {
    public int val;
    public Node left;
    public Node right;
    public Node next;

    public Node() {}
    
    public Node(int _val) {
        val = _val;
    }

    public Node(int _val, Node _left, Node _right, Node _next) {
        val = _val;
        left = _left;
        right = _right;
        next = _next;
    }
};
*/

class Solution {
    List<Node> list = new ArrayList<>();
    public Node connect(Node root) {
        dfs(root,0);
        return root;
    }
    public void dfs(Node root , int dept){
        if(root == null){
            return ;
        }
        if(list.size()== dept){
            list.add(root);
        }else{
            list.get(dept).next = root;
            list.set(dept,root);
        }
        dfs(root.left,dept+1);
        dfs(root.right,dept+1);
        
    }
}
