class Solution {
    public int findDuplicate(int[] strs) {
            int aim = 0;
        Arrays.sort(strs);
        for (int i = 0; i < strs.length; i++) {
            if (strs[i] == strs[i+1]) {
                aim = strs[i];
                break;
            } 
        }
        return aim;
    }
}
