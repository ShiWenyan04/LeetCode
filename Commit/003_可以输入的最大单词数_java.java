class Solution {
    public static int canBeTypedWords(String text, String brokenLetters) {
        HashSet<Character> set = new HashSet<>();
        for (char c : brokenLetters.toCharArray()) {
            set.add(c);
        }
        boolean flag = true;
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if(text.charAt(i) == ' '){
                if(flag){
                    count++;
                }
                 flag = true;
            }
            else if (set.contains(text.charAt(i))) {
                flag = false;
            }
        }
        if(flag){
            count++;
        }
        return count;
    }
}