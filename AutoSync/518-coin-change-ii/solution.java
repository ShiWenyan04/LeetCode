class Solution {
    public int change(int amount, int[] coins) {
        return Method(amount,coins);
    }
    public static int Method(int amt,int []coins){
        int []f = new int[amt+1];
        f[0] = 1;
        for (int coin : coins) {
            for (int i = coin; i <= amt; i++) {
                f[i] += f[i-coin];
            }
        }
       return f[amt];
    }
}
