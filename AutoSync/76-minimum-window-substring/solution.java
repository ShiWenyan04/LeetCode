class Solution {
    public String minWindow(String s, String t) {
        int []hash = new int [128];
        for(char c : t.toCharArray()){
            hash[c]--;
        }

        int sLen = s.length(),tLen = t.length();
        int left_idx = -1;
        int count = 0,minLen = sLen+1;
        for(int left = 0,right = 0; right < sLen; right++){
            char c = s.charAt(right);
            if(hash[c] < 0){
                count++;
            }
            hash[c] ++;

            while(left < right && hash[s.charAt(left)] > 0){
                hash[s.charAt(left)]--;
                left++;
            }

            if(count == tLen && right-left+1 < minLen){
                minLen = right-left+1;
                left_idx = left;
            }
            
        }
        return left_idx >= 0 ? s.substring(left_idx,left_idx+minLen) : "";
    }
}
