class Solution {
    public int findUnsortedSubarray(int[] nums) {
        return Method(nums);
    }
    public static int Method(int [] nums){
        int n = nums.length;
        int max = Integer.MIN_VALUE,min = Integer.MAX_VALUE;
        int rightIndex = -1,leftIndex = -1;
        for (int i = 0;i < nums.length;i++){
            if(max <= nums[i]){
                max =  nums[i];
            }else {
                leftIndex = i;
            }
            if(min >= nums[n-1-i]){
                min = nums [n-1-i];
            }else {
                rightIndex = n-1-i;
            }
        }
        return rightIndex != -1 ? leftIndex-rightIndex+1:0;
    }
}
