class Solution {
    public int minNumber(int[] nums1, int[] nums2) {
        return Method(nums1,nums2);
    }
    public static int Method(int [] nums1,int []nums2){
        HashSet<Integer> hashSet = new HashSet<>();
        int min1 = Integer.MAX_VALUE,min2 = Integer.MAX_VALUE;
        int ans = Integer.MAX_VALUE;
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < Math.max(nums1.length,nums2.length); i++) {
            if(i <nums1.length){//计算nums1的最小值，依次存入哈希表
                hashSet.add(nums1[i]);
                min1 = Math.min(min1,nums1[i]);
            }
            if(i < nums2.length){//计算nums2的最小值
                min2 = Math.min(min2,nums2[i]);
            }
        }
        for (int i = 0; i < nums2.length; i++) {
            if(hashSet.contains(nums2[i])){//判断是否有相同值，依次存入list
                list.add(nums2[i]) ;
            }
        }
        for(Integer i : list){//遍历list，寻求相同值中的最小值
            ans = Math.min(i,ans);
        }
        if (ans != Integer.MAX_VALUE){//ans改变，说明有相同的最小值出现
            return ans;
        }else {//否则就两个数列的最小值构成最小的两位数
            ans = Math.min(min1,min2)*10 + Math.max(min1,min2);
        }
        return ans;
    }
}
