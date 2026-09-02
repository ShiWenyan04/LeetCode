class Solution {
    public int numberOfArithmeticSlices(int[] nums) {
       int n = nums.length;
       if(n == 1||n == 2) return 0;
        int cnt = 0;
        int t = 0;
        int d = nums[0]-nums[1];
        for (int k = 2; k < n; k++) {
            if(nums[k-1]-nums[k] == d){
                t++;
            }else {
                t = 0;
                d = nums[k-1]-nums[k];
            }
            cnt+=t;
        }
        return cnt;
    }
}
