class Solution {
public:
    vector<vector<string>> ans;

    bool check(string s,int l,int r) {//检查是否是回文串
        while (l < r && s[l] == s[r]) {//向中心逐个遍历判断
            l++; 
            r--;
        }
        return r <= l;//如果是回文  会l>=r
    }

    void dfs(vector<string>& list,string s,int l) {//递归
        if (l == s.size()) {//当遍历到字符串末尾
            ans.push_back(list);
            return;
        }

        for (int r = l; r < s.size() ; r++)//遍历r，获取每一种长度的回文子串
        {
            if (check(s, l, r)) {//检查
                list.push_back(s.substr(l, r-l+1));
                dfs(list, s, r + 1);//递归
                list.pop_back( );//回溯
            }
        }
    }
    vector<vector<string>> partition(string s) {
        vector<string> list;
        list.clear();//答案中的每一组
        ans.clear();//答案
        dfs(list, s, 0);//递归
        return ans;
    }
};
