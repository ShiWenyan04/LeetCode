class Solution {
    public long[] distance(int[] nums) {
        return Method(nums);
    }
    public  static long []Method(int[] nums){
        HashMap<Integer, List<Integer>> hashMap = new HashMap<>();
        long []ans = new long[nums.length];
        for (int i = 0; i < nums.length; i++) {//相同数的下标和统一存放进哈希表中的同一个list里，即将相同数的下标存放在一起
            if (!hashMap.containsKey(nums[i])){
                hashMap.put(nums[i],new ArrayList<>());
            }
            hashMap.get(nums[i]).add(i);
        }

//              entrySet是 java中 键-值 对的集合，Set里面的类型是Map.Entry，一般可以通过map.entrySet()得到。
        for (Map.Entry<Integer, List<Integer>> value : hashMap.entrySet()) {
            List<Integer> list = value.getValue();//从一组键值对中获得 value
            int m = list.size();
            if (m == 1){//说明只有唯一的值存在，对应数组默认为0，所以返回就是答案
                continue;
            }
//          计算前缀和
            long [] sum = new long[m+1];
            for (int j = 0; j < m; j++) {
                sum[j+1] = sum[j] + list.get(j);
            }
//            遍历计算左、右距离，然后相加得最终答案，这里利用了公式，只需记住公式即可
            for (int i = 0; i < m; i++) {//此处i可以等于原数组当前值的左边的相同数个数
                long left = (long)list.get(i) * i -sum[i];//对应数组下标*当前下标左边个数-索引的前缀和
                long right = sum[m]-sum[i]-(long)list.get(i) * (m-i)  ;//对应数组下标*当前下标右边个数+最后一个下标的前缀和-当前下标后一位的前缀和
                ans[list.get(i)] = left + right;//根据已存索引，存放索引的值
            }
        }
        return ans;
    }
}
