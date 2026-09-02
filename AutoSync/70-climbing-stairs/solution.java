class Solution {
    public int climbStairs(int n) {
        return Method(n);
    }
     public static int Method(int n){
        int []f = new int[n+1];
        f[0] = 1;
        f[1] = 1;
        for (int i = 2; i < n+1 ; i++) {
            f[i] = f[i-1] + f[i-2];
        }
        return f[n];
    }
}
