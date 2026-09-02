class Solution {
    public boolean isValid(String s) {
        return Method(s);
    }
    public static boolean Method(String s){
        if (s.length()<2){
            return false;
        }
        Deque<Character> deque = new ArrayDeque<>();
        int i =0;
         while(i < s.length()){
           if ( !deque.isEmpty()&&s.charAt(i) ==')' && deque.peek() == '('){
                deque.pop();
            }else if (!deque.isEmpty()&& s.charAt(i) =='}' && deque.peek() == '{'){
                deque.pop();
            }else if (!deque.isEmpty()&& s.charAt(i) ==']' && deque.peek() == '[') {
                deque.pop();
            }else {
               deque.push(s.charAt(i));
           }
            if (deque.isEmpty() && i ==s.length()-1){
                return true;
            }
            i++;
        }
        return false;
    }
}
