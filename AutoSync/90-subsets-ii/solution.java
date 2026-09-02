class Solution {
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        return Method(nums);
    }
     public static List<List<Integer>> Method(int [] nums){
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> group = new ArrayList<>();
        HashSet<String> hashSet = new HashSet<>();
        Arrays.sort(nums);
        list.add(new ArrayList<>());
        for (int times = 1; times <= nums.length; times++) {
            Method2(nums, times, 0, list,group,hashSet);
        }
        return list;
    }
    public static void Method2(int[] nums,int times,int index, List<List<Integer>> list,List<Integer> group,HashSet<String> hashSet){
        if (group.size() == times && times != 0){
            StringBuilder s = new StringBuilder();
            for (int i = 0; i < group.size(); i++) {//将数字变为字符串，并用，隔开，造成集合的形象
                s.append((group.get(i))).append(",");
            }
            if (!hashSet.contains(s.toString())){//判断哈希表中是否存在这个s，存在即为重复
                hashSet.add(s.toString());
                list.add(new ArrayList<>(group));
            }
            return;
        }
        for (; index < nums.length; index++) {
            group.add(nums[index]);
            Method2(nums,times,index+1,list,group,hashSet);
            group.remove(group.size()-1);
        }
    }
}
