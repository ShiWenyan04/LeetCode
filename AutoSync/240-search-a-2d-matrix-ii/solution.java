class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        return Method(matrix,target);
    }
   public static boolean Method(int [][]matrix,int target){
        int len1 = matrix.length,len2 = matrix[0].length;
        for (int i = 0; i < len1; i++) {
            if (matrix[i][len2-1] > target && matrix[i][0] < target){
                int left = 0;
                int right = len2-1;
                while (left<right){
                    int mid = (right + left)/2;
                    if (matrix[i][mid] == target){
                        return true;
                    } else if (matrix[i][mid] < target) {
                        left = mid+1;
                    }else right = mid;
                }
            } else if (matrix[i][len2-1] == target || matrix[i][0] == target) {
                return true;
            }else continue;
        }
        return false;
    }
}
