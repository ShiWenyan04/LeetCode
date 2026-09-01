class Solution {
public :
	int jewelleryValue(vector<vector<int>>& frame) {
		int n = frame.size();
		int m = frame[0].size();
		for (int i = 1; i < m; i++) {//第一行只能向右累加得到最大值
			frame[0][i] += frame[0][i - 1];
		}
		for (int i = 1; i < n; i++) {//第一列只能向下累加得到最大值
			frame[i][0] += frame[i-1][0];
		}
		for (int i = 1; i < n; i++){//其余均为（i，j）的上方和左方的最大的一个值相加得来
			for (int j = 1; j < m; j++) {
				frame[i][j] += max(frame[i - 1][j], frame[i][j - 1]);
			}
		}
		return frame[n - 1][m - 1];
	}
};
