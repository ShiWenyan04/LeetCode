class Solution {
public :
	long long countInterestingSubarrays(vector<int>& nums, int m, int k) {
		int n = nums.size();
		vector<int> a(n+1);
		vector<int>b(n + 1);
		for (int i = 1; i <= n; i++) {//计算每一个num是否可以满足条件，如果满足条件那么相对的a数组就会变为1；
			if (nums[i-1] % m == k) {//由于题目要求cnt%m=k   cnt可以由 sum[j]-sum[i]求得
				a[i] = 1;
			}
			b[i] = b[i - 1] + a[i ];//上文提到的sum为b数组
		}

		unordered_map<int, int > map;
		map[0]=1;
		long long  ans = 0;
		for (int i = 1; i <= n; i++) {//遍历前缀和
			//计算当前前缀和对 modulo 取余数。
			//找到哪些前缀和 b[j]，满足(b[i] - b[j]) % modulo == k
			int t = map[(b[i] + m - k) % m]; 
			ans += t;//左式累加
			map[b[i] % m]++;//右式更新
		}
	return ans;
    }
};
