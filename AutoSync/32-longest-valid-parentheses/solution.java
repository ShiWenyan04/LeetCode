class Solution {
    public int longestValidParentheses(String s) {
        return Method1(s);
    }
    public static int Method1(String s){
        Deque<Integer> deque = new ArrayDeque<>();
        int length = 0;
        deque.push(-1);
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '('){//对于遇到的每个 ‘(’ ，我们将它的下标放入栈中
                deque.push(i);
            }else {//对于遇到的每个 ‘)’ ，我们先弹出栈顶元素表示匹配了当前右括号：
                deque.pop();
                if (deque.isEmpty()) {//如果栈为空，说明当前的右括号为没有被匹配的右括号，我们将其下标放入栈中来更新我们之前提到的「最后一个没有被匹配的右括号的下标」
                    deque.push(i);
                }else {//如果栈不为空，当前右括号的下标减去栈顶元素即为「以该右括号为结尾的最长有效括号的长度」
                    length = Math.max(length,i-deque.peek());
                }
            }
        }
        return length;
    }
}
