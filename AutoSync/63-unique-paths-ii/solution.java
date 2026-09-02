class Solution {
    public int uniquePathsWithObstacles(int[][] obstacleGrid) {
        return Method(obstacleGrid);
    }
      public static int Method(int[][] obstacleGrid){
        int m = obstacleGrid.length;
        int n = obstacleGrid[0].length;
        int [][]f = new int[m][n];
        for (int i = 0; i < m; i++) {
            if (obstacleGrid[i][0] != 1){
                f[i][0] = 1;
            }else {
                for (int k = i; k < m ; k++) {
                    f[k][0] = 0;
                }
                break;
            }
        }
        for (int j = 0; j < n; j++) {
            if (obstacleGrid[0][j] != 1){
                f[0][j] = 1;
            }else {
                for (int k = j; k < n ; k++) {
                    f[0][j] = 0;
                }
                break;
            }
        }
        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n ; j++) {
                if (obstacleGrid[i][j] == 1){
                    f[i][j] = 0;
                }else{
                    f[i][j] = f[i-1][j] + f[i][j-1];
                }
            }
        }
        return f[m-1][n-1];
    }
}
