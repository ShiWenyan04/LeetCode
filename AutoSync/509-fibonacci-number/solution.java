class Solution {
    public int fib(int n) {
        return Method(n);
    }
    public static int Method(int n){
        int f0 = 0,f1 = 1;
        int f = 1;
         if (n==0){
            return n;
        }
        for (int i = 2; i < n+1; i++) {
            f = f0 + f1;
            f0 = f1;
            f1 = f;
        }
        return f;
    }
}
