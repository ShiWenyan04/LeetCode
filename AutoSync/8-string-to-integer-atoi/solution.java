class Solution {
    public int myAtoi(String s) {
        char [] ch = s.toCharArray();
        int index = 0;
        boolean judge =false;
        StringBuilder sb = new StringBuilder();
        if (s.length() == 1 && Character.isDigit(ch[index])){
            return Integer.valueOf(s);
        }
        while(index < s.length() &&  (ch[index] == ' ')) {//去除前导空格
            index++;
        }
        if (index==s.length()){//去除前导空格后到了末尾
            return 0;
        }
        if (ch[index] == '-' || ch[index] == '+'){//开头有符号且为“-”"+"
            sb.append(ch[index]);
            index++;
            if (s.length() == 1 || !Character.isDigit(ch[index])){//如果字符串为1且还是符号，或者下一个字符仍为字符
                return 0;
            }
        }else if (!Character.isDigit(ch[index]) && ch[index] != '-' && ch[index] != '+'){//开头为其他符号
            return 0;
        }
        while(index < s.length() && ch[index] == '0'){//去除前端无效0
            index++;
        }
        while (index < s.length() && Character.isDigit(ch[index])){//记录数字
            sb.append(ch[index]);
            index++;
            judge = true;
            if (Long.parseLong(sb.toString()) >= Integer.MAX_VALUE){
                return Integer.MAX_VALUE;
            }
            if (Long.parseLong(sb.toString()) <= Integer.MIN_VALUE){
                return Integer.MIN_VALUE;
            }
        }
        if (!judge){
            return 0;
        }else return Integer.valueOf(sb.toString());
    }
}
