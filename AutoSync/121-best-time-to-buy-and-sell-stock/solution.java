class Solution {
    public static int maxProfit(int[] nums) {
        int n = nums.length;
        int ans = 0;
        int min = nums[0];
        for(int i=1;i<n;i++){
            min =  Math.min(min,nums[i]);
            ans = Math.max(ans,nums[i]-min);
        }
        return ans;
    }
}
