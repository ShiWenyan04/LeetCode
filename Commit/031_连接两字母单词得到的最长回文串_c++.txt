class Solution {
public:
    int longestPalindrome(vector<string>& words ) {
        unordered_map<string, int> map;
        for (string& word : words) {
            map[word]++;
        }

        int max_len = 0;
        bool mid = false;
        for(const auto&[word,cnt]:map){
            string s = string(1,word[1])+word[0];
            if(word==s){
                if(cnt%2==1){
                    mid = true;
                }
                max_len += cnt/2*2*2;
            }else if(word > s){
                 max_len += min(map[word],map[s])*4;
            }
        }
        if(mid){
            max_len+=2;
        }
         return max_len;
    }
};
