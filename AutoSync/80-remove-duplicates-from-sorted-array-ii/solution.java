class Solution {
    public int removeDuplicates(int[] nums) {
        int n = nums.length;
        if(n <=2){
            return n;
        }

        int slow = 2;
        int fast = 2;
        for(;fast <n;fast++){
            if(nums[fast] != nums[slow-2]){
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;
    }
}
