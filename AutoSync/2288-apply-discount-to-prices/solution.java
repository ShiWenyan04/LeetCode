class Solution {
    public String discountPrices(String sentence, int discount) {
        return Method(sentence,discount);
    }
     public static String Method(String sentence,int discount) {
        double dis = (100 - discount) / 100.0;
        String[] ss = sentence.split(" ");
        String pattern = "[$][0-9]+";
        for (int i = 0; i < ss.length; i++) {
            if (ss[i].matches(pattern)) {
                ss[i] = String.format("$%.2f", Double.parseDouble(ss[i].substring(1)) * dis);
            }
        }
        return String.join(" ", ss);
    }
}
