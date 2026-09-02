class Solution {
    public boolean isPalindrome(int number) {
         String str = Method1(number);
         return Method2(str);

    }
    //    形成字符串
    public static String Method1(int number){
        String str = String.valueOf(number);//int 转 字符串
        return str;
    }
    //    遍历判断
    public static boolean Method2(String str){
        boolean judge = str.length() == 1;
            if (str.length()%2 == 0 ) {//字符串长度为偶数时

                if (str.substring( 0 ,( str.length()/2) ) .equals( Method3(str.substring( str.length()/2 , str.length())))){
                    judge = true;
                }
            }else {//字符串为奇数
                if (str.substring( 0 , str.length()/2 ).equals( Method3(str.substring( (str.length()/2)+1 , str.length()))))
                judge = true;
            }
        return judge;
    }
    public static String Method3(String str) {
        StringBuilder sb = new StringBuilder(str);
        return sb.reverse().toString();
    }
}
