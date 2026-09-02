class Solution {
    public String capitalizeTitle(String title) {
        return Method(title);
    }
    public static String Method(String title){
        String []words = title.split(" "); 
        int z = 0;
        while(z < words.length){
            words[z] = words[z].toLowerCase();
            if (words[z].length() > 2 ){//单词个数大于2时，首字母大写
                 words[z]=(char)(words[z].charAt(0)-32) + words[z].substring(1);
            }
             z++;
        }
           
        return String.join(" ",words);
    }
}
