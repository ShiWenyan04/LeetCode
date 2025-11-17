class Solution {
    public int minimumSum(int n, int k) {
        return  Method2(n,k);
    }
    public static int Method2(int n,int k) {
		int temp = k/2;
		int sum = 0;
		int num = 1;
		for (int i = 0; i < n; i++,num++) {
			if(num <= temp) {
				sum+=num;
			}else {
				sum+=k;
				k++;
			}
		}
		return sum;
	}
}