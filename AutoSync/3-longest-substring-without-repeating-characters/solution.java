class Solution {
    public static int lengthOfLongestSubstring(String s) {
        int ans = 0;
        int n = s.length();
        if(n < 2){
            return n;
        }
        HashSet<Character> set = new HashSet<>();
        int right = 0,left = 0;
        while(right<n){
            while(set.contains(s.charAt(right))){
                set.remove(s.charAt(left));
                left++;
            }
            set.add(s.charAt(right));
            ans = Math.max(ans,right-left+1);
            right++;
        }
        return ans;
    }
}
