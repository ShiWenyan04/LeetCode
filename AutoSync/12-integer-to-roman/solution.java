class Solution {
    public static String intToRoman(int num) {
        String[][] r = new String[][]{
                {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"}, // 个位
                {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"}, // 十位
                {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"}, // 百位
                {"", "M", "MM", "MMM"}, // 千位
        };
        return r[3][num/1000]+r[2][num/100%10]+r[1][num/10%10]+r[0][num%10];

    }
}
