class Solution {
     public static boolean isHappy(int num) {
        if(num == 1){
            return true;
        }
        int slow = num;
        int fast = getnext(num);
        while (slow != fast) {
            slow = getnext(slow);
            fast = getnext(getnext(fast));
            if (slow ==1 || fast==1) {
                return true;
            }
        }
        return false;
    }
    public static int getnext(int num){
        int total = 0;
        while(num != 0){
            int one = num % 10;
            total += one*one;
            num /= 10;
        }
        return total;
    }
}
