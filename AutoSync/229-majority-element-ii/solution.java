class Solution {
    public List<Integer> majorityElement(int[] nums) {
        return Method(nums);
    }
    public static List<Integer> Method(int[] nums){
        List<Integer> list = new ArrayList<>();
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        hashMap.put(nums[0],1);
        for (int i = 1; i < nums.length; i++) {
            if (hashMap.containsKey(nums[i])){
                hashMap.put(nums[i],hashMap.get(nums[i])+1);
            }else {
                hashMap.put(nums[i],1);
            }
        }
        for (Integer key: hashMap.keySet()){
            if (hashMap.get(key) > nums.length/3) {
                list.add(key);
            }
        }
        return list;
    }
}
