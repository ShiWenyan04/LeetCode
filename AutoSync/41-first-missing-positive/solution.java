class Solution {
    public int firstMissingPositive(int[] nums) {
        return Method(nums);
    }
       public static int Method(int [] nums){
        int len = nums.length;
        int temp;
        for (int i = 0; i < len; i++) {
//遍历的目的是让数字（大于0，且小于等于数组长度）从1开始排序，且对应的下标是从0开始，即x的位置为x-1，倘若对应不上那就是缺失的正整数，若都对应的上，那就是数组长度减一
            while ((0 < nums[i]&& nums[i] <= nums.length) && nums[i] != nums[nums[i]-1]){
//不用if，而用while，因为有两个数换完位置，其中一个可能到不了正确的位置
                temp = nums[nums[i]-1];
                nums[nums[i]-1] = nums[i] ;
                nums[i] = temp;
            }
        }
        for (int i = 0 , target = 1; i < len; i++,target++) {//缺失最小正整数
            if (nums[i] != target){
               return target;
            }
        }
        return len + 1;
    }
}
