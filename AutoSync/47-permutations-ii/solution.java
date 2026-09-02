class Solution {
    public List<List<Integer>> permuteUnique(int[] nums) {
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> com = new ArrayList<>();
        boolean []visited = new boolean[nums.length];
        int n = nums.length;
        Arrays.sort(nums);
        Method2(list,com,new HashSet(),nums,visited, n);
        return list;
    }
    public static void Method2(List<List<Integer>> list, List<Integer> com, HashSet hashSet, int [] nums, boolean[] visited, int n ){
        if (com.size()==n && !hashSet.contains(com)){
            list.add(new ArrayList<>(com));
            hashSet.add(com);
            return;
        }
        for (int index = 0; index < n; index++) {
            if (visited[index]) {
                continue;
            }else {
                visited[index] = true;
                com.add(nums[index]);
                Method2(list,com,hashSet,nums,visited,n);
                com.remove(com.size()-1);
                visited[index] = false;
            }
        }
    }
}
