class Solution {
public :
	int maxPalindromes(string str,int k) {
		int n = str.size();
		vector<int> f(n+1);
		for (int i = 0; i < 2 * n - 1; i++) {
			int left = i / 2, right = (i + 1) / 2;
			f[left + 1] = max(f[left], f[left + 1]);
			for (; left >= 0 && right <= n - 1 && str[left] == str[right]; right++, left--) {
				if (right - left + 1 >= k) {
					f[right + 1] = max(f[right + 1], f[left] + 1);

				}
				
			}
		}
        return f[n];
    }
};
