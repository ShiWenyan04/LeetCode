#include <iostream>
#include <vector>
/*����һ���ַ��� s ��һ�� �� ���� k ��
���ַ��� s ��ѡ��һ���������������� ���ص� �����ַ�����
ÿ�����ַ����ĳ��� ���� Ϊ k ��
ÿ�����ַ�����һ�� ���Ĵ� ��
�������ŷ�������ѡ������ַ����� ��� ��Ŀ��
���ַ��� ���ַ�����һ���������ַ����С�
ʾ�� 1 ��
���룺s = "abaccdbbd", k = 3
�����2
���ͣ�����ѡ�� s = "abaccdbbd" ��б��Ӵֵ����ַ�����"aba" �� "dbbd" ���ǻ��ģ��ҳ�������Ϊ k = 3 ��
����֤�����޷�ѡ���������ϵ���Ч���ַ�����*/
using namespace std;

class Solution {
public:
	int maxPalindromes(string str, int k) {
		int n = str.size();
		vector<int> f(n + 1);//f[i]��ʾ������i���ַ�ʱ�ܹ����ɵĻ����Ӵ����������
		for (int i = 0; i < 2 * n + 1; i++) {//Ŀ���Ǳ����������ģ����ڻ������ģ�
															//1.���ַ�����  2˫�ַ�����

			int left = i / 2, right = (i + 1) / 2;//left ��right�ǵ�ǰ�������ĵ����ұ߽�
																//Ŀ������iΪż��ʱ��ʾ˫�ַ����ģ�iΪ����ʱ��ʾ���ַ�����
			
			f[left + 1] = max(f[left], f[left + 1]);//f[left+1]��������f[left]�Ĵ�

			for (; left >= 0 && right <= n - 1 && str[left] == str[right]; right++, left--) {
				//ʹ�� for ѭ����չ�����Ӵ��ı߽磬�� left �� right ��ʼ��
				// ��������չֱ�� left �� right ����ƥ�䣬���߳����ַ����߽硣

				if (right - left + 1 >= k) {
					//�����չ�Ļ��ĳ��ȴ��ڵ��� k����ô���ǿ��Կ��ǽ��û��ļ�������
					// ��˸��� f[right + 1]
				
					f[right + 1] = max(f[right + 1], f[left] + 1);
				}

			}
		}
		return f[n];
	}
};

			int main() {
			
				string str = "abaccdbbd";
				int k = 3;
				Solution s;
				cout << s.maxPalindromes(str, k) << endl;
			}