class Solution {
    public int[] searchRange(int[] nums, int target) { 
        return Method(nums,target);
    }
     public static int[] Method(int [] nums, int target){
        int low = 0, height = nums.length-1;
        int min = 0, max = 0 ;
        int[] index = {min, max};
        while(low <= height){
            int mid=(low+height)/2;
            if (target > nums [mid] ){
                low = mid + 1 ;
            }else if(target == nums[mid]){
                    int temp = mid;
                    max = mid;
                    min = mid;
                    while (++mid < nums.length && nums[mid] == target){//右边的数字是否是target
                        max = mid;
                    }
                    mid = temp;
                    while(--mid >= 0 && nums[mid] == target){
                        min = mid;
                    }
                    index = new int[]{min,max};
                    return index;
            } else if (target < nums[mid]) {
                height = mid - 1 ;
            }
        }
        return index = new int[]{-1,-1};
    }
}
