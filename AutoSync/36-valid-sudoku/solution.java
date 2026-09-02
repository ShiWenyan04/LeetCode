class Solution {
   public static boolean isValidSudoku(char[][] board) {
        int [][] hang = new int [9][9];
        int [][] lie = new int [9][9];
        int [][][] jiu = new int [3][3][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if(board[i][j] != '.') {
                    int num = board[i][j] - '0'-1;
                    hang[i][num]++;
                    lie[j][num]++;
                    jiu[i/3][j/3][num]++;
                    if(hang[i][num] > 1 || lie[j][num] > 1 || jiu[i/3][j/3][num] > 1) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
