class Solution {
    public List<List<Integer>> permute(int[] nums) {
 List<List<Integer>> list = new ArrayList<>();
        List<Integer> com = new ArrayList<>();
        boolean []visited = new boolean[nums.length];
        int n = nums.length;
        Method2(list,com,nums,visited, n);
        return list;
    }
    public static void Method2(List<List<Integer>> list,  List<Integer> com, int [] nums, boolean[] visited, int n ){
        if (com.size()==n){
            list.add(new ArrayList<>(com));
            return;
        }
        for (int index = 0; index < n; index++) {
            if (visited[index]) {
                continue;
            }else {
                visited[index] = true;
                com.add(nums[index]);
                Method2(list,com,nums,visited,n);
                com.remove(com.size()-1);
                visited[index] = false;
            }
        }
    }
}
