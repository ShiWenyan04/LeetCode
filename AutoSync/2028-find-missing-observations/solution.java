class Solution {
    public int[] missingRolls(int[] rolls, int mean, int n) {
        return Method(rolls,mean,n);
    }
     public static int[] Method(int [] nums,int mean,int n) {
        int [] ans = new int[n];
        int count = n+nums.length;
        int total = count * mean;
        int temp = total;
        for (int i = 0; i < nums.length; i++) {
            temp -= nums[i];
        }
        if (temp < n || temp > 6*n){//观察一次，对象的值区间在【1，6】，观察n次，对象的和的区间在【n，n*6】，不在这个区间返回空数组
            return new int[0];
        }
        if (temp >= n && temp <= 6*n) {//在这个区间，设每组平均值为aver，如果i<余数，aver+1，否则就为aver
            int aver = temp/n;
            for (int i = 0; i < n; i++) {
                if (i < temp % n) {
                    ans[i] = aver + 1;
                } else {
                    ans[i] = aver;
                }
            }
        }
        return ans;
    }
}
