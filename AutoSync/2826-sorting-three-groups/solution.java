class Solution {
    public int minimumOperations(List<Integer> nums) {
        return Method(nums);
    }
     public static int Method(List<Integer>list){
        int []f = new int[4];
        for (Integer x:list) {
            f[x]++;
            f[2] = Math.max(f[2],f[1]);
            f[3] = Math.max(f[3],Math.max(f[2],f[1]));
        }return list.size()-f[3];
    }
}
