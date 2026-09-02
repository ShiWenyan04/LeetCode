class Solution {
    public int numDecodings(String s) {
        return Method(s);
    }
    public static int Method(String s){
        int n = s.length();
        int []f = new int[n+1];
        f[0] = 1;
        int i = 1;
        while (i <= n){
            if(s.charAt(i-1) != '0'){
                f[i] += f[i-1];
            }
            if( i>1 && s.charAt(i-2) != '0'&& (s.charAt(i-1)-'0')+(s.charAt(i-2)-'0')*10 <= 26 ){
                f[i] += f[i-2];
            }
            i++;
        }
        return f[n];
    }
}
