#include <iostream>
#include <vector>
#include <algorithm>
/*��������һλ�ܰ��ļҳ�����Ҫ����ĺ�����һЩС���ɡ����ǣ�ÿ���������ֻ�ܸ�һ����ɡ�

��ÿ������ i������һ��θ��ֵ g[i]���������ú���������θ�ڵı��ɵ���С�ߴ磻����ÿ����� j������һ���ߴ� s[j] ����� s[j] >= g[i]�����ǿ��Խ�������� j ��������� i ��������ӻ�õ����㡣���Ŀ�������㾡���ܶ�ĺ��ӣ��������������ֵ��

 */
using namespace std;
/*���� + ˫ָ�� + ̰��*/
class Solution {
public:
    int findContentChildren(vector<int>& g, vector<int>& s) {
        sort(g.begin(), g.end());
        sort(s.begin(), s.end());
        int count = 0;
        int n = g.size(), m = s.size();
        for (int i = 0,j = 0; i < n && j <m; i++,j++) {
            while (j < m && g[i]  > s[j]) {
                j++;
            }
            if (j < m) {
                count++;
            }
        }
        return count;
    }
};

int main() {
    vector<int> g = { 1,2 };
    vector<int> s = { 1,2,3 };
    Solution so;
    cout << so.findContentChildren(g, s) << endl;
}