class Solution {
    public int tribonacci(int n) {
        return Method(n);
    }
     public static int Method(int n){
        if (n == 0){
            return 0;
        }else if(n==1 || n == 2){
            return 1;
        }
         int []f = new int[n+1];
        f[0] = 0;
        f[1] = 1;
        f[2] = 1;
        for (int i = 3; i <= n; i++) {
            f[i] = f[i-3] + f[i-2] + f[i-1];
        }
        return f[f.length-1];
    }
}
