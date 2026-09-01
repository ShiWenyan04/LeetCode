class Solution {
    public static int smallestNumber(int n) {
//        计算最高位前导0的数量
        int cnt = 32-Integer.numberOfLeadingZeros(n);
        return (1<<cnt)-1;
//        左移cnt之后 再减一就是最大置位位数
    }
}
