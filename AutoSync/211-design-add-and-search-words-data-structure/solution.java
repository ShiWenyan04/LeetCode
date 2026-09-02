class WordDictionary {
    public class Node {
        Node son[] = new Node[26];
        boolean end = false;
    }

    Node root = new Node();

    public WordDictionary() {

    }

    public void addWord(String word) {
        Node cur = root;
        for (char c : word.toCharArray()) {
            c -= 'a';
            if (cur.son[c] == null) {
                cur.son[c] = new Node();
            }
            cur = cur.son[c];
        }
        cur.end = true;
    }

    public boolean search(String word) {
        return dfs(word, root, 0);
    }

    public boolean dfs(String word, Node node, int index) {
        if (index == word.length()) {
            return node.end;
        }
        char c = word.charAt(index);

        if (c == '.') {
            for (Node n : node.son) {
                if (n != null) {
                    if (dfs(word, n, index + 1)) {
                        return true;
                    }

                }

            }
            return false;
        } else {
            c -= 'a';
            if (node.son[c] == null) {
                return false;
            }
            return dfs(word, node.son[c], index + 1);
        }

    }

}

/**
 * Your WordDictionary object will be instantiated and called as such:
 * WordDictionary obj = new WordDictionary();
 * obj.addWord(word);
 * boolean param_2 = obj.search(word);
 */
