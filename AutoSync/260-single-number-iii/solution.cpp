class Solution {
public :
	vector<int> singleNumber(vector<int>& nums) {
		unordered_map <int,int> map;
		for (int i : nums) {
			map[i]++;
		}
		vector<int> ans;
		for (const auto& [num, occ] : map) {
			if (occ == 1) {
				ans.push_back(num);
			}
		}
		return ans ;
	}
};
