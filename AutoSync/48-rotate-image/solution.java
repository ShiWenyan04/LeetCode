class Solution {
     public static int[][] rotate(int[][] arr) {
        int n = arr.length;
        for (int i = 0; i < n/2; i++) {
            for (int j = 0; j < n; j++) {
                int temp = arr[i][j];
                arr[i][j] = arr[n-i-1][j];
                arr[n-i-1][j] = temp;
            }
        }

        for(int i = 0; i < n; i++){
            for(int j = 0; j < i; j++){
                int temp = arr[i][j];
                arr[i][j] = arr[j][i];
                arr[j][i] = temp;
            }
        }
        return arr;
    }
}
