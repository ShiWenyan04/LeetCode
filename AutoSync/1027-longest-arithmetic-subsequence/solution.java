class Solution {
    public int longestArithSeqLength(int[] nums) {
       
        return Method(nums);
    }
     public static int Method(int [] nums){
        int minv = Arrays.stream(nums).min().getAsInt();
        int maxv = Arrays.stream(nums).max().getAsInt();
        int diff = maxv-minv;
        int maxLen = 1;

        for (int d = -diff; d <= diff; d++) {
            int []f = new int[maxv+1];
            Arrays.fill(f,-1);
            for (int j = 0; j < nums.length; j++) {
                int pre = nums[j] - d;
                if(pre <= maxv && pre >= minv && f[pre]!=-1){
                    f[nums[j]] = Math.max(f[pre]+1,f[nums[j]]);
                    maxLen = Math.max(maxLen,f[nums[j]]);
                }
                f[nums[j]] = Math.max(1,f[nums[j]]);
            }
        }
        return maxLen;
    }
}
