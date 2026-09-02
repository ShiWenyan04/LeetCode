class Solution {
    public int longestContinuousSubstring(String s) {
        return Method(s);
    }
     public static int Method(String s){
        int dp=1,ans = 0;
        int i=0;
        if (s.length() == 1){
            return 1;
        }
        while(i<s.length()-1){
            if ((int)s.charAt(i) == (int)s.charAt(i+1)-1){
                dp++;
            }else {
                dp = 1;
            }
            ans = Math.max(ans,dp);
            i++;
        }
        return ans;
    }
}
