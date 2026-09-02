class Solution {
    public int equalSubstring(String s, String t, int maxCost) {
        return Method(s,t,maxCost);
    }
    public static int Method(String s,String t,int k){
        int n = s.length();
        char [] sch = s.toCharArray();
        char [] tch = t.toCharArray();
        int right = 0,left = 0,cnt = 0;
        int maxLen = 0;
        while(right < n){
            cnt += Math.abs(sch[right]-tch[right]);
            right++;
            while (cnt > k){
                cnt-= Math.abs(sch[left]-tch[left]);
                left++;
            }
            maxLen = Math.max(maxLen,right-left);
        }
        return maxLen;
    }
}
