class Solution {
    public boolean exist(char[][] board, String word) {
      return Method1(board,word);
    }

    public static boolean Method1(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;
        boolean [][]used = new boolean[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                // 开头开始匹配
                if (board[i][j]==word.charAt(0)) {
                    if(Method2(board,word,m,n,i,j,used,0))
                        return true;
                }
            }
        }
        return false;
    }

    public static boolean Method2(char[][] board, String word, int m,int n,int i, int j, boolean [][]used, int index){
        if (board[i][j] == word.charAt(index)) {
            used[i][j] = true;
            if (
                    (index == word.length()-1)
                    || (i > 0 && !used[i-1][j]) && Method2(board,word,m,n,i-1,j,used,index+1)
                    || (i < m-1 && !used[i +1][j]) && Method2(board,word,m,n,i+1,j,used,index+1)
                    || (j > 0 && !used[i][j-1]) && Method2(board,word,m,n,i,j-1,used,index+1)
                    || (j < n-1 && !used[i][j+1]) && Method2(board,word,m,n,i,j+1,used,index+1)
            )return true;
            used[i][j] = false;
        }
        return false;
    }
}
