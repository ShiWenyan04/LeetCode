class Solution {
    public int search(int[] nums, int target) {
        return Method(nums,target);
    }
     public static int Method(int [] nums,int target) {
        int start = 0;
        int end = nums.length-1;
        int mid = -1;
        while(start <= end){
            mid = (start+end)/2;
            if(nums[mid] == target){
                return mid;
            }
            if(nums[start] == nums[mid]){
                start++;
                continue;
            }
            if (nums[start] < nums[mid]){
                if (nums[start] <= target && target <= nums[mid]){
                    end = mid-1;
                }else {
                    start = mid+1;
                }
            }else{
                if (nums[mid] < target && nums[end] >= target){
                    start = mid+1;
                }else {
                    end = mid -1;
                }
            }
        }
        return -1;
    }
}
