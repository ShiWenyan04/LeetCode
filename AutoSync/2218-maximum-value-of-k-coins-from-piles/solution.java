class Solution {
    public int maxValueOfCoins(List<List<Integer>> piles, int k) {
        return Method(piles,k);
    }
     public static int Method(List<List<Integer>> list,int k){
        int []f = new int[k+1];//每一位的答案
        int sumN = 0;//表示每一堆的最多能选多少个
        for (List<Integer>  Pile: list) {
            Integer []pile = Pile.toArray(new Integer[0]);//换成数组更快计算
            int n = pile.length;
            for (int i = 1; i < n; i++) {//求当前堆的前缀和
                pile[i] += pile[i-1];
            }
            sumN = Math.min(sumN+n,k);//表示每一堆的最多能选多少个，不能超过k
            for (int i = sumN; i > 0 ; i--) {//从当前堆的最大数开始反向遍历
                for (int j = 0; j < Math.min(n,i); j++) {//表示当前堆选多少个硬币，最多不能超过sumN，也不能超过当前堆的个数
                    f[i] = Math.max(f[i],f[i-j-1]+pile[j]);
                }
            }
        }
        return f[k];
    }
}
