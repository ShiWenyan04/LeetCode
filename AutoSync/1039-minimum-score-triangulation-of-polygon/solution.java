class Solution {
    public int minScoreTriangulation(int[] values) {
        int n = values.length;
        int [][] memo = new int [n][n];
        for(int [] a: memo){
            Arrays.fill(a,-1);
        }
        return (dfs(0,n-1,values,memo));
    }
    public static int dfs(int i,int j,int[] v,int[][] memo) {
        if(i+1 == j){// 只有两个点，无法组成三角形
            return 0;
        }
        if(memo[i][j] != -1){// 之前计算过
            return memo[i][j];
        }
        int resMin = Integer.MAX_VALUE;
        for (int k = i+1; k < j; k++) {
            int sum = dfs(i,k,v,memo)+dfs(k,j,v,memo)+v[i]*v[j]*v[k];
            resMin = Math.min(resMin,sum);
        }
        return memo[i][j]=resMin  ;// 记忆化
    }
}
