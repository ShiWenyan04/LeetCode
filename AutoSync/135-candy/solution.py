class Solution(object):
    def candy(self, ratings):
        ans = 0;
        cnt = [1]
        j=1;

        while j < len(ratings):
            if(ratings[j]>ratings[j-1] ):
                cnt.append(cnt[j-1]+1)
            else:
                cnt.append(1)
            j+=1;

        j = len(ratings)-2;
        while j >= 0:
            if(ratings[j]>ratings[j+1]):
                cnt[j] = (max(cnt[j],cnt[j+1]+1));
            j-=1;

        for i in cnt:
            ans += i;
        return ans;
