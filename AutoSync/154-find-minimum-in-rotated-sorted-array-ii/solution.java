class Solution {
    public int findMin(int[] nums) {
        return (Method(nums));
    }
    public static int Method(int[] nums){
         if (nums.length == 1){
            return nums[0];
        }
        if (nums.length == 2){
            return Math.min(nums[0],nums[1]);
        }
        int left = 0,right = nums.length-1;
        int mid = 0;
        while(left <= right){
            mid = (left + right)/2;
            if (nums[mid] == nums[right]){
                right--;
            }else if (nums[mid] < nums[right]){
                right = mid;
            }else if (nums[mid] > nums[right]){
                left = mid + 1;
            }
        }
        return nums[left];
    }
}
