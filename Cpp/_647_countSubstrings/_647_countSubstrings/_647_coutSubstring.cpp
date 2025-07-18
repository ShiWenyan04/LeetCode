#include < iostream>
#include < string>
using namespace std;
/*����һ���ַ��� s ������ͳ�Ʋ���������ַ����� �����Ӵ� ����Ŀ��
�����ַ��� �����Ŷ��͵�������һ�����ַ�����
���ַ��� ���ַ����е��������ַ���ɵ�һ�����С�
ʾ�� 1��
���룺s = "abc"
�����3
���ͣ����������Ӵ�: "a", "b", "c"
ʾ�� 2��
���룺s = "aaa"
�����6
���ͣ�6�������Ӵ�: "a", "a", "a", "aa", "aa", "aaa"
 */
class Solution {
public:
    int countSubstrings(string s) {
        //������ɢ��
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