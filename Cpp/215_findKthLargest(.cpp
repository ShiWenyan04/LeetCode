#include <iostream>
#include <vector>

using namespace std;
class Solution {
  public:
  int partition(vector<int>& nums,int s,int t) {
    int i = s,j = t;
    int base = nums[s];//以序列中的首元素作为基准
    while (i < j) {//从两端交替向中间遍历，直到i==j为止
      while (i<j && nums[j] <= base) {//从右向左查找大于base的nums[j]
        j--;
      }
      if (i <j) {
        nums[i] = nums[j];//把nums[j]前移到nums[i]的位置
        i++;
      }
      while (i<j && nums[i]>=base) {
        i++;
      }
      if (i < j) {
        nums[j] = nums[i];
        j--;
      }
    }
    nums[i] = base;//基准归位
    return i;
  }

  int quickSelect(vector<int> & nums,int s,int t,int k) {
    if (s < t) {
      int i = partition(nums,s,t);
      if (i == k-1) {
        return nums[i];
      }else if (i > k-1) {
        return quickSelect(nums,s,i-1,k);//左区间递归
      }else {
        return quickSelect(nums,i+1,t,k);//右区间递归
      }
    }
    return nums[k-1];
  }
};

int main() {
  Solution s;
  int n,k;//数组个数、k
  cin >> n >> k;
  vector<int> nums (n,0);
  for (int i = 0; i < n;i++) {//输入nums
    cin >> nums[i];
  }
  s.quickSelect(nums, 0,n-1,k);
  cout << nums[k-1] << endl;
}
