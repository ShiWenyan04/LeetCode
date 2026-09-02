class Solution {
    public int largestRectangleArea(int[] heights) {
        return (Method(heights));
    }
    public static int Method(int[] heights){
        int n = heights.length,ans = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        int i = 0;
        int peek;
        while (i <= n){
            if (i == n && deque.isEmpty()){
                break;
            }
            if (i < n && (deque.isEmpty() || heights[i]>heights[deque.peek()])){
                deque.push(i);
            }else {
                peek = deque.poll();
                ans = Math.max(ans,heights[peek]*(deque.isEmpty()? i:(i-deque.peek()-1)));
                //如果栈为空，那就乘当前索引值，如果栈不为空就乘(i-deque.poll()-1)
                i--;
            }
            i++;
        }
        return ans;
    }
}
