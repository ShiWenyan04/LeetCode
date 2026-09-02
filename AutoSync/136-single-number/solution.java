class Solution {
    public int singleNumber(int[] nums) {
        return Method(nums);
    }
    public static int Method(int []nums){
        int x = 0;
        for (int i = 0; i < nums.length; i++) {
            x ^= nums[i];
        }
        return x;
    }
}
