class Solution {
    public int[] nextGreaterElements(int[] nums) {
        return Method(nums);
    }
    public static int[] Method(int[] nums){
        int n = nums.length;
        int []arr = new int[n];
        Arrays.fill(arr,-1);
        Deque<Integer> deque = new ArrayDeque<>();
        for (int i = 0; i < 2*n-1; i++) {
//            因为栈在压入最后一个元素的索引时，后面无索引可以比较，所以我们可以将数组的长度变为两倍，然后取余
//            这样就可以使最后一位元素同前面的元素进行比较了
            int x = nums[i%n];
            while (!deque.isEmpty() && nums[deque.peek()] < x){
                // x 是 nums[st.peek()] 的下一个更大元素
                // 既然 nums[st.peek()] 已经算出答案，则从栈顶弹出
                arr[deque.pop()] = x;
            }
//            仅在第一次遍历时压入栈中，因为当遍历到数组当中的组后一个元素时，后面再无元素需要判断，当前为最后一个需要判断的数字
//            无需再压入栈，只用判断就可以，
            if (i < n){
                deque.push(i);
            }
        }
        return arr;
    }
}
