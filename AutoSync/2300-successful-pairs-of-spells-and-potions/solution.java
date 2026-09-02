class Solution {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        return Method(spells,potions,success);
    }
    public static int[] Method(int []spells,int []potions,long success){
        Arrays.sort(potions);
        int []ans = new int[spells.length];
        int m = potions.length;
        for (int i = 0; i < spells.length; i++) {
            int num = spells[i];
            int left = 0,right = m-1;
            while(left<=right){
                int mid = (left+right)/2;
                if((long)potions[mid]*num >= success){
                    right = mid-1;
                }else {
                    left = mid+1;
                }
            }
            ans[i] = m-left;
        }
        return ans;
    }
}
