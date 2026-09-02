class Solution {
    public int compress(char[] chars) {
        return Method(chars);
    }
    public static int Method(char [] chars){
        int n = chars.length;
        int count = 1;
        char ch = chars[0];
        StringBuilder sb = new StringBuilder(String.valueOf(ch));
        for (int i = 1; i < n; i++) {
            if(ch == chars[i]){
                count++;
                ch = chars[i];
            }else {
                if(count > 1){
                    sb.append(count);
                }
                count = 1;
                ch = chars[i];
                sb.append(ch);
            }
        }
        if(count > 1){
            sb.append(count);
        }
        int len = sb.length();
        for (int i = 0; i < len; i++) {
            chars[i] = sb.charAt(i);
        }
        return sb.length();
    }
}
