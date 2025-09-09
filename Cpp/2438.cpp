#include <vector>
#include <iostream>

using namespace std;

/*
根据题目描述，我们需要将 n 分解成最少数目的 2 的幂，这就提示我们将 n 写成二进制表示，
如果从低到高的第 k(k≥0) 个二进制位为 1，那么分解中就包括 2的k次方
比如n=11，二进制表示为（1011），其中第0，1，3位置上为1，所以为分解后为bin（1，2，8）

之后在给出的二维数组中，每一组代表一个范围，在这个为范围内 将分解后的bin相乘，再将每一组的值存到ans即可
 */
class Solution {
public:
  vector<int> productQueries(int n, vector<vector<int>>& queries) {
    int mod = 1e9 + 7;
    vector<int> bin;
    int rep = 1;
    while(n>0) {
      if(n%2==1){
        bin.push_back(rep);
      }
      n/=2;
      rep*=2;
    }
    vector<int> ans (queries.size());
    for(int i=0;i<queries.size();i++) {
      int temp = 1;
      int start = queries[i][0],end = queries[i][1];
      for(int j=start;j<=end;j++){
        temp = (temp*bin[j]) % mod;
      }
      ans[i] = temp;
    }
    return ans;
  }
};

int main(){
  vector<vector<int>> queries = {{0,1},{2,2},{0,3}};
  int n = 15;
  vector<int> ans = Solution().productQueries(n, queries);
  for(int i=0;i<ans.size();i++){
    cout<<ans[i]<<" ";
  }
}
