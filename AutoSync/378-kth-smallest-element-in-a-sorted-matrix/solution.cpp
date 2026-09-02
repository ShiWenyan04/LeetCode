class Solution {
public:
    bool  check(vector<vector<int>>& matrix,int mid, int k,int n) {
        int i = n-1, j = 0;//从右下角开始遍历
        int sum = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {//对角线式遍历      设martrix[i][j]为x
                sum+=i+1;//因为是排序好 的二维矩阵，所以左上角板块小于右下角板块，当x小于mid时，这一列在x上面的 均小于mid，所以为i+1
                j++;//从前往后
            }else{
                i--;//从下往上
            }
        }
        return sum >= k;
    }
    int kthSmallest(vector<vector<int>>& matrix, int k) {
        int n = matrix.size();
        int left = matrix[0][0];//左上角的值
        int right = matrix[n - 1][n - 1];//右下角的值
        while (left < right) {
            int mid = left + ((right - left) >> 1);
            if (check(matrix, mid, k, n)) {//小于mid的个数大于等于k，说明第k小的值在mid左边
                right = mid;
            }else{
                left = mid+1;//大于mid 的个数小于k，说明mid太小了
            }
        }
        return left;
    }
};
