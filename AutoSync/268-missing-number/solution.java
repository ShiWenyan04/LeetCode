class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int sum = 0;
        for(int i = 0; i<=n; i++){
            sum+=i;
            if(i<n){
              sum-=nums[i];
            }
            
        }
        return sum;
    }
}
