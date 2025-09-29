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
        if(i+1 == j){
            return 0;
        }
        if(memo[i][j] != -1){
            return memo[i][j];
        }
        int resMin = Integer.MAX_VALUE;
        for (int k = i+1; k <= j; k++) {
            int sum = 0;
            sum = dfs(i,k,v,memo)+dfs(k,j,v,memo)+v[i]*v[j]*v[k];
            resMin = Math.min(resMin,sum);
        }
        return resMin == Integer.MAX_VALUE ? -1 : resMin;
    }

}
