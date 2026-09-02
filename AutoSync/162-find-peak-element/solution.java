class Solution {
    public int findPeakElement(int[] nums) {
        return Method(nums);
    }
    public static int Method(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        int mid = 0;
        if ((nums.length == 2 || nums.length == 1) && (nums[0]>=nums[nums.length-1])){
            return 0;
        } else if ((nums.length == 2 || nums.length == 1) && (nums[0]<nums[nums.length-1])) {
            return nums.length-1;
        }
       while(left<=right){
             mid = (left+right)/2;
            if ((mid != 0 && mid != nums.length-1 && nums[mid-1]<nums[mid] && nums[mid+1]<nums[mid])
                    || (mid == 0&& nums[mid]>nums[mid+1])
                    ||(mid == nums.length-1&&nums[mid-1]<nums[mid])){
                return mid;
            }
            if (nums[mid] > nums[mid-1] ||nums[mid] < nums[mid+1] ){
                left ++ ;
            }else if (nums[mid] > nums[mid+1] ||nums[mid] < nums[mid-1]){
                right --;
            }
        }
        return mid;
    }
}
