class Solution {
    public int maxVowels(String s, int k) {
        int l = s.length();
        int i = 0,j = 0;
        int cnt = 0;
        int ans = 0;
        String str = "aeiou";
        while(j < l){
            char ch = s.charAt(j);
            if(str.indexOf(ch)>=0){
                cnt++;
            }
            j++;

            while(j - i  > k){
                if(str.indexOf(s.charAt(i)) >= 0){
                    cnt--; 
                }
                i++;
            } 
            ans = Math.max(ans,cnt);
        }
        return ans;
    }
}
