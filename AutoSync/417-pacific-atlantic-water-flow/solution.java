class Solution {
    public List<List<Integer>> pacificAtlantic(int[][] heights) {
        return Method(heights);
    }
    public static List<List<Integer>> Method(int [][] height){
        int n = height.length,m = height[0].length;
        List<List<Integer>> list = new ArrayList<>();
        boolean [][]tai = new boolean[n][m];
        boolean [][]da = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            dfs(height,tai,i,0);
            dfs(height,da,i,m-1);
        }
        for (int i = 0; i < m; i++) {
            dfs(height,tai,0,i);
            dfs(height,da,n-1,i);
        }
        for (int i = 0;i < n;i++){
            for (int j = 0; j < m; j++) {
                if(tai[i][j] && da[i][j]){
                    list.add(Arrays.asList(i,j));
                }
            }
        }
        return list;
    }
    public static void dfs(int [][] height,boolean [][] search ,int i,int j){
        int n = height.length,m = height[0].length;
        if(search[i][j]){
            return;
        }
        search[i][j] = true;
        if(j-1 >= 0 && height[i][j-1]>=height[i][j]) dfs(height,search,i,j-1);
        if(j+1 <= m-1 && height[i][j+1]>=height[i][j]) dfs(height,search,i,j+1);
        if(i-1 >= 0 && height[i-1][j]>=height[i][j])dfs(height,search,i-1,j);
        if(i+1 <= n-1 && height[i+1][j]>=height[i][j]) dfs(height,search,i+1,j);
    }
}
