class Solution {
    public int coinChange(int[] coins, int amount) {
        return Method(coins,amount);
    }
    public static int Method(int [] coins,int amt){
        int [] f = new int[amt+1];
        Arrays.fill(f,amt+1);
        f[0] = 0;
        for (int i = 1; i <= amt ; i++) {
            for (int coin : coins) {
                if(coin <= i){
                    f[i] = Math.min(f[i],f[i-coin]+1);
                }
            }
        }
        return f[amt] > amt?-1:f[amt];
    }
}
