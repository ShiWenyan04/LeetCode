class Solution {
    public String largestNumber(int[] nums) {
        return Method(nums);
    }
    public static String Method(int[] nums){
      Integer[] str = new Integer[nums.length];
        int j = 0;
        for (int i: nums){
            str [j] = i ;
            j++;
        }
        StringBuilder sb = new StringBuilder();
        Arrays.sort(str,(a,b)->{//str[] = [3,30,34,5,9]     str1 = 3,str2 = 30      tr1+str2 = 330,str2+str1=303,前者大于后者，所以str1排在str2之前，以此类推
            String str1 = a.toString();
            String str2 = b.toString();
            return (str2+str1).compareTo(str1+str2);
        });
        if(str[0] == 0){//排序后首位元素为0，说明str[]所有都为0，可组成的最大字符串的首位也是0，实际就是都为0
            return "0";
        }
        for (Integer s : str){//sb连接
            sb.append(s);
        }
        return String.valueOf(sb);
    }
}
