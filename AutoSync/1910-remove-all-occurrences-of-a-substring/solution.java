class Solution {
    public String removeOccurrences(String s, String part) {
        return Method(s,part);
    }
    public static String Method(String s, String part){
        while(s.contains(part)) {
            for (int i = 0; i < s.length(); i++) {//
                if (part.equals(s.substring(i, i + part.length()))) {//三个为一组，查看是否与part相等
                    s = s.substring(0, i) + s.substring(i + part.length());//改组前一半+改组后一半，等于新s
                    break;//减去part后从头开始查找是否还有part存在s中
                }
            }
        }
        return s;
    }
}
