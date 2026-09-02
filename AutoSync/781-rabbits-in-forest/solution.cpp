class Solution {
public :
	int numRabbits(vector<int>&  nums) {
		unordered_map <int, int >map;
		for (auto& x : nums) {
			map[x]++;
		}
		int ans = 0;
		for (auto &[x,y] : map) {
			ans += (y+x) / (x + 1) * (x + 1);
		}
		return ans;
	}
};
