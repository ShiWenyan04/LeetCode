#define _CRT_SECURE_NO_WARNINGS
#include<iostream>
using namespace std;
#define MAX 100
/*
��������
����n����Ʒ��һ������ΪC�ı�������Ʒi��������wi�����ֵΪvi����Ӧ���ѡ��װ�뱳������Ʒ��ʹ��װ�뱳������Ʒ���ܼ�ֵ���

�������
0-1����������һ�����������Ż����⣬����NP��ȫ���⡣������ͨ�����ݷ�����������ݷ���һ��ϵͳ�����������ķ������ڻ��ݷ��У�����ͨ��������ռ�����̽�����п��ܵĽ⣬��ͨ����֦���������������ռ䡣
*/
int n; // ��Ʒ����
int w[MAX]; // ��Ʒ����
int v[MAX]; // ��Ʒ��ֵ
int c; // ��������
int best = 0; // ����ֵ
int bestx[MAX]; // ��¼���Ž�
int x[MAX]; // ��ǰ��
int cw = 0; // ��ǰ����
int cv = 0; // ��ǰ��ֵ
int r = 0; // ʣ����Ʒ���ܼ�ֵ

void backtrack(int i) {
  if (i > n) {
    best = cv;
    for (int j = 1; j <= n; j++) {
      bestx[j] = x[j];
    }
    return;
  }
  r -= v[i];
  if (w[i] + cw <= c) { // ����������
    cw += w[i];
    cv += v[i];
    x[i] = 1;
    backtrack(i + 1);
    cw -= w[i];
    cv -= v[i];
    x[i] = 0;
  }
  if (r + cv >= best) { // ����������
    x[i] = 0;
    backtrack(i + 1);
  }
  r += v[i];
}

int main() {
  cin >> n >> c;
  for (int i = 1; i <= n; i++) {
    cin >> w[i] >> v[i];
    r += v[i];
  }
  backtrack(1);
  cout << best << endl;
  //ѡ�����Ʒ
  for (int i = 1; i <= n; i++)
    cout << bestx[i] << " ";
  return 0;
}