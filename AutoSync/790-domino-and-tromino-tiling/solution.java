class Solution {
    public int numTilings(int n) {
        return Method(n);
    }
    public static int Method(int n){
        int [] f = new int[n+1];
        f[0] = 1;
        f[1] = 1;
        if(n >= 2){
            f[2] = 2;
        for (int i = 3; i < n+1; i++) {
            f[i] = ((2*f[i-1])%1000000007 + f[i-3])%1000000007;
        }
        }
        return f[n];
    }
}
