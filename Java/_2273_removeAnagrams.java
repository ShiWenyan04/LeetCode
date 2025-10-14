package Java;

import java.util.ArrayList;
import java.util.List;

public class _2273_removeAnagrams {
    public static void main(String[] args) {
        String [] words = {"abba","baba","bbaa","cd","cd"};
        System.out.println(removeAnagrams(words));
    }
    public static List<String> removeAnagrams(String[] words) {
        List<String> ans = new ArrayList<>();
        ans.add(words[0]);
        for(int i = 1 ; i < words.length ; i++){
            //不是异位字符串就添加，是的话就删除（跳过）
            if(!judge(words[i],words[i-1])){
                ans.add(words[i]);
            }
        }

        return ans;
    }
    //判断两个字符串是否为异位字符串
    public static boolean judge(String s1, String s2) {
        int [] ch =  new int[26];
        for(char c : s1.toCharArray()){
            ch[c-'a']++;
        }
        for(char c : s2.toCharArray()){
            ch[c-'a']--;
        }
        for(int i = 0; i < 26; i++){
            if(ch[i] != 0){
                return false;
            }
        }
        return true;
    }
}
