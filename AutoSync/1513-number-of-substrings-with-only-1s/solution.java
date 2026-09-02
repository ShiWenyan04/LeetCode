class Solution {
    public static int numSub(String s) {
        long mod = 1000000007;
        int n = s.length();
        int i = 0;
        long cnt = 0,ans = 0;
        while(i <= n){
            if(i < n && s.charAt(i) == '1'){
                cnt++;
            }else{
                ans = (ans + cnt * (cnt + 1) / 2) % mod;
                cnt = 0;
            }
            i++;
        }
        return (int)ans;
    }
}
