class Solution {
    public int maxFrequencyElements(int[] nums) {
        int map[]=new int[101];
        int max=0;
        int ans=0;
        for(int i:nums) {
        	map[i]++;
        	if(map[i]>max) {
        		max=map[i];
        		ans=max;
        	}else if(map[i]==max){
        		ans+=max;
        	}
        }
        return ans;
    }
}
