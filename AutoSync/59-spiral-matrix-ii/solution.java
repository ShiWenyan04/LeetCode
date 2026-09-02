class Solution {
    public int[][] generateMatrix(int n) {
        return Method(n);
    }
    public static int [][] Method(int n){
        if (n == 1){
            return new int[][]{{1}};
        }
         boolean [][]visited = new boolean[n][n];
         int [][]num = new int[n][n];
         int i = 1;
         int row = 0, col = 0;
         while (i <= n*n){
//             行向右
             while (col<n && !visited[row][col] && i<=n*n){
                 num[row][col] = i;
                 visited[row][col] = true;
                 col++;
                 i++;
             }
             if (i > n*n){
                 break;
             }
//             列向下
             col--;
             row++;
             while(row < n && !visited[row][col]&& i<=n*n){
                 num[row][col] = i;
                 visited[row][col] = true;
                 row ++;
                 i++;
             }
             if (i > n*n){
                 break;
             }
//             行向左
             row--;
             col--;
             while(col >= 0 && !visited[row][col]&& i<=n*n){
                 num[row][col] = i;
                 visited[row][col] = true;
                 col--;
                 i++;
             }
             if (i > n*n){
                 break;
             }
//             列向上
             col++;
             row--;
             while(row >= 0 && !visited[row][col]&& i<=n*n){
                 num[row][col] = i;
                 visited[row][col] = true;
                 row --;
                 i++;
             }
             row++;
             col++;
         }
         return num;
    }
}
