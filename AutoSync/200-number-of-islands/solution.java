class Solution {
    public int numIslands(char[][] grid) {
        return Method(grid);
    }
     public static int Method(char [][]grid){
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int hang = grid.length;
        int lie = grid[0].length;
        int ans = 0;
        for (int i = 0; i < hang; i++) {
            for (int j = 0; j < lie; j++){
                if(grid[i][j] == '1' ){
                   ans++;
                   dfs(grid,i,j);
                }
            }
        }
        return ans;
    }

    public static void dfs(char [][]grid,int i,int j){
        int hang = grid.length;
        int lie = grid[0].length;
        if(i<0 || i == hang || j < 0 || j ==lie || grid[i][j] == '0'){
            return;
        }
        grid[i][j] ='0';
        dfs(grid,i+1,j);
        dfs(grid,i-1,j);
        dfs(grid,i,j+1);
        dfs(grid,i,j-1);
    }
}
