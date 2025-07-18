//
// Created by 20538 on 2025/6/2.
//
#include <iostream>
#include <vector>

using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(NULL);
  cout.tie(NULL);
  int n;
  cin >> n;
  vector<long long> nums(n+1,0);
  long long ans = 0;
  for (int i = 1; i <= n; i++) {
    cin >> nums[i];
    if (nums[i] < nums[i-1]) {
      ans+=nums[i-1]-nums[i];
      nums[i] =nums[i-1];
    }
  }
  cout << ans << endl;
}