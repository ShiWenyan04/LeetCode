class Solution {
public:
    int countSubstrings(string s) {
        //中心扩散法
        int n = s.size();
        int ans = 0;
        for (int  i = 0; i < n*2-1; i++){
            int left = i / 2, right = i / 2 + i % 2;
            while (left >= 0 && right < n && s[left] == s[right]) {
                left--;
                right++;
                ans++;
            }
        }
        return ans;
    }
};
