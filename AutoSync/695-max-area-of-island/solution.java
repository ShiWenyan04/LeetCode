class Solution {
    public int maxAreaOfIsland(int[][] grid) {
        return Method(grid);
    }
    public static int Method(int [][]num){
        int n = num.length;
        int m = num[0].length;
        int ans = 0;
        int []preans = new int[1];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(num[i][j] == 1){
                    dfs(num,preans,i,j);
                    ans = Math.max(ans,preans[0]);
                    preans[0] = 0;
                }
            }
        }
        return ans;
    }
    public static void dfs(int [][] num,int [] preans,int i,int j){
        int n = num.length;
        int m = num[0].length;
        if( i < 0 || j < 0 || i == n || j == m || num[i][j] == 0 ){
            return ;
        }
        num[i][j] = 0;
        preans[0]++;
        dfs(num,preans,i+1,j);
        dfs(num,preans,i-1,j);
        dfs(num,preans,i,j+1);
        dfs(num,preans,i,j-1);
    }
}
