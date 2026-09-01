class Solution {
    public int magicTower(int[] nums) {
        return Method(nums);
    }
    public static int Method(int[] nums) {
		int n = nums.length;
		long sum = 1;
		for (int i : nums) {
			sum+=i;
		}
		
		if(sum < 0) return -1;
		
		sum = 1;
		int count = 0;
		PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
		for (int num : nums) {
			sum+=num;
			if(num < 0) {
				queue.offer(num);
			}
			if(sum <= 0) {
				sum-=queue.poll();
				count ++;
			}
		}
		return count;
}
}
