class Solution {
    public static int removeDuplicates(int[] nums) {
       // 数组为空，直接返回0
        if (nums == null || nums.length == 0) {
            return 0;
        }
        int i = 0,j =1;
        int n = nums.length;
        for(;j < n&& i+1<n; j++ ){
            if(nums[i] != nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }
        
        return i+1;
    }
}
