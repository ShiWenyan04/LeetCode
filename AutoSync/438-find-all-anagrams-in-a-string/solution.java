class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> ans = new ArrayList<>();
        int slen = s.length();
        int plen = p.length();
        int []cntp = new int [26];
        for(char c : p.toCharArray()){
            cntp[c-'a']++;
        }
        int[] cnt = new int [26];
        char[] sc = s.toCharArray();
        for(int j = 0; j < slen;j++){
            //直接滑动，右边新滑入一个字母
            cnt[sc[j] - 'a']++;
            int left = j-plen+1;//判断当前的窗口左边起点在哪儿
            if(left < 0){//起点小于0说明长度不够p的长度
                continue;
            }            
            if(Arrays.equals(cnt,cntp)){//两个数组如果相等就说明字母相同
                ans.add(left);//记录索引
            }
            cnt[sc[left]-'a']--;//左边的字母可以画出了
        }
        return ans;
    }
}
