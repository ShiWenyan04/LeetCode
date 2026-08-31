class Solution {
    public int countOfSubstrings(String word, int k) {
        int ans  = 0;
        HashSet<Character> hashSet = new HashSet<>();
        for (int i = 0; i < word.length(); i++) {
            int countFu = 0;
            for (int l = i; l < word.length() && countFu <= k; l++) {
                if (isVowel(word.charAt(l))){//判断是否为aeiou中的一个
                    hashSet.add(word.charAt(l));//存哈希
                }else {
                    countFu++;//为辅音，辅音个数加一
                }
                if (countFu == k && hashSet.size() == 5){//当元音个数为5，辅音个数为k时答案加一
                    ans ++;
                }
            }
            hashSet.clear();
        }
        return (ans);
    }
    public static boolean isVowel(char ch){//判断是否为aeiou中的一个
        return ch == 'a'||ch == 'e'||ch == 'i'||ch == 'o'||ch == 'u';
    }
}
