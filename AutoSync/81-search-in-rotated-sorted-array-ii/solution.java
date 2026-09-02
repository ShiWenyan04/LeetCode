class Solution {
    public boolean search(int[] nums, int target) {
        return Method(nums,target);
    }
    public static boolean Method(int [] nums,int target){
        if(nums.length == 1 ){
            return nums[0] == target;
        } else if (nums.length == 0) {
            return false;
        }
        int left = 0;
        int right = nums.length-1;
        int mid ;
        while(left <= right){
            mid = (right+left)/2;
            if(nums[mid] == target)
                return true;
            if (nums[left] == nums[mid] ){
                left++;
                continue;
            }
            //前半部分有序
            if (nums[left] < nums[mid] ){
                if (nums[left]<=target && nums[mid]>target){//在前半部分
                    right = mid-1;
                }else {//在后半部分
                    left = mid+1;
                }

            }else {//后半部分有序
                if( nums[right]>=target && nums[mid]<target){//在前半部分
                    left = mid+1;
                }else {//在后半部分
                    right = mid-1;
                }
            }
        }
        return false;
    }
}
