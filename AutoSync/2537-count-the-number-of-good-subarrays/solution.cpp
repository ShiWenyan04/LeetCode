class Solution {
public :
	long long countGood(vector<int> & nums,int k){
		int n = nums.size();
        int same = 0 ,right = -1 ;//当前区间成对的数量 
		unordered_map<int,int> cnt;//哈希表记录每个数字出现的次数 
        long long ans= 0;//结果 
		
		int left = 0;//双指针，right为右边界，left为左边界 
		
		while(left <n){
			while(same < k && right +1 < n){
				right ++;
				same+= cnt[nums[right]];//当前数字如果有重复就增加 
				cnt[nums[right]]++;//右边界的数字计输增加 
			}
			if(same >= k){//当same满足条件 
				ans+=n-right;//从right到n-1的每一个子数组都是有效的 
			}
			cnt[nums[left]]--;//移动左边界，缩小窗口 
			same -= cnt[nums[left]];//移除左边界数字后对数减少 
            left++;
        }
		return ans;	
	}
};
