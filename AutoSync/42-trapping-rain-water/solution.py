class Solution:
    def trap(self, height):
        ans = 0;
        left = 0;
        right = len(height) - 1;
        leftMax = height[0];
        rightMax = height[-1];

        while (left < right):
            leftMax = max(height[left], leftMax);
            rightMax = max(height[right], rightMax);
            if leftMax < rightMax:
                ans += leftMax - height[left];
                left += 1;
            else:
                ans += rightMax - height[right];
                right -= 1;
        return ans;
