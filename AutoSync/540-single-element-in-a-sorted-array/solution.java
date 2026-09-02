class Solution {
    public int singleNonDuplicate(int[] nums) {
        return Method(nums);
    }
    public static int Method(int [] nums){
        int left = 0;
        int right = nums.length-1;
        int mid = 0;
        if (nums.length == 1){
            return nums[0];
        }
        while(left<right){
            mid = (left+right)/2;
            if ( nums[mid-1] != nums[mid] && nums[mid+1] != nums[mid]){
                return nums[mid];
            }else if ((mid-left+1)%2==0 && nums[mid]==nums[mid-1]){
                left=mid+1;
            }else if ((mid-left+1)%2!=0 && nums[mid]==nums[mid-1]){
                right = mid-2;
            }else if ((right-mid+1)%2==0 && nums[mid] == nums[mid+1]){
                right=mid-1;
            }else if ((right-mid+1)%2!=0 && nums[mid] == nums[mid+1]){
                left = mid+2;
            }
        }
        return nums[left];
    }
}
