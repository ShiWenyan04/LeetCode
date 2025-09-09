#include <vector>
//
// Created by 20538 on 2025/6/2.
//
# include <iostream>

using namespace std;

int main() {
  int n,x,t;
  cin >> n >> x >> t;
  int cnt = n/x;
  if (n%x!= 0) {
    cnt+=1;
  }
 cout  <<  cnt*t;
}


