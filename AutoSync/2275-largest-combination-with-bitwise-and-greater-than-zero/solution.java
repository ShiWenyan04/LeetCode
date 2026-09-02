class Solution {
    public int largestCombination(int[] candidates) {
        return Method(candidates);
    }
    public static int Method(int [] can){
        int len = 0;
        for (int i = 0; i < 24; i++) {
            int preLen = 0;
            for (int num : can) {
                if((num & (1 << i)) != 0){
                    preLen++;
                }
            }
            len = Math.max(len,preLen);
        }
        return len;
    }
}
