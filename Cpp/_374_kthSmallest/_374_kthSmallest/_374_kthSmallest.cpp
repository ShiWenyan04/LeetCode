#include <iostream>
#include <vector>

/*����һ�� n x n ���� matrix ������ÿ�к�ÿ��Ԫ�ؾ������������ҵ������е� k С��Ԫ�ء�
��ע�⣬���� ����� �ĵ� k СԪ�أ������ǵ� k �� ��ͬ ��Ԫ�ء�
������ҵ�һ���ڴ渴�Ӷ����� O(n2) �Ľ��������
ʾ�� 1��
���룺matrix = [[1,5,9],[10,11,13],[12,13,15]], k = 8
�����13
���ͣ������е�Ԫ��Ϊ [1,5,9,10,11,12,13,13,15]���� 8 СԪ���� 13
ʾ�� 2��
���룺matrix = [[-5]], k = 1
�����-5*/
using namespace std;


class Solution {
public:
    bool  check(vector<vector<int>>& matrix,int mid, int k,int n) {
        int i = n-1, j = 0;//�����½ǿ�ʼ����
        int sum = 0;
        while (i >= 0 && j < n) {
            if (matrix[i][j] <= mid) {//�Խ���ʽ����      ��martrix[i][j]Ϊx
                sum+=i+1;//��Ϊ������� �Ķ�ά�����������Ͻǰ��С�����½ǰ�飬��xС��midʱ����һ����x����� ��С��mid������Ϊi+1
                j++;//��ǰ����
            }else{
                i--;//��������
            }
        }
        return sum >= k;
    }
    int kthSmallest(vector<vector<int>>& matrix, int k) {
        int n = matrix.size();
        int left = matrix[0][0];//���Ͻǵ�ֵ
        int right = matrix[n - 1][n - 1];//���½ǵ�ֵ
        while (left < right) {
            int mid = (left + right) / 2;
            if (check(matrix, mid, k, n)) {//С��mid�ĸ������ڵ���k��˵����kС��ֵ��mid���
                right = mid;
            }else{
                left = mid+1;//����mid �ĸ���С��k��˵��mid̫С��

            }
        };
        return left;
    }
};

int main(){
   vector<vector<int>>matrix = { {1, 5, 9},{10, 11, 13},{12, 13, 15} };
   int k = 8;
   Solution s;
   cout << s.kthSmallest(matrix, k) << endl;
    
}