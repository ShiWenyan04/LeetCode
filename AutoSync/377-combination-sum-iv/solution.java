class Solution {
    public int combinationSum4(int[] nums, int target) {
        return Method(nums,target);
    }
    public static int Method(int [] nums,int target){
        int []f = new int[target+1];
        f[0] = 1;
        for (int i = 1; i <= target; i++) {
            for (int num : nums) {
                if(num <= i){
                    f[i] += f[i-num];
                }
            }
        }
        return f[target];
    }
}
