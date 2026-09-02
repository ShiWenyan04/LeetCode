class Solution {
    public static int minOperations(int[] nums) {
        int n = nums.length;
//        两种特殊情况
//        第一种，所有的最大公约数不等于1，那么这道题无解返回-1
//        第二种，如果数组中有一个1，那么这个1可以向两边扩散进行计算，那么要操作n-1此操作
//        第二种，如果数组中有多个1，那么所有的1只需要向左边计算，最后一个1不仅向左边计算，也要向右边计算，最后的次数为n-cnt（1的个数）
        int gcdAll = 0,cnt1 = 0;
        for(int x : nums){
            gcdAll = gcd(gcdAll,x);
            if(x == 1){
                cnt1++;
            }
        }
        if(gcdAll!=1){
            return -1;
        }
        if(cnt1!=0){
            return n-cnt1;
        }

//        正常情况：
//        我们只要有一个1，就可以得到答案。
//        可以先找到一个最大公约数为1的子数组，由于要最少次数，所以子数组的个数要最短minLen。
//        在这个子数组中，将其化为1的次数应该是minLen-1。
//        又因为上述操作得到一个1，然后就可以按照有1的方法进行计算，及n-1
//        综上，总次数为minLen-1+n-1
        int minLen = n;
        for (int i = 0; i < n; i++){
            int g = 0;
            for (int j = i; j < n; j++){
                g = gcd(g,nums[j]);
                if(g == 1){
                    minLen = Math.min(minLen,j-i+1);
                    break;
                }
            }
        }
        return minLen-1+n-1;
    }
    public static int gcd(int a,int b){
        while(a!=0){
            int tmp = a;
            a = b%a;
            b = tmp;
        }
        return b;
    }
}
