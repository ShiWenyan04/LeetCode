class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        return Method(asteroids);
    }
    public static int [] Method(int [] ast){
        int n = ast.length;
        Deque< Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            int num = ast[i];
            boolean alive = true;
            while(alive &&!deque.isEmpty() && deque.peek() > 0 && ast[i] < 0 ){
                alive = deque.peek() < -num;
                if(Math.abs(num) >= deque.peek()) {
                    deque.poll();
                }
            }
            if (alive){
                deque.push(num);
            }
        }
        int len = deque.size();
        int []ans = new int[len];
        for (int i = len-1; i >= 0; i--) {
            ans[i] = deque.pop();
        }
        return ans;
    }
}
