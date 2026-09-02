class Solution {
    public int singleNumber(int[] nums) {
        return Method(nums);
    }
     public static int Method(int[]nums){
        Deque<Integer> deque = new ArrayDeque<>();
        Arrays.sort(nums);
        int times = 0;
        for(int i=0;i<nums.length;i++){
            if (!deque.isEmpty() && deque.peek() == nums[i]){
                deque.poll();
                deque.push(nums[i]);
                times ++;
                if (times == 3){
                    deque.poll();
                    times=0;
                }
            }else if(deque.isEmpty()){
                deque.push(nums[i]);
                times ++;
            }
        }
        return deque.peek();
    }
}
