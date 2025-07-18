#include <iostream>
#include <vector>
#include <unordered_map> 
using namespace std;

//����һ���������� nums ��һ������ k �����㷵�� nums �� �� ���������Ŀ��
//һ�������� arr ����� ���� k ���±� (i, j) ���� i < j �� arr[i] == arr[j] ����ô������һ�� �� �����顣
//������ ��ԭ������һ������ �ǿ� ��Ԫ�����С�
//ʾ�� 1��
//���룺nums = [1,1,1,1,1], k = 10
//�����1
///���ͣ�Ψһ�ĺ���������������鱾��
//ʾ�� 2
//���룺nums = [3,1,4,3,2,2,4], k = 2
//�����4
//���ͣ��ܹ��� 4 ����ͬ�ĺ������飺
//- [3,1,4,3,2,2] �� 2 �ԡ�
//- [3,1,4,3,2,2,4] �� 3 �ԡ�
//- [1,4,3,2,2,4] �� 2 �ԡ�
//- [4,3,2,2,4] �� 2 �ԡ�
class solution {
public :
	long long countGood(vector<int> & nums,int k){
		int same = 0;//��ǰ����ɶԵ����� 
		long long ans= 0;//��� 
		int n = nums.size();
		int right = -1, left = 0;//˫ָ�룬rightΪ�ұ߽磬leftΪ��߽� 
		unordered_map<int,int> cnt;//��ϣ���¼ÿ�����ֳ��ֵĴ��� 
		while(left < n){
			while(same < k && right +1 < n){
				right ++;
				same+= cnt[nums[right]];//��ǰ����������ظ������� 
				cnt[nums[right]]++;//�ұ߽�����ּ������� 
			}
			if(same >= k){//��same�������� 
				ans+=n-right;//��right��n-1��ÿһ�������鶼����Ч�� 
			}
			cnt[nums[left]]--;//�ƶ���߽磬��С���� 
			same -= cnt[nums[left]];//�Ƴ���߽����ֺ�������� 
			left++;//��ָ���ƶ� 
			
		}
		return ans;	
	}
};

int main ()
{
	vector<int> nums = {3,1,4,3,2,2,4};
	int k = 2;
	solution s; 
	cout << s.countGood(nums,k) << endl;
	return 0;
}

