class Solution {
    public int countNumbersWithUniqueDigits(int n) {
        return Method(n);
    }
     public static int Method(int n){
        if (n == 0){
            return 1;
        } else if (n==1) {
            return 10;
        }
        int ans = 9;
        int nums[] = new int[n];
        nums[0] = 10;
        for (int i = 0; i < n-1; i++) {
            ans *= (Method2(9-i))/(Method2(9-i-1));
            nums[i+1] = ans + nums[i];
        }
        return nums[nums.length-1];
    }
    public static int Method2(int k){
        int j = 1;
        for (int i = 0; i < k; i++){
            j= j * (i+1);
        }
        return j;
    }
}
