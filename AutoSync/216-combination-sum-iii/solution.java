class Solution {
    public List<List<Integer>> combinationSum3(int k, int n) {
        return Method(k,n);
    }
     public static List<List<Integer>> Method(int k, int n) {
        int [] nums = {1,2,3,4,5,6,7,8,9};
        List<List<Integer>> list = new ArrayList<>();
        List<Integer> com = new ArrayList<>();
        if (k > n || (k <n && k ==9 && n != 45)){
            return list;
        }
        Method2(k,n,nums,list,com,0,0);
        return list;
    }
    public static void Method2(int k,int n,int[] nums, List<List<Integer>> list, List<Integer> com, int sum, int i){
        if (sum == n && com.size() == k){
            list.add(new ArrayList<>(com));
            return;
        }
        for(;i< nums.length;i++){
            if (sum+nums[i] <= n && com.size()+1<=k){
                sum += nums[i];
                com.add(nums[i]);
                Method2(k,n,nums,list,com,sum,i+1);
                com.remove(com.size()-1);
                sum-=nums[i];
            }
        }
    }
}
