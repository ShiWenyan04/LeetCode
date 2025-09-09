#include <iostream>
#include <unordered_map>
#include <string>
#include <sstream>
/*你在和朋友一起玩 猜数字（Bulls and Cows）游戏，该游戏规则如下：
写出一个秘密数字，并请朋友猜这个数字是多少。朋友每猜测一次，你就会给他一个包含下述信息的提示：
猜测数字中有多少位属于数字和确切位置都猜对了（称为 "Bulls"，公牛），
有多少位属于数字猜对了但是位置不对（称为 "Cows"，奶牛）。也就是说，这次猜测中有多少位非公牛数字可以通过重新排列转换成公牛数字。
给你一个秘密数字 secret 和朋友猜测的数字 guess ，请你返回对朋友这次猜测的提示。
提示的格式为 "xAyB" ，x 是公牛个数， y 是奶牛个数，A 表示公牛，B 表示奶牛。

请注意秘密数字和朋友猜测的数字都可能含有重复数字。
输入：secret = "1807", guess = "7810"
输出："1A3B"
解释：数字和位置都对（公牛）用 '|' 连接，数字猜对位置不对（奶牛）的采用斜体加粗标识。
"1807"
  |
"7810"

输入：secret = "1123", guess = "0111"
输出："1A1B"
解释：数字和位置都对（公牛）用 '|' 连接，数字猜对位置不对（奶牛）的采用斜体加粗标识。
"1123"        "1123"
  |      or     |
"0111"        "0111"
注意，两个不匹配的 1 中，只有一个会算作奶牛（数字猜对位置不对）。通过重新排列非公牛数字，其中仅有一个 1 可以成为公牛数字。*/

using namespace std;

class Solution {
public:
    string getHint(string secret, string guess) {
        unordered_map <char, int> map;// 用于记录 secret 中每个数字出现的次数
        int a_count = 0;
        int b_count = 0;
        for (int j = 0; j < secret.size(); j++) {
            if (secret[j] == guess[j]) {//公牛数量
                a_count++;
            }else {//不是公牛，就计数
                map[secret[j]]++;
            }
        }
        //在遍历一次，计算奶牛
        for (int i = 0; i < guess.size(); i++) {//如果猜测的数字在map中存在，但是猜测的位置和secret的位置对应不上
            if (map[guess[i]] > 0 && secret[i] != guess[i]) {
                b_count++;//奶牛个数增加
                map[guess[i]]--;//map中该数字的个数减少
            }
        }

        ostringstream oss;
        oss << a_count << "A" << b_count << "B"; // 输出格式 "xAyB"
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