class Solution {
    public String countAndSay(int n) {
return Method(n);
    }
     public static String Method(int n){
        if (n == 1){//当n为一时，形成编码默认为1
            return String.valueOf(n);
        }
        String s = "1";

        for (int i = 1; i < n; i++) {//次数
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < s.length(); j++) {//从首位开始判断行程长度编码
                int count = 1;//初始化数字个数为1
                while(j+1<s.length() && s.charAt(j) == s.charAt(j+1)){//确保下一个数字不超过界限，且当前数字是否与下一个数字相同，以此来决定行程长度编码
                    count++;
                    j++;
                }
                sb.append(count).append(s.charAt(j));//count为该数字个数，格式为：数字个数+数字
            }
            s = String.valueOf(sb);//化成字符串赋给s，再重新进行判断，但绝对不能超过次数n
        }
        return s;
    }
}
