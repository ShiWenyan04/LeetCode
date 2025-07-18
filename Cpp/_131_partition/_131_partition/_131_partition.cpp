#include < iostream >
#include < vector>
/*����һ���ַ��� s�����㽫 s �ָ��һЩ �Ӵ���ʹÿ���Ӵ����� ���Ĵ� ������ s ���п��ܵķָ����
ʾ�� 1��
���룺s = "aab"
�����[["a","a","b"],["aa","b"]]
ʾ�� 2��
���룺s = "a"
�����[["a"]]*/
using namespace std;

class Solution {
public:
    vector<vector<string>> ans;

    bool check(string s,int l,int r) {//����Ƿ��ǻ��Ĵ�
        while (l < r && s[l] == s[r]) {//��������������ж�
            l++; 
            r--;
        }
        return r <= l;//����ǻ���  ��l>=r
    }

    void dfs(vector<string>& list,string s,int l) {//�ݹ�
        if (l == s.size()) {//���������ַ���ĩβ
            ans.push_back(list);
            return;
        }

        for (int r = l; r < s.size() ; r++)//����r����ȡÿһ�ֳ��ȵĻ����Ӵ�
        {
            if (check(s, l, r)) {//���
                list.push_back(s.substr(l, r-l+1));
                dfs(list, s, r + 1);//�ݹ�
                list.pop_back( );//����
            }
        }
    }
    vector<vector<string>> partition(string s) {
        vector<string> list;
        list.clear();//���е�ÿһ��
        ans.clear();//��
        dfs(list, s, 0);//�ݹ�
        return ans;
    }
};

int main() {
    string str = "aab";
    Solution s;
    vector<vector<string>> ans = s.partition(str);
    for (vector<string> v : ans) {
        for (string s : v) {
            cout << s << endl;
        }
        cout << endl;
    }
}