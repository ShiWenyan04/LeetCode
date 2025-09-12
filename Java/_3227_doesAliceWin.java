package Java;

public class _3227_doesAliceWin {
    public static void main(String[] args) {
        String s = "leetcode";
        System.out.println(Method(s));
    }
    public static boolean Method(String s){
        // 直接在循环中判断，一旦找到元音就返回true，无需统计总数
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == 'a' || c == 'e' || c == 'i' || c == 'o' || c == 'u') {
                return true;
            }
        }
        return false;
    }
}
