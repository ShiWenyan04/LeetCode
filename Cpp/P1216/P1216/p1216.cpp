#include <iostream>
#include <vector>
#include<algorithm>
/*�۲���������ֽ�������
дһ�����������Ҵ���ߵ㵽�ײ����⴦������·����ʹ·���������ֵĺ����ÿһ�������ߵ����·��ĵ�Ҳ���Ե������·��ĵ㡣
������������У��� 7��3��8��7��5 ��·�����������Ȩֵ��*/
using namespace std;

long long n;
int  nums[1000][1000],f[1010][1010];
int main() {
	cin >> n;
	for (int i =1; i <= n; i++) {
		for (int j =1; j <=i; j++) {
			cin >> nums[i][j];
		}
	}

	for (int i = 1; i <= n; i++) {
		for (int j = 1; j <=i; j++) {
			f[i][j] = max(f[i - 1][j - 1], f[i - 1][j])+nums[i][j];
		}
	}
	int ans = 0;
	for (int j = 1; j <= n; j++) {
		ans = max(ans, f[n][j]);
	}
	cout << ans<< endl;
}