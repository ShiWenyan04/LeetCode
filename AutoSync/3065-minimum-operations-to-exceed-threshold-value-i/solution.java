class Solution {
    public int minOperations(int[] nums, int k) {
  Arrays.sort(nums);
        int left = 0, right = nums.length;
        int mid = 0;
        if (nums[nums.length - 1] < k){
            return nums.length;
        } else if (nums[0] >= k) {
            return 0;
        }else {
            while(left < right){
                mid = (right + left)/2;
               if (nums[mid] == k  && nums[mid-1] != k|| (nums[mid] > k && nums[mid-1] < k)){
                    break;
                }else if (nums[mid] >= k){
                    right = mid;
                }else if (nums[mid] <= k){
                    left = mid+1;
                }
            }
            return mid;
        }
    }
}
