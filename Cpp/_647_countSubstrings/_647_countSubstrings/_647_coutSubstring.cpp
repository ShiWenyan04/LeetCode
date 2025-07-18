#include < iostream>
#include < string>
using namespace std;
/*给你一个字符串 s ，请你统计并返回这个字符串中 回文子串 的数目。
回文字符串 是正着读和倒过来读一样的字符串。
子字符串 是字符串中的由连续字符组成的一个序列。
示例 1：
输入：s = "abc"
输出：3
解释：三个回文子串: "a", "b", "c"
示例 2：
输入：s = "aaa"
输出：6
解释：6个回文子串: "a", "a", "a", "aa", "aa", "aaa"
 */
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

int main() {
    string str = "abbccbba";
    Solution s;
    cout << s.countSubstrings(str) << endl;
}