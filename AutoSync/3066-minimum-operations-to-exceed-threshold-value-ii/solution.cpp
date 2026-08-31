class Solution {
public:
    int minOperations(vector<int>& nums, int k) {
        int count = 0 ;
	priority_queue<long long,vector<long long>,greater<long long>> queue(nums.begin(),nums.end()); 
	while(queue.top() < k){
		long long x = queue.top();queue.pop();
		long long y = queue.top();queue.pop();
		queue.push(x*2+y);
		count++;
	}
	return count;
    }
};
