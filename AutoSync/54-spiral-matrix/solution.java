class Solution {
    public static List<Integer> spiralOrder(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        if (n < 1 || m<1){
            return new ArrayList<>();
        }
        boolean [][] judge = new boolean[n][m];
        List<Integer> list = new ArrayList<>(m*n);
        int [][] xy = {{0,1},{1,0},{0,-1},{-1,0}};
        int idx = 0;
        int i = 0 , j = 0;
        for(int z = 0; z < n*m;z++){
            list.add(matrix[i][j]);
            judge[i][j] = true;
            int x = i + xy[idx][0];
            int y = j + xy[idx][1];
            if(x >= n || x < 0 || y < 0|| y >= m || judge[x][y] ){
                idx = (idx+1)%4;
            }
            i+=xy[idx][0];
            j+=xy[idx][1];
        }
        return list;
    }
}
