class Solution {
    public int[] findPeakGrid(int[][] mat) {
        return Method(mat);
    }
    public static int[] Method(int[][] mat) {
        int n = mat.length;
        int m = mat[0].length;
        int up = 0, down = n-1, mid = 0;
        while (up <= down) {//从中间行列开始
            mid = (up + down) / 2;
            int max = -1,index1 = -1;
            for (int i = 0; i < m; i++) {//寻找中间行最大值及坐标
                if (mat[mid][i] > max) {
                    max = mat[mid][i];
                    index1 = i;
                }
            }
            if (mid >= 1 &&mat[mid][index1] < mat[mid-1][index1]) {
                down = mid-1;
                continue;
            }else if(mid < n-1 &&mat[mid][index1] < mat[mid+1][index1]){
                up = mid+1;
                continue;
            }
            return new int[]{mid,index1};
        }
        return new int[]{0};
    }
}
