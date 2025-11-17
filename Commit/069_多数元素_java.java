class Solution {
    public int majorityElement(int[] nums) {
		int n = nums.length;
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		int t = n/2;
		for (int i : nums) {
			map.put(i, map.getOrDefault(i, 0)+1);
		}
		for (Integer i : map.keySet()) {
			if(map.get(i) > t) {
				return (i);
			}
		}
        return 0;
	}
}