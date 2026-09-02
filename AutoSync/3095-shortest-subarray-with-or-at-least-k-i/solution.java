class Solution {
    public int minimumSubarrayLength(int[] nums, int k) {
        return Method(nums,k);
    }
    public static int Method(int [] nums,int k){
        int count = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int num = 0;
            for (int j = i; j < nums.length; j++) {
                num |= nums[j];
                if(num >= k){
                    count = Math.min(count,j-i+1);
                    break;
                }
            }
        }
         return count == Integer.MAX_VALUE?-1:count;
    }
}
