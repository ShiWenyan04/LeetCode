#include <iostream>
#include <vector>
#include<algorithm>
/*观察下面的数字金字塔。
写一个程序来查找从最高点到底部任意处结束的路径，使路径经过数字的和最大。每一步可以走到左下方的点也可以到达右下方的点。
在上面的样例中，从 7→3→8→7→5 的路径产生了最大权值。*/
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