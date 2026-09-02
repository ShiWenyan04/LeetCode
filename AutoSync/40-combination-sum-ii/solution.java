class Solution {
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        return Method1(candidates, target);
    }
    public static List<List<Integer>> Method1(int[] candidates, int target) {
        ArrayList<List<Integer>> list = new ArrayList<>();
        ArrayList<Integer> group = new ArrayList<>();
        Arrays.sort(candidates);
        Method2( candidates, target, list,  group, 0, 0);
        return list;
    }
    public static void Method2( int[] candidates, int target, ArrayList<List<Integer>> list, ArrayList<Integer> group, int start, int result){
        if (result == target){
            list.add(new ArrayList<>(group));
            return;
        }
        int index = start;
        while(index < candidates.length && result + candidates[index] <= target ){
            if (index > start && candidates[index] == candidates[index - 1]) {//遇见可以凑成target的重复数字跳过，进行下一个
                index++;
                continue;
            }
            result = result + candidates[index];
            group.add(candidates[index]);
            Method2(candidates,target,list,group,index+1,result);
            result -= group.get(group.size()-1);
            group.remove(group.size()-1);
            index++;
        }
    }
}
