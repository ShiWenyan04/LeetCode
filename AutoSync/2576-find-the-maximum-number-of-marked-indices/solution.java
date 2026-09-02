class Solution {
    public int maxNumOfMarkedIndices(int[] nums) {
        return Method(nums);
    }
     public static int Method(int[]nums){
        int ans = 0;
        Arrays.sort(nums);
        int right = nums.length/2,left = 0;
        while (left<nums.length/2 && right<nums.length)
            if (2*nums[left] <= nums[right]){
                ans+=2;
                right++;
                left++;
            }else {
                right++;
            }
        return ans;
    }
}
