class Solution {
    public String removeKdigits(String num, int k) {
        return Method(num,k);
    }
    public static String Method(String num,int k){
        if(num.length() == k){
            return "0";
        }
        Deque<Character> deque = new ArrayDeque<>();
        int i = 0;
        char ch = ' ';
        while (i != num.length()){
            ch = num.charAt(i);
            while (i != 0 && !deque.isEmpty() &&  k > 0 && deque.peek() > ch ){
                deque.pop();
                k--;
            }
            deque.push(ch);
            i++;
        }

        for (int j = 0; j < k; ++j) {
            deque.pop();
        }

        StringBuilder sb = new StringBuilder();
        while (!deque.isEmpty()){
            sb.append(deque.pollLast());
        }
        int j = 0;
        String str = sb.toString();
        String ans = str;
        while( j < str.length() && str.charAt(j) == '0') {
            ans = str.substring(++j);
        }
        return ans.isEmpty() ?"0":ans;
    }
}
