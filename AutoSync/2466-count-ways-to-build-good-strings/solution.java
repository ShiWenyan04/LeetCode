class Solution {
    public int countGoodStrings(int low, int high, int zero, int one) {
        return Method(low,high,zero,one);
    }
     public static int Method(int low,int high,int zero,int one){
        int []f = new int[high+1];
        int mod = 1000000007;
        f[0] = 1;
        int ans = 0 ;
        for (int i = 1; i <= high; i++) {
            if(i-zero >= 0){
                f[i] = f[i-zero];
            }
            if (i-one >= 0) {
                f[i] = (f[i] + f[i-one])%mod;
            }
            if(i >= low){
                ans = (ans+f[i])%mod;
            }
        }
        return ans;
    }
}
