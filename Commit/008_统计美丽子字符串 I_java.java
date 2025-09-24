class Solution {
    public int beautifulSubstrings(String s, int k) {
        return Method(s,k);
    }
    public static boolean judge(char c){
        return c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u';
    }
    public static int Method(String str,int k){
        int n = str.length();
        int ans = 0;

        for(int i = 0 ; i < n ; i++){
            int y_cnt = 0, f_cnt = 0;
            for(int j = i ; j < n ; j++){
                if(judge(str.charAt(j)) ){
                    y_cnt++;
                }else {
                    f_cnt++;
                }
                if(y_cnt == f_cnt && (y_cnt * f_cnt )% k == 0){
                    ans++;
                }
            }
        }
        return ans;
    }
}
