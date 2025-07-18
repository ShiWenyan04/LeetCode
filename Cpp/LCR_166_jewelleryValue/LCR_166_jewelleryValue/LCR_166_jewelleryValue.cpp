#include<iostream>
#include <vector>
/*����һ��������ά���� frame ���鱦�ܣ����� frame[i][j] Ϊ��λ���鱦�ļ�ֵ����ȡ�鱦�Ĺ���Ϊ��
ֻ�ܴӼ��ӵ����Ͻǿ�ʼ���鱦
ÿ�ο����ƶ����Ҳ���²������λ��
�����鱦���ӵ����½�ʱ��ֹͣ��ȡ
ע�⣺�鱦�ļ�ֵ���Ǵ��� 0 �ġ��������������û���κ��鱦������ frame = [[0]]��
ʾ�� 1��
���룺frame = [[1,3,1],[1,5,1],[4,2,1]]
�����12
���ͣ�·�� 1��3��5��2��1 �����õ���߼�ֵ���鱦*/
using namespace std;
//��̬�滮
class Solution {
public :
	int jewelleryValue(vector<vector<int>>& frame) {
		int n = frame.size();
		int m = frame[0].size();
		for (int i = 1; i < m; i++) {//��һ��ֻ�������ۼӵõ����ֵ
			frame[0][i] += frame[0][i - 1];
		}
		for (int i = 1; i < n; i++) {//��һ��ֻ�������ۼӵõ����ֵ
			frame[i][0] += frame[i-1][0];
		}
		for (int i = 1; i < n; i++){//�����Ϊ��i��j�����Ϸ����󷽵�����һ��ֵ��ӵ���
			for (int j = 1; j < m; j++) {
				frame[i][j] += max(frame[i - 1][j], frame[i][j - 1]);
			}
		}
		return frame[n - 1][m - 1];
	}
};
int main() {
	vector<vector<int >> frame = { {1,2} ,{5,6},{1,1} };
	Solution s;
	cout << s.jewelleryValue(frame) << endl;
}