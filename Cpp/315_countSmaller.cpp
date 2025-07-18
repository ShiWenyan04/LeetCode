#include <vector>
#include <iostream>  // 添加输入输出流头文件
using namespace std;

class Solution {
public :
  vector<int> index;    // 记录原始索引位置
  vector<int> temp;     // 临时存储排序数组
  vector<int> tempIndex;// 临时存储索引数组
  vector<int> ans;      // 存储每个位置的结果

  vector<int> countSmaller(vector<int>& nums) {
    int n = nums.size();
    index.resize(n);
    temp.resize(n);
    tempIndex.resize(n);
    ans.resize(n, 0);

    // 初始化索引数组
    for (int i = 0; i < n; ++i) {
      index[i] = i;
    }

    // 归并排序主过程
    mergeSort(nums, 0, n - 1);
    return ans;
  }

  // 归并排序递归函数
  void mergeSort(vector<int>& a, int l, int r) {
    if (l >= r) {
      return;
    }
    int mid = (l + r) / 2;
    mergeSort(a, l, mid);
    mergeSort(a, mid + 1, r);
    merge(a, l, mid, r);
  }

  // 合并两个有序数组并统计逆序对
  void merge(vector<int>& a, int l, int mid, int r) {
    int i = l, j = mid + 1, p = l;

    // 合并两个有序数组，同时统计右边小于左边的元素个数
    while (i <= mid && j <= r) {
      if (a[i] <= a[j]) {
        // 当前左半部分元素a[i]大于等于右半部分已处理的元素个数(j-mid-1)
        temp[p] = a[i];
        tempIndex[p] = index[i];
        ans[index[i]] += (j - mid - 1);
        ++i;
      } else {
        // 右半部分元素较小，直接放入临时数组
        temp[p] = a[j];
        tempIndex[p] = index[j];
        ++j;
      }
      ++p;  // 修正：每次赋值后都需要增加p指针
    }

    // 处理左半部分剩余元素
    while (i <= mid) {
      temp[p] = a[i];
      tempIndex[p] = index[i];
      ans[index[i]] += (j - mid - 1); // 右半部分所有元素都小于a[i]
      ++i;
      ++p;
    }

    // 处理右半部分剩余元素
    while (j <= r) {
      temp[p] = a[j];
      tempIndex[p] = index[j];
      ++j;
      ++p;  // 修正：添加p指针的递增
    }

    // 将临时数组复制回原数组
    for (int k = l; k <= r; ++k) {
      index[k] = tempIndex[k];
      a[k] = temp[k];
    }
  }
};

int main() {
  int n;
  cin >> n;  // 读取数组长度
  vector<int> nums(n);

  for (int i = 0; i < n; i++) {
    cin >> nums[i];  // 读取数组元素
  }

  Solution s;
  vector<int> result = s.countSmaller(nums);

  for (int i = 0; i < n; i++) {
    cout << result[i] << " ";  // 输出结果
  }

  cout << endl;
  return 0;
}