class Solution {
    public void setZeroes(int[][] matrix) {
        int m = matrix.length;
        int n = matrix[0].length;
        boolean[] rowHasZero = new boolean[m]; // 行是否包含 0
        boolean[] colHasZero = new boolean[n]; // 列是否包含 0

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (matrix[i][j] == 0) {
                    rowHasZero[i] = colHasZero[j] = true;
                }
            }
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (rowHasZero[i] || colHasZero[j]) { // i 行或 j 列有 0
                    matrix[i][j] = 0; // 题目要求原地修改，无返回值
                }
            }
        }
    }
}
