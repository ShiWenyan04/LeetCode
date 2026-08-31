class Solution {
public:
  bool isValid(int n) {
    string s = to_string(n);
   if (s.size() &1) {
      return false;
    }
    int diff = 0;
    for (int i = 0;i<s.size()/2;i++) {
      diff += s[i]-s[s.size()-1-i];
    }
    return diff == 0;
  }
  int countSymmetricIntegers(int low, int high) {
    int ans = 0;
    for (int i = low; i <= high; i++) {
      if (isValid(i)) {
        ans++;
      }
    }
    return ans;
  }
};
