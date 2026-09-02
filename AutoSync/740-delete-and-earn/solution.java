class Solution {
    public int deleteAndEarn(int[] nums) {
        return Method(nums);
    }
    public static int Method(int [] nums){
        int maxVal = Integer.MIN_VALUE;
        // 找到数组中最大的值
        for (int m : nums) {
            maxVal = Math.max(maxVal,m);
        }
        // sum[i] 表示值为 i 的元素在数组中的总点数
        int [] sum = new int[maxVal+1];
        for (int i = 0; i < nums.length; i++) {
            sum[nums[i]] +=nums[i];
        }
        // 动态规划数组 f
        int []f = new int[maxVal+2];// 需要 +2 来处理 f[i-2] 的情况
        f[1] = sum[1];// 初始化 f[1] 为 sum[1]
        for (int i = 2; i < maxVal+1; i++) {
            f[i] = Math.max(f[i-1],f[i-2]+sum[i]);
        }
        return f[maxVal];
    }
}
