class Solution {
    public int getKth(int lo, int hi, int k) {
        return Method(lo,hi,k);
    }
	public static  int Method(int l,int h,int k) {
		PriorityQueue<Integer>queue = new PriorityQueue<Integer>((a,b)->{
			if(getWeight(a)!=getWeight(b)) return getWeight(b)-getWeight(a);
			else {
				return b-a;
			}
		});
		for (int i = l; i <= h; i++) {
			queue.offer(i);
			if(queue.size() > k) {
				queue.poll();
			}
		}
		return queue.poll();
	}
	public static int getWeight(int x) {
		int w = 0;
		while(x!=1) {
			if(x%2 != 0) {
				x=x*3+1;
			}else {
				x/=2;
			}
			w++;
		}
		return w;
	}
}
