class Solution {
public:
    int minimumSubarrayLength(vector<int>& nums, int k) {
        return Method(nums,k);
    }
    int Method(vector<int>& nums,int k){
	int ans = INT_MAX;
	for(int i = 0;i < nums.size();i++){
		int n = 0;
		for(int j = i;j < nums.size();j++){
			n|=nums[j];
			if(n >= k){
				ans = min(ans,j-i+1);
				break;
			}
		}
	}
	return ans == INT_MAX?-1:ans;
}
};
