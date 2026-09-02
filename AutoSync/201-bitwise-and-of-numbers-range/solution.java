class Solution {
    public int rangeBitwiseAnd(int left, int right) {
        int m = 32-Integer.numberOfLeadingZeros(left^right);
        return left&~((1<<m)-1);
    }
}
