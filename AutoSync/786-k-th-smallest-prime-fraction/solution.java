class Solution {
    public int[] kthSmallestPrimeFraction(int[] arr, int k) {
        return Method(arr,k);
    }
   public static int[] Method(int[] arr,int k){
        int n = arr.length;
        PriorityQueue<int []> pq = new PriorityQueue<>((a,b)-> Double.compare(b[0]*1.0/b[1],a[0]*1.0/a[1]));
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                double temp = arr[i]*1.0/arr[j];
                if(pq.size()<k){
                    pq.add(new int[]{arr[i],arr[j]});
                } else if (pq.size() == k && pq.peek()[0]*1.0/pq.peek()[1] > temp) {
                    pq.poll();
                    pq.add(new int[]{arr[i],arr[j]});
                }
            }
        }
        return pq.poll();
    }
}
