class Solution {
    public static int lengthOfLastWord(String str) {
        int n = str.length();
        int cnt = 0;
        for(int i = n-1;i>=0;i--){
             if(cnt==0 && str.charAt(i)==' '){
                continue;
            }else if(str.charAt(i)==' '){
                break;
            }
            cnt++;
        }
        return cnt;
    }
}
