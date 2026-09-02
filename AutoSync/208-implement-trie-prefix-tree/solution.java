class Trie {
    private static class Node{
        Node []son = new Node[26];
        boolean end = false;
    }
    Node root = new Node();
    public Trie() {
        
    }
    
    public void insert(String word) {
        Node cur = root;
        for(char c: word.toCharArray()){
            c-='a';
            if(cur.son[c] == null){
                cur.son[c] = new Node();
            }
            cur = cur.son[c];
        }
        cur.end = true;
    }
    
    public boolean search(String word) {
        int judge = find(word);
        return judge==1;
    }
    
    public boolean startsWith(String prefix) {
        return find(prefix)!=0;
    }
    public int find(String s){
        Node cur = root;
        for(char c : s.toCharArray()){
            c-='a';
            if(cur.son[c] == null){
                return 0;
            }
            cur = cur.son[c];
        }
        return cur.end ? 1:2;
    }
}

/**
 * Your Trie object will be instantiated and called as such:
 * Trie obj = new Trie();
 * obj.insert(word);
 * boolean param_2 = obj.search(word);
 * boolean param_3 = obj.startsWith(prefix);
 */
