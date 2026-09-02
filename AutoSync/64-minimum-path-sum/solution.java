class Solution {
    public int minPathSum(int[][] grid) {
        return Method(grid);
    }
    public static int Method(int[][] grid) {
        int len1 = grid.length,len2 = grid[0].length;
        int i, j;
        for (j = 1; j < len2; j++) {
            grid[0][j] = grid[0][j] + grid[0][j - 1];
        }
        for (i = 1; i < len1; i++) {
            grid[i][0] = grid[i][0] + grid[i - 1][0];
        }
        for (i = 1; i < len1; i++) {
            for (j = 1; j < len2; j++) {
                grid[i][j] = Math.min(grid[i][j - 1], grid[i - 1][j]) + grid[i][j];
            }
        }
        return grid[len1-1][len2-1];
    }
}
