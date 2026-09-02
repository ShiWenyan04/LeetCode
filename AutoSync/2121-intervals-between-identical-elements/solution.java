class Solution {
    public long[] getDistances(int[] arr) {
        return Method(arr);
    }
    public static long[] Method(int []arr) {
		int n = arr.length;
		long [][]leftPre = new long [100010][2];
		long [][]rightPre = new long [100010][2];
		long []result = new long [n];
		for (int i = 0; i < n; i++) {
			int num = arr[i];
			leftPre[num][0]+=i;//累加位置和
			leftPre[num][1]++;//累计次数
			result[i] += Math.abs(leftPre[num][0] - i*leftPre[num][1]);
		}
		for (int i = n-1; i >= 0; i--) {
			int num = arr[i];
			rightPre[num][0]+=i;//累加位置和
			rightPre[num][1]++;//累计次数
			result[i] += Math.abs(rightPre[num][0] - i*rightPre[num][1]);
		}
		return result;
	}
}
