class Solution {
    public double findMaxAverage(int[] nums, int k) {
        return Method(nums,k);
    }
    public static double Method(int[] nums,int k){
        int n = nums.length;
        int sum = 0;
        for (int i = 0; i < k; i++) {
            sum += nums[i];
        }
        int max = sum;
        for (int i = 0; i < n-k; i++) {
            if(i+k < n){
                sum -= nums[i];
                sum += nums[i+k];
            }
            max = Math.max(max,sum);
        }
        return 1.0*max/k;
    }
}
