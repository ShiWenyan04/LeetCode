class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        return Method(temperatures);
    }
    public static int[] Method(int []temper) {
		int n = temper.length;
		int []ans = new int [n];
        Deque<Integer> d = new LinkedList<>();
		for (int i = 0; i < n; i++) {
            int t = temper[i];
            while(!d.isEmpty() && t > temper[d.peek()]){
                ans[d.peek()] = i-d.pop();
            }
            d.push(i);
        }
		return ans;
	}
}