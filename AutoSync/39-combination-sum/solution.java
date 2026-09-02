class Solution {
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        int result = 0;
        int index = 0;
        List<List<Integer>> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        Method(list1,list2,candidates,target,result,index);
        return list1;
    }

    public static void Method(List<List<Integer>> list1, List<Integer> list2, int[] candidates, int target, int result, int index) {
        if (result == target){
            list1.add(new ArrayList<>(list2));
            return;
        }
        for (; index < candidates.length ; index++) {
            if (result + candidates[index] <= target ){
                result += candidates[index];
                list2.add(candidates[index]);
                Method(list1,list2,candidates,target,result,index);
                list2.remove(list2.size()-1);
                result-=candidates[index];
            }
        }
    }
}
