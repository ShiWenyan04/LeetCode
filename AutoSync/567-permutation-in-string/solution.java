class Solution {
    public boolean checkInclusion(String s1, String s2) {
        int m = s1.length();
        if(m > s2.length()){
            return false;
        }

        int[] cntS1 = new int[26];
        for (char c : s1.toCharArray()) {
            cntS1[c - 'a']++;
        }

        char[] s = s2.toCharArray();
        int [] cnt = new int [26];
        for(int i = 0; i < s.length;i++){
            cnt[s[i]-'a']++;
            if(i < m-1){
                continue;
            }

            if(Arrays.equals(cntS1,cnt)){
                return true;
            }
            cnt[s[i-m+1]-'a']--;
        }
        return false;
    }
}
