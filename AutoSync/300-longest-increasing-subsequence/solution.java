class Solution {
    public int lengthOfLIS(int[] nums) {
        return Method(nums);
    }
    public static int Method(int[] nums){
         int n = nums.length;
        int[]f = new int[n];
        int piles = 0;
        for (int i = 0; i < n; i++) {
            int poker = nums[i];
            int left = 0,right = piles;
            while(left < right){
                int mid = (left + right)/2;
                if(f[mid]>=poker){
                    right = mid;
                }else {
                    left = mid+1;
                }
            }
            if(right== piles) piles++;
            f[left] = poker;
        }
        return piles;
    }
}
