class Solution {
    public List<String> letterCombinations(String digits) {
       return Method1(digits);
    }
     public static ArrayList<String> Method1(String digits){
        ArrayList<String> list = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        int len = digits.length(),index=0;
        if ( len == 0 ){
            return list;
        }
        Method2(digits,list,sb,index);
        return list;
    }
    public static void Method2 (String digits , ArrayList<String> list , StringBuilder sb , int index){
        String []alphabet = {" ","","abc","def","ghi","jkl","mno","pqrs","tuv","wxyz"};

        int len = digits.length();
        if (sb.length() == len){
                list.add(sb.toString());
                return;
            }
        Integer num = Integer.valueOf(String.valueOf(digits.charAt(index)));//按键的数字对应着字母表的索引
        for (int i = 0; i < alphabet[num].length(); i++) {
            sb.append(alphabet[num].charAt(i));//按键所对应的字母拼接，sb.append用于将char参数的字符串表示形式附加到给定序列
            Method2(digits,list,sb,index+1);
            sb.deleteCharAt(index);
        }
    }
}
