class Solution {
    public int countSquares(int[][] matrix) {
        return Method(matrix);
    }
    public static int Method(int [][]matrix){
        int ans = 0;
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            return ans;
        }
        int [][]dp = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length;i++){
            for (int j = 0; j < matrix[0].length; j++) {
                if(matrix[i][j] == 1){
                    if (i == 0 || j == 0){
                        dp[i][j] = 1;
                    }else {
                        dp[i][j] = Math.min(dp[i-1][j],Math.min(dp[i][j-1] , dp[i-1][j-1]))+1;
                    }
                }
                ans += dp[i][j];
            }
        }
        return ans;
    }
}
