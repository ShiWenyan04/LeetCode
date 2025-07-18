#include <iostream>
#include <unordered_map>
#include <string>
#include <sstream>
/*���ں�����һ���� �����֣�Bulls and Cows����Ϸ������Ϸ�������£�
д��һ���������֣��������Ѳ���������Ƕ��١�����ÿ�²�һ�Σ���ͻ����һ������������Ϣ����ʾ��
�²��������ж���λ�������ֺ�ȷ��λ�ö��¶��ˣ���Ϊ "Bulls"����ţ����
�ж���λ�������ֲ¶��˵���λ�ò��ԣ���Ϊ "Cows"����ţ����Ҳ����˵����β²����ж���λ�ǹ�ţ���ֿ���ͨ����������ת���ɹ�ţ���֡�
����һ���������� secret �����Ѳ²������ guess �����㷵�ض�������β²����ʾ��
��ʾ�ĸ�ʽΪ "xAyB" ��x �ǹ�ţ������ y ����ţ������A ��ʾ��ţ��B ��ʾ��ţ��

��ע���������ֺ����Ѳ²�����ֶ����ܺ����ظ����֡�
���룺secret = "1807", guess = "7810"
�����"1A3B"
���ͣ����ֺ�λ�ö��ԣ���ţ���� '|' ���ӣ����ֲ¶�λ�ò��ԣ���ţ���Ĳ���б��Ӵֱ�ʶ��
"1807"
  |
"7810"

���룺secret = "1123", guess = "0111"
�����"1A1B"
���ͣ����ֺ�λ�ö��ԣ���ţ���� '|' ���ӣ����ֲ¶�λ�ò��ԣ���ţ���Ĳ���б��Ӵֱ�ʶ��
"1123"        "1123"
  |      or     |
"0111"        "0111"
ע�⣬������ƥ��� 1 �У�ֻ��һ����������ţ�����ֲ¶�λ�ò��ԣ���ͨ���������зǹ�ţ���֣����н���һ�� 1 ���Գ�Ϊ��ţ���֡�*/

using namespace std;

class Solution {
public:
    string getHint(string secret, string guess) {
        unordered_map <char, int> map;// ���ڼ�¼ secret ��ÿ�����ֳ��ֵĴ���
        int a_count = 0;
        int b_count = 0;
        for (int j = 0; j < secret.size(); j++) {
            if (secret[j] == guess[j]) {//��ţ����
                a_count++;
            }else {//���ǹ�ţ���ͼ���
                map[secret[j]]++;
            }
        }
        //�ڱ���һ�Σ�������ţ
        for (int i = 0; i < guess.size(); i++) {//����²��������map�д��ڣ����ǲ²��λ�ú�secret��λ�ö�Ӧ����
            if (map[guess[i]] > 0 && secret[i] != guess[i]) {
                b_count++;//��ţ��������
                map[guess[i]]--;//map�и����ֵĸ�������
            }
        }

        ostringstream oss;
        oss << a_count << "A" << b_count << "B"; // �����ʽ "xAyB"
        string ans = oss.str();
        return ans;
    }
};

int main() {
    string s = "1123";
    string g = "0111";
    Solution solution;
    cout << solution.getHint(s, g) << endl;
}