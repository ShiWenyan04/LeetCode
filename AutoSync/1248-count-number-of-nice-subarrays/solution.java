class Solution {
    public int numberOfSubarrays(int[] nums, int k) {
        return Method(nums,k);
    }
   public static int Method(int []nums,int k) {
		int n = nums.length;
		int right = 0,left = 0;
		int count = 0;
		int ans = 0;
		while(right < n) {
			if(nums[right] %2 !=0) {
				count ++;
			}
			right ++;
			
			if(count == k) {
				int leftLen = 0,rightLen = 0;
				int temp = right;
				while(right<n&& nums[right]%2==0) {
					right++;
				}
				rightLen = right-temp;

				while(nums[left]%2==0) {
					left++;
					leftLen++;
				}
		
				ans+=(leftLen+1)*(rightLen+1);
				
				left++;
				count--;
			}
			
		}
		return ans;
	}
}
