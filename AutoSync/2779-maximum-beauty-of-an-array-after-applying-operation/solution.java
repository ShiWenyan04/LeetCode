class Solution {
    public int maximumBeauty(int[] nums, int k) {
        return Solution(nums,k);
    }
    public static int Solution(int [] nums,int k){
        int ans = 1;
        int left = 0;
        Arrays.sort(nums);
        for (int right = 0; right < nums.length; right++) {
            while(left < right && nums[right] - nums[left] > 2*k){
                left++;
            }
            ans = Math.max(ans,right-left+1);
        }
        return ans;
    }
}
