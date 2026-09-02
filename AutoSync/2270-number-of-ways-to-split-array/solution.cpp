class Solution {
public:
long Method(vector<int>& nums){
	long total = 0;;
	for(int n : nums){
		total+=n;
	}
	long sum = 0;
	int ans = 0;
	for(int i = 0; i < nums.size()-1;i++){
		sum+=nums[i];
		if(total - sum <= sum){
			ans++;
		}
	}
	return ans;
}
    int waysToSplitArray(vector<int>& nums) {
        return Method(nums);
    }
};
