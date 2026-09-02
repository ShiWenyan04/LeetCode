class Solution {
    public int longestSubsequence(int[] arr, int difference) {
        return Method(arr,difference);
    }
     public static int Method(int []arr,int diff){
        int n = arr.length;
        HashMap<Integer,Integer> map = new HashMap<>();
        int maxLen = 1;
        for (int i = 0; i < n; i++) {
            map.put(arr[i],map.getOrDefault(arr[i]-diff,0)+1);
            maxLen = Math.max(maxLen,map.get(arr[i]));
        }
        return maxLen;
    }
}
