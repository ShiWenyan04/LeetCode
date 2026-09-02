class Solution {
    public static List<String> removeAnagrams(String[] words) {
        List<String> ans = new ArrayList<>();
        ans.add(words[0]);

        for(int i = 1 ; i < words.length ; i++){
            if(!judge(words[i],words[i-1])){
                ans.add(words[i]);
            }
        }
        
        return ans;
    }
    public static boolean judge(String s1, String s2) {
        int [] ch =  new int[26];
        for(char c : s1.toCharArray()){
            ch[c-'a']++;
        }
        for(char c : s2.toCharArray()){
            ch[c-'a']--;
        }
        for(int i = 0; i < 26; i++){
            if(ch[i] != 0){
                return false;
            }
        }
        return true;
    }
}
