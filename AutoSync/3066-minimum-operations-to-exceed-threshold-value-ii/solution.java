class Solution {
    public int minOperations(int[] nums, int k) {
        return  Method(nums,k);
    }
    public static int Method(int [] nums,int k){
        PriorityQueue<Long> queue = new PriorityQueue<>();
        for (long num : nums) {
            queue.offer(num);
        }
        int count = 0;
        while(queue.peek() < k){
            long x = queue.poll(),y = queue.poll();
            queue.offer(x*2+y);
            count ++;
        }
        return count;
    }
}
