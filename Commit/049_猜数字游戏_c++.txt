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
