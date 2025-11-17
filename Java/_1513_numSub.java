package Java;

public class _1513_numSub {
    public static void main(String[] args) {
        String s = "0110111";
        System.out.println(numSub(s));
    }
    public static int numSub(String s) {
        int n = s.length();
        int mod = 1000000007;
        int i = 0;
        int cnt = 0,ans = 0;
        while(i < n){
            if( s.charAt(i) == '1'){
                cnt++;
                ans = (ans + cnt) % mod;
            }else{
                cnt=0;
            }
            i++;
        }
        return ans%mod;
    }
//    将每一截均为1的字符串的个数通过n*(n+1)/2得出，最后相加起来
    public static int numSub2(String s) {
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
