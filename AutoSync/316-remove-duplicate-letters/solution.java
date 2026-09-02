class Solution {
    public String removeDuplicateLetters(String s) {
        return Method(s);
    }
   public static String Method(String s){
        char []chars = s.toCharArray();
        int [] index = new int[26];
        for (int i = 0; i < chars.length; i++) {
            index[chars[i] - 'a'] = i;
        }
        Deque<Character> deque = new ArrayDeque<>();
        boolean []visited = new boolean[26];
        for (int i = 0; i < s.length(); i++) {
            if(visited[chars[i]-'a']){
                continue;
            }
            while(!deque.isEmpty() && deque.peek() > chars[i] && index[deque.peek()-'a']>i){
                char c = deque.pop();
                visited[c-'a'] = false;
            }
            deque.push(chars[i]);
            visited[chars[i] -'a'] = true;
        }
        StringBuilder sb = new StringBuilder();
         while(!deque.isEmpty()){
            sb.append(deque.pollLast());
        }
        return sb.toString();
    }
}
