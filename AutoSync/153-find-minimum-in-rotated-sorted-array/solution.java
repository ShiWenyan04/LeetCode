class Solution {
    public int findMin(int[] nums) {
        return Method(nums);
    }
     public static int Method(int []nums){
        if (nums.length == 1){
            return nums[0];
        }
        int i = 0;
        int []numsCopy = new int[nums.length];
        for (; i < nums.length-1; i++) {
            if (nums[i] > nums[i+1]){
                break;
            }
            if (i == nums.length-2){
                i = -1;
                break;
            }
        }
        return nums[i+1];
    }
}
