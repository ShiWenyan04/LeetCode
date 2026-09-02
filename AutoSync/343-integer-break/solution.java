class Solution {
    public int integerBreak(int n) {
        if(n == 1)return 0;
        if(n == 2)return 1;
        if(n == 3)return 2;
        int []f = new int [n+1];
        for(int i = 4;i <=n; i++){
            for(int j = 1; j <= i-j; j++){
                f[i] = Math.max(f[i],Math.max(j*(i-j),j*f[i-j]));
            }
        }
        return f[n];
    }
}
