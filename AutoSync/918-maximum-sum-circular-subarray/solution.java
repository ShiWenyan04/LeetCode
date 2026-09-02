class Solution {
    public int maxSubarraySumCircular(int[] nums) {
        int maxF = 0;
        int maxS = nums[0];
        int minF = 0;
        int minS = nums[0];
        int sum = 0;
        for(int x:nums){
            maxF = Math.max(maxF,0)+x;
            maxS = Math.max(maxF,maxS);
            minF = Math.min(minF,0)+x;
            minS = Math.min(minF,minS);
            sum+=x;
        }
        return maxS<0 ? maxS:Math.max(maxS,sum-minS);
    }
}
