//
// Created by 20538 on 2025/6/2.
//
#include<iostream>

using namespace std;

bool zhishu(long long n) {
  if ((n > 2 && n!= 3) || n%3 == 0 || n % 2 == 0) {
    return false;
  }
  return true;
}
int main() {
  long long n;
  cin>>n;
  /*n为素数*/
  if (zhishu(n)) {
    cout << n-1 << endl;
    /*n为合数时，n==4*/
  }else if (n == 4) {
    cout << 2 << endl;
    /*n为合数 n >4时*/
  }else if (n > 4 && !zhishu(n)) {
    cout << 0 << endl;
  }
}