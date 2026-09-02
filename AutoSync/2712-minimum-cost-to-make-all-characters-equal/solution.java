class Solution {
    public long minimumCost(String s) {
        return Method2(s);
    }
    public static long Method2(String str) {
		int n = str.length();
		long ans = 0;
		for (int i = 0; i < str.length()-1; i++) {
			if(str.charAt(i) != str.charAt(i+1)) {
			ans+=Math.min(i+1,n-i-1);
			}
		}
		return ans;
	}
}
