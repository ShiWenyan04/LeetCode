class Solution {
    public int evalRPN(String[] tokens) {
        return Method(tokens);
    }
    public static int Method(String[] tokens){
        Deque<Integer> deque = new ArrayDeque<>();
        int i = 0;
        while(i < tokens.length){
            if(!tokens[i].equals("+") && !tokens[i].equals("-") && !tokens[i].equals("*") && !tokens[i].equals("/")){
                deque.push(Integer.valueOf(tokens[i]));//遇见数字就存
            }else{
                int a = deque.poll(),b = deque.poll();
                int preAns;
                switch (tokens[i]) {
                    case "*":
                        preAns = b * a;
                        deque.push(preAns);
                        break;
                    case "+":
                        preAns = b + a;
                        deque.push(preAns);
                        break;
                    case "-":
                        preAns = b - a;
                        deque.push(preAns);
                        break;
                    case "/":
                        preAns = b / a;
                        deque.push(preAns);
                        break;
                }
            }
            i++;
        }
        return deque.poll();
    }
}
