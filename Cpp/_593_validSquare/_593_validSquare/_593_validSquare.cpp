#include <iostream>
#include <vector>


/*����2D�ռ����ĸ�������� p1, p2, p3 �� p4��������ĸ��㹹��һ�������Σ��򷵻� true ��
������� pi ��ʾΪ [xi, yi] �� ����û���κ�˳�� ��
һ�� ��Ч�������� �������ȱߺ��ĸ��Ƚ�(90�Ƚ�)��
ʾ�� 1:
����: p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,1]
���: true
ʾ�� 2:
���룺p1 = [0,0], p2 = [1,1], p3 = [1,0], p4 = [0,12]
�����false
ʾ�� 3:
���룺p1 = [1,0], p2 = [-1,0], p3 = [0,1], p4 = [0,-1]
�����true*/
using namespace std;

class Solution {
public:
	bool isRightTriangle(vector<int>& p1, vector<int>& p2, vector<int>& p3) {
		//�����ε������ߣ���������֮����빫ʽ��ã���Ϊ���滹Ҫƽ��������û����sqrt
		int a = pow(p1[0] - p2[0], 2) + pow(p1[1] - p2[1], 2);
		int b = pow(p1[0] - p3[0], 2) + pow(p1[1] - p3[1], 2);
		int c = pow(p3[0] - p2[0], 2) + pow(p3[1] - p2[1], 2);
		 
		//�ж��Ƿ��ǵȱ�ֱ��������
		if ((a  + b  == c && a == b && a != 0) 
			||( a  + c  == b  && a == c && a != 0)
			||( c  + b == a&&c == b && a != 0)) {
			return true;
		}
		return false;
	}
	//����ȡ�����㣬�жϹ��ɵ��������Ƿ��ǵȱ�ֱ��������
	bool validSquare(vector<int>& p1, vector<int>& p2, vector<int>& p3, vector<int>& p4) {
		return isRightTriangle(p1, p2, p3) && isRightTriangle(p4, p2, p3) && isRightTriangle(p1, p4, p3) && isRightTriangle(p1, p2, p4);
	}
};
int main() {
	vector <int> p1 = { 0, 0 }, p2 = { 1, 1 }, p3 = { 1, 0 }, p4 = { 0, 1 };
	Solution s;
	cout << s.validSquare(p1, p2, p3, p4) << endl;
	return 0;
}