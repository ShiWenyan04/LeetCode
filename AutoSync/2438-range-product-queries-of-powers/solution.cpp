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
        temp = ((long long)temp*bin[j]) % mod;
      }
      ans[i] = temp;
    }
    return ans;
  }
};
