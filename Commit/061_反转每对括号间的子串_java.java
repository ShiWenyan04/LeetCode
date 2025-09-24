class Solution {
    public String reverseParentheses(String s) {
        return Method(s);
    }
    public static String Method(String str) {
		Deque <String> deque = new ArrayDeque<>();
		char []ch = str.toCharArray();
		StringBuilder sBuilder = new StringBuilder();
		for (int i = 0; i < ch.length; i++) {
			if(ch[i] == '(') {
				deque.push(sBuilder.toString());
				sBuilder.setLength(0);
			}else if (ch[i] == ')') {
				sBuilder.reverse();
				sBuilder.insert(0, deque.poll());
				
			} else {
				sBuilder.append(ch[i]);
			}
		}
		return sBuilder.toString();
	}
}