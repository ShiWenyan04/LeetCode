class Solution {
   public static int peopleAwareOfSecret(int n, int delay, int forget) {
        int mod = 1_000_000_007; // 取余常数，避免数值溢出
        int[] know = new int[n+1];
        // known[i]：恰好在第i天新得知秘密的人数，下标从1到n（第0天无意义）
        know[1] = 1;
        long ans = 0;
        for (int i = 1; i <= n; i++) {
            //判断第i天新得知秘密的人，在第n天是否仍未忘记
            if(i >= n - forget + 1){
                ans+=know[i];// 加入结果
            }
            //计算第i天的人，会在哪些天分享秘密，并更新对应天数的新人数
            //分享开始天：i+delay，分享结束天：i+forget-1（且不超过n）
            for (int j = i+delay; j <= Math.min(i+forget-1,n) ;j++){
                know[j] = (know[j]+know[i])%mod;
            }
        }
        return (int)(ans%mod);
    }
}