class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        return Method(nums,k);
    }
  public static int [] Method(int[] nums,int k) {
        int max[] = new int[nums.length-k+1];
        int index = 0;
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < nums.length; i++) {
            while(!deque.isEmpty() && nums[i] >= nums[deque.getLast()]){
                deque.removeLast();
            }
            deque.addLast(i);
            if (i - deque.getFirst() >= k){
                deque.removeFirst();
            }
            if (i >= k-1){
                max[index++] = nums[deque.getFirst()];
            }
        }
        return max;
    }
}
