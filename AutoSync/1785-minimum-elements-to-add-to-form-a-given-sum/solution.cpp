class Solution {
public:
  int minElements(vector<int>& nums, int limit, int goal) {
    long long sum = 0;
    for (int i:nums) {
      sum += i;
    }
    long long diff = abs(sum - goal);
    int ans = (diff+limit-1)/limit;
    return ans;
  }
};
