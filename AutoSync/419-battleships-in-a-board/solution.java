class Solution {
    public int countBattleships(char[][] board) {
        return Method(board);
    }
     public static int Method(char [][]board){
        int n = board.length;
        int m = board[0].length;
        int ans = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++){
                if(board[i][j]=='X'){
                    ans ++;
                    dfs(board,i,j);
                }
            }
        }
        return ans;
    }
    public static void dfs(char[][]board ,int i,int j){
        int n = board.length;
        int m = board[0].length;
        if(i <0 || j < 0||i==n ||j==m || board[i][j]=='.'){
            return;
        }
        board[i][j] = '.';
        dfs(board,i+1,j);
        dfs(board,i-1,j);
        dfs(board,i,j+1);
        dfs(board,i,j-1);
    }
}
