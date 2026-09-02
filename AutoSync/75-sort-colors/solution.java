class Solution {
    public void sortColors(int[] nums) {
        Method(nums);
    }
    public static int[] Method (int[] nums){
        int n = nums.length;
        int j = 0;
        int k = 0;
            for (int i = 0; i < n; i++) {
                if (nums[i] == 0){
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                    j++;
                    k = j;
                }
            }
            for (; k < n; k++) {
                if (nums[k] == 1){
                 int temp1 = nums[k];
                 nums[k] = nums[j];
                 nums[j] = temp1;
                 j++;
                }
            }
        return nums;
    }
}
