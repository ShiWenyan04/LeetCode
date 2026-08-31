class Solution {
    public String clearDigits(String s) {
        return Method(s);
    }public static String Method(String s) {
            int n = s.length ();
            Deque<String> deque = new LinkedList<>();
            for (int i = 0;i < n;i++){
                if (!Character.isDigit (s.charAt(i))){
                    deque.push(s.substring(i,i+1));
                }else deque.poll();
            }
            s = "";
            while (!deque.isEmpty()){
                s+= deque.getLast();
                deque.pollLast();
            }
            return s;
    }
}
