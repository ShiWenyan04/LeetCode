//
// Created by 20538 on 2025/6/2.
//
# include <iostream>

using namespace std;

int main() {
  ios::sync_with_stdio(false);
  cin.tie(NULL);
  cout.tie(NULL);

  string n;
  cin>>n;
  long long sum = 0;
  for(int i=0;i<n.length();i++) {
    sum+=n[i]-'0';
  }
  if (sum%9==0) {
    cout<<"Yes"<<endl;
  }else {
    cout<<"No"<<endl;
  }
}
