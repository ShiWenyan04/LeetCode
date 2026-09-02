class Solution {
    public int trap(int[] height) {
        return Method(height);
    }
    public static int Method(int[] heights){
        Deque<Integer> deque = new ArrayDeque<>();
        int i = 0;
        int peek;
        int h ,w, ans=0;
          while(i < heights.length){
            if (deque.isEmpty() || heights[deque.peek()] > heights[i]){
                deque.push(i);
            }else {
                peek = deque.pop();
                if (deque.isEmpty()){
                    continue;
                }
                h = Math.min(heights[deque.peek()],heights[i])-heights[peek];
                w = (i-deque.peek()-1);
                ans+=h*w;
                i--;
            }
            i++;
        }
        return ans;
    }
}
