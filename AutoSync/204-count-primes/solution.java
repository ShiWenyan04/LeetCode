class Solution {
    public int countPrimes(int n) {
        return Method(n);
    }
    public static int Method(int n){
        int ans =0;
        boolean []isPrime = new boolean[n];
        for (int i = 2; i < n; i++) {
            if(!isPrime[i]){//当前数为质数
                ans+=1;
                //当前数为质数时，那么它的倍数都为合数
                // 并且在统计合数时，可以从i*i开始统计，因为在此之前的合数都已经被前一个指数的倍数统计过了
                if((long) i*i < n){
                    for (int j = i*i; j < n ; j+=i) {
                        isPrime[j] = true;
                    }
                }
            }
        }
        return ans;
    }
}
