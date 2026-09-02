class Solution {
    public int maxSubArray(int[] nums) {
        //动态规划
        int f = 0;
        int ans = nums[0];
        for(int x:nums){
            f = Math.max(f,0)+x;
            ans = Math.max(ans,f);
        }
        return ans;
    }
}
