class Solution {
public:
    int minOperations(vector<int>& nums, int k) {
        int count = 0;
        for(int n: nums){
            if(n < k){
                count ++;
            }
        }
        return count;
    }
};
