class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        return Method(matrix,target);
    }
    public static boolean Method(int[][] matrix, int target) {
        for (int i = 0; i < matrix.length; i++) {
            if (matrix[i].length == 1){
                if (target == matrix[i][0]){
                    return true;
                }else if(i == matrix.length-1){
                    break;
                }else {
                    continue;
                }
            }
            if (target == matrix[i][0] || target == matrix[i][matrix[i].length - 1]) {
                return true;
            } else if (target > matrix[i][0] && target < matrix[i][matrix[i].length - 1]) {
                int left = 0;
                int right = matrix[i].length - 1;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (matrix[i][mid] == target) {
                        return true;
                    } else if (matrix[i][mid] > target) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }
            }
        }
        return false;
    }
}
