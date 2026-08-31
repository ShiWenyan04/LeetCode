class Solution {
public:
  long long maximumTripletValue(vector<int>& nums) {
    int n = nums.size();
    vector<int> left_max (n,0);
    vector<int> right_max (n,0);
    for (int i = 1; i < n; i++) {
      left_max[i] = max(left_max[i-1],nums[i-1]);//left_max[i]表示从（0，i）的最大值
      right_max[n-1-i] = max(right_max[n-i],nums[n-i]);//right_max[i]表示从（n-1-i,n-1）的最大值
    }
    long long result = 0;
    for (int j = 1; j < n-1; j++) {
      result = max(result, (long long)(left_max[j]-nums[j])*right_max[j]);
    }
    return result;
  }
};
