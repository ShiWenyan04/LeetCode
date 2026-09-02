class Solution {
    public static int romanToInt(String s) {
        Map<Character, Integer> map = Map.of(//罗马字符对应数字
                'I', 1,
                'V', 5,
                'X', 10,
                'L', 50,
                'C', 100,
                'D', 500,
                'M', 1000
        );
        
        char[] ch = s.toCharArray();//罗马字符
        int ans = 0;
        for(int i = 0;i < ch.length-1;i++){
            int x = map.get(ch[i]);
            int y = map.get(ch[i+1]);
            if(x<y){//加-x
                ans-=x;
            }else{
                ans+=x;
            }
        }
        return ans+map.get(ch[ch.length-1]);
    }
}
