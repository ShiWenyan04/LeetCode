class Solution {
    public int searchInsert(int[] nums, int target) {
        int mid = 0, left = 0,right = nums.length-1;
        if (nums[nums.length-1] < target){
            return nums.length;
        }else if(nums[0] > target){
            return 0;
        }
        while(left <= right){
            mid = (right + left)/2;
            if (nums[mid] == target || (nums[mid] > target && nums[mid-1] < target) ) {
                break;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else if (nums[mid] > target){
                right = mid;
            }
        }
        return mid;
    }
}
