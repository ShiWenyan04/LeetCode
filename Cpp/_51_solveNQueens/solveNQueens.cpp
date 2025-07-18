#include<iostream>
#include<vector>
#include <string>
#include <cmath>
/*���չ�������Ĺ��򣬻ʺ���Թ�����֮����ͬһ�л�ͬһ�л�ͬһб���ϵ����ӡ�
n �ʺ����� �о�������ν� n ���ʺ������ n��n �������ϣ�����ʹ�ʺ�˴�֮�䲻���໥������
����һ������ n ���������в�ͬ�� n �ʺ����� �Ľ��������
ÿһ�ֽⷨ����һ����ͬ�� n �ʺ����� �����ӷ��÷������÷����� 'Q' �� '.' �ֱ�����˻ʺ�Ϳ�λ��*/
using namespace std;
/*˼·��
1.ʹ��idx�����ŵ�i�лʺ����ڵ�λ�ã�idx�����������ʾ��
2.���ڷ���λ��Ҫ�С��С�б�Խ��߾�����ͬ������Ҫ�жϵ�ǰλ���Ƿ���֮ǰ�ĳ��ֹ�
3.����Ҫ�ҳ����еķ���������Ҫ����dfs*/

vector<vector<string>> list;// ���ڴ洢���յ����нⷨ

//�ж�
bool judge(int i, vector<int>& q) {
	int row = q.size();
	if (row == 0) {//����ǵ�һ���ʺ����Ŷ�����
		return true;
	}
	for (int k = 0; k < row; k++) {//�����ж�ǰ��Ļʺ��Ƿ�͵�ǰ��ͬ�л�ͬ�Խ���
		if (q[k] == i) {//ͬ��
			return false;
		}else if ( abs(q[k] - i) == abs(row - k) ) {//ͬ�Խ���
			return false;
		}
	}
	return true;
}

//���̴�ӡ
vector<string> board(vector<int>q,int n) {
	vector<string> b;
	for (int i = 0; i < n; i++) {//ö����
		string row(n, '.');//��ʼ��ÿ��Ϊ��."
		int idx = q[i];//��ǰ�лʺ��ŵ�λ��
		row[idx] = 'Q';
		b.push_back(row);//�д�������
	}
	return b;
}

//�ݹ�
void dfs(int n,vector<int>& q) {
	if (q.size() == n) {//���������n���ʺ󣬱�ʾ�ҵ���һ�ֽⷨ
		list.push_back(board(q, n));//����
		return;
	}
	
	for (int i = 0; i < n; i++) {//��ǰ�б���ÿһ�У��ж�֮ǰ�Ļʺ��Ƿ���֮ͬ��
		if (judge(i, q)) {
			q.push_back(i);//���ûʺ�
			dfs(n, q);//dfs
			q.pop_back();//����
		}
	}
}

class Solution {
public :
	vector<vector<string>> solveNQueens(int n){
		list.clear();
		vector<int> q;//��Żʺ�ÿһ�е�λ��
		dfs(n,q);
		return list;
	}
};

int main() {
	int n = 4;
	Solution s;
	s.solveNQueens(n);
	//��ӡ���нⷨ
	for (auto& board : list) {
		for (auto& row : board) {
			cout << row << endl;//����
		}
		cout << endl;
	}
}