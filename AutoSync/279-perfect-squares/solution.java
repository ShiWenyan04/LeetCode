class Solution {
    public int numSquares(int n) {
        return Method(n);
    }
    public static int Method(int n){
        int []f = new int[n+1];
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 1; j*j <= i; j++) {
                min = Math.min(min,f[i-j*j]);
            }
            f[i] = min+1;
        }
        return f[n];
    }
}
