class Solution {
    public static String sortVowels(String s){
      int n = s.length();
        Set<Character> ch = new HashSet<>(Arrays.asList('A','E','I','O','U','a','e','i','o','u'));
        int [] cnt = new int [58];
        Arrays.fill(cnt,-1);

        for(char c : ch){
            cnt[c-'A'] = 0;
        }

        for (int i = 0; i < n; i++) {
            if(ch.contains(s.charAt(i))){
                cnt[s.charAt(i)-'A']++;
            }
        }

        char[] arr = s.toCharArray(); // 将字符串转为字符数组，方便修改
        int idx = 0; // 用于遍历 cnt 数组，寻找下一个待填充的元音
        for (int i = 0; i < arr.length; i++) {
            int pos = arr[i] - 'A';
            if (cnt[pos] != -1) { // 当前位置是元音，需要替换为排序后的元音
                // 找到下一个有剩余频次的元音（cnt[idx] > 0）
                while (cnt[idx] <= 0) {
                    idx++;
                }
                // 将当前位置替换为对应的元音（idx + 'A' 还原为字符）
                arr[i] = (char)(idx + 'A');
                // 该元音的频次减 1
                cnt[idx]--;
            }
            // 若当前位置是辅音，直接保留原字符，不做修改
        }
        return new String(arr); // 将字符数组转回字符串返回
    }
}
