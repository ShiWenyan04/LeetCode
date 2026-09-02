class Solution {
    public double myPow(double x, int n) {
        if(n == 0){
            return 1l;
        }
        if(n == 1){
            return x;
        }

        if(n<0){
            return pow(1/x,-(long) n);
        }
        return pow(x,n);
    }
    public double pow(double x,long n){
        if(n==0){
            return 1;
        }
        double half = pow(x,n/2);
        half*=half;
        if(n%2 != 0){
            half*=x;
        }
        return half;
    }
}
