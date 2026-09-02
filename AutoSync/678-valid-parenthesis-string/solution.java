class Solution {
    public boolean checkValidString(String s) {
        return Method(s);
    }
     public static boolean Method(String s){
        Deque<Integer> deque = new ArrayDeque<>();//
        Deque<Integer> deque2 = new ArrayDeque<>();
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == '(' ){
                deque.push(i);
            }else if(s.charAt(i) == '*'){
                deque2.push(i);
            }else {
               if(!deque.isEmpty()){
                   deque.poll();
               }else if(!deque2.isEmpty()) {
                   deque2.pop();
               }else return false;
            }
        }
           while(!deque.isEmpty() && !deque2.isEmpty()) {
                if (deque.pop() > deque2.pop()){
                   return false;
                }
            }
        return deque.isEmpty();
    }
}
