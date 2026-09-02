class Solution {
    public int minimumDeleteSum(String s1, String s2) {
        return Method(s1,s2);
    }
    public static int Method(String s1,String s2){
        int n = s1.length(),m = s2.length();
        int [][]f = new int[n+1][m+1];
         for (int i = 1; i < n+1; i++) {
             f[i][0] = f[i-1][0]+s1.codePointAt(i-1);
         }
         for (int j = 1; j < m+1; j++) {
             f[0][j] = f[0][j -1]+s2.codePointAt(j-1);
         }
         for (int i = 1; i < n+1; i++) {
             int value1 = s1.codePointAt(i-1);
             for (int j = 1; j < m+1; j++) {
                 int value2 = s2.codePointAt(j-1);
                 if(s1.charAt(i-1) == s2.charAt(j-1)){
                     f[i][j] = f[i-1][j-1];
                 }else {
                     f[i][j] = Math.min(f[i-1][j]+value1,f[i][j-1]+value2);
                 }
             }
         }
         return f[n][m];
     }
}
