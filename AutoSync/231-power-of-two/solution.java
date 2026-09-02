class Solution {
    public boolean isPowerOfTwo(int n) {
        if(n == 1){
            return true;
        }
        if(n%2 ==0 && n/2 == 1){
            return true;
        }
        if(n%2 !=0 || n == 0 ){
            return false;
        }
        boolean judge = isPowerOfTwo(n/2);
        return judge;
    }
}
