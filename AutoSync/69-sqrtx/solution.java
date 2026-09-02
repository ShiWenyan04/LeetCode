class Solution {
    public int mySqrt(int x) {
        return Method(x);
    }
     public static int Method(int x){
        int left = 1, right = (x/2);
        int  mid;
        if (x == 1||x==0){
            return x;
        }
        while(left <= right){
            mid = (left + right) / 2 ;
            int temp = x / mid ;
            if ( mid < temp ) {//相当于 mid*mid<x
                left = mid + 1; //  区间向大值区域缩进
            }if (mid == temp ){
                return mid ;
            }if (mid > temp ){//相当于 mid*mid>x
                right = mid - 1;//区间向小值区域缩进
            }
        }
        return left - 1;
    }
}
