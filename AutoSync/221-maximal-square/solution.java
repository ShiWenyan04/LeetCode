class Solution {
    public int maximalSquare(char[][] matrix) {
        return Method(matrix);
    }
   public static int Method(char[][] matrix){
        int ans = 0;
        int n = matrix.length,m = matrix[0].length;
        int [][]f = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(matrix[i][j] == '1'){
                    if (i == 0 || j==0){
                        f[i][j] = 1;
                    }else {
                        f[i][j] = Math.min(f[i][j-1],Math.min(f[i-1][j],f[i-1][j-1]))+1;
                    }
                    ans = Math.max(f[i][j],ans);
                }
            }
        }
        return ans*ans;
    }
}
