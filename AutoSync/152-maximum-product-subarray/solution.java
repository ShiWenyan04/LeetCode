class Solution {
    public int maxProduct(int[] nums) {
        return Method(nums);
    }
     public static int Method(int []nums){
        int curMax = nums[0];
        int max = nums[0];
        int min = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums [i]<0){
                int temp = max;
                max = min;
                min = temp;
            }

            max = Math.max(max*nums[i],nums[i]);
            min = Math.min(min*nums[i],nums[i]);

            curMax = Math.max(curMax,max);
        }
        return curMax;
    }
}
