#include<iostream>
#include<vector>
#include<string>

using namespace std;

class Solution {
public :
	int cnt = 0;
	//�жϻʺ���ǰ�漸�г��ֵ�λ���뵱ǰλ���Ƿ��ͻ
	bool judge(int idx, vector<int>& q) {
		int row = q.size();
		if (row== 0) {//��һ������
			return true;
		}
		for (int i = 0; i < row; i++) {//�����Ѿ��Ź��ʺ��λ�ã��Ƿ��ͻ
			if (q[i] == idx || abs(row - i) == abs(idx - q[i])) {
				return false;
			}
		}
		return true;
	}

	void dfs(int n, vector<int>& q) {
		if (q.size() == n) {//����ʺ�����ˣ�������һ
			cnt++;
			return;
		}
		for (int i = 0; i < n; i++){//����ÿһ�У������֮ǰ�ʺ��λ��û�г��ֹ�����ô��λ�÷Żʺ�
			if (judge(i, q)) {
				q.push_back(i);
				dfs(n, q);//�ݹ�
				q.pop_back();//����
			}
		}
	}

	int totalNQueens(int n) {
		vector<int> q;//���ÿһ�д�Żʺ���е�λ��
		dfs(n, q);
		return cnt;
	}
};
int main() {
	int n = 4;
	Solution s;
	cout << s.totalNQueens(n) << endl;
}