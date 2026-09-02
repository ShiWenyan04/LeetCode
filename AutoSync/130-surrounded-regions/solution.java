class Solution {
    public void solve(char[][] board) {
        Method(board);
    }
    public static char[][] Method(char[][] board){
        int n = board.length;
        int m = board[0].length;
         if (n == 0) {
            return board;
        }
//        处理矩阵的第一列和第n列，从这两列出发，寻找没有被包裹的面积
        for (int i = 0; i < n; i++) {
            dfs(board,i,0);
            dfs(board,i,m-1);
        }
//        处理矩阵的第一行和第n行，从这两行出发，寻找没有被包裹的面积，由于四个顶角已经被上一个循环处理过，所以可以不用再访问
        for (int i = 1; i < m-1; i++) {
            dfs(board,0,i);
            dfs(board,n-1,i);
        }
//        遍历矩阵，将标记过的位置换成O,没有被标记过的O换成X
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if(board[i][j] == 'A' ){
                    board[i][j] = 'O';
                } else if (board[i][j]=='O') {
                    board[i][j] = 'X';
                }
            }
        }
        return board;
    }
//    递归寻找没有被包裹的面积，
    public static void dfs(char[][] board ,int i,int j){
        int n = board.length;
        int m = board[0].length;
        if(i == n || j == m || i == -1 || j == -1 || board[i][j]!='O'){
            return ;
        }
        board[i][j] = 'A';
        dfs(board,i-1,j);
        dfs(board,i+1,j);
        dfs(board,i,j-1);
        dfs(board,i,j+1);
    }
}
