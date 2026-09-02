class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        return Method(nums,k);
    }
    private static int [] Method(int[] nums, int k) {
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        // 使用字典，统计每个元素出现的次数，元素为键，元素出现的次数为值
        for (int i : nums){
            if(hashMap.containsKey(i)){
                hashMap.put(i,hashMap.get(i)+1);
            }else{
                hashMap.put(i,1);
            }
        }
        // 遍历map，用最小堆保存频率最大的k个元素
        PriorityQueue<Integer> pq = new PriorityQueue<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer a, Integer b) {
                return hashMap.get(a)-hashMap.get(b);
            }
        });
//        pq
        for (int key : hashMap.keySet()){
            if (pq.size()<k){
                pq.add(key);
            } else if (hashMap.get(key) > hashMap.get(pq.peek())) {
                pq.remove();
                pq.add(key);
            }
        }
        // 取出最小堆中的元素
        int []ans = new int[k];
        for (int i = 0; i < k; i ++){
            ans[i] = pq.remove();
        }
        return ans;
    }
}
