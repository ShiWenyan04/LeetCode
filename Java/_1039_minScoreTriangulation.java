package Java;

import com.sun.security.jgss.GSSUtil;

import java.util.Arrays;

public class _1039_minScoreTriangulation {
    public static void main(String[] args) {
        int []values = {};
        int n = values.length;
        int [][] memo = new int [n][n];
        for(int [] a: memo){
            Arrays.fill(a,-1);
        }
        System.out.println(dfs(0,n-1,values,memo));
    }
    public static int dfs(int i,int j,int[] v,int[][] memo) {
        if(i+1 == j){// 只有两个点，无法组成三角形
            return 0;
        }
        if(memo[i][j] != -1){// 之前计算过
            return memo[i][j];
        }
        int resMin = Integer.MAX_VALUE;
        // 尝试所有可能的中间顶点k，将多边形i...j分割为i...k和k...j两部分
        for (int k = i+1; k < j; k++) {
            // 递归计算两部分的最小得分，加上当前三角形(i,j,k)的得分
            int sum = dfs(i,k,v,memo)+dfs(k,j,v,memo)+v[i]*v[j]*v[k];
            resMin = Math.min(resMin,sum);
        }
        return memo[i][j]=resMin  ;// 将计算结果存入记忆化数组，避免重复计算
    }

}
