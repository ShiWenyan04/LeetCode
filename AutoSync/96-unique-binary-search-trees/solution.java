class Solution {
    public int numTrees(int n) {
        //用动态规划，其实就是前n个排列组合之和，去除重复的情况1-4，2-3，3-2，4-1
        int []f = new int [n+1];
        f[0] = 1;
        f [1] = 1;
        for(int i = 2;i <= n; i++){
            for(int j = 1;j <= i;j++){
                f[i] = f[i] +f[j-1]*f[i-j];
            }
        }
        return f[n];
    }
}
