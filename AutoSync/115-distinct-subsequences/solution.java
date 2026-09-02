class Solution {
    public int numDistinct(String s, String t) {
        return Method(s,t);
    }
    public static int Method(String s,String t){
        int m = s.length(), n = t.length();
        if (m < n) {
            return 0;
        }
        int[][] f = new int[m + 1][n + 1];
        for (int i = 0; i <= m; i++) {
            f[i][n] = 1;
        }
        for (int i = m - 1; i >= 0; i--) {
            char ch1 = s.charAt(i);
            for (int j = n - 1; j >= 0; j--) {
                char ch2 = t.charAt(j);
                if(ch1 == ch2){
                    f[i][j] = f[i+1][j+1]+f[i+1][j];
                }else {
                    f[i][j] = f[i+1][j];
                }
            }
        }
        return f[0][0];
    }
}
