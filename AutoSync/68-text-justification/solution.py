class Solution:
    def fullJustify(self, words: List[str], maxWidth: int) -> List[str]:
        i = 0;
        n = len(words);
        list = [];
        while i < n:
            start = i;
            l = -1;# 第一个单词之前没有空格
            while i < n and l+len(words[i])+1<=maxWidth:
                l+=len(words[i])+1;
                i+=1;
            endcnt = maxWidth-l;#假设一个单词一个空格的话，这个表示除去他们长度之后，需要加的空格数量
            gapcnt = i-start-1;##间隙数量

            ##特殊情况，最后一行或者最后一个单词的时候,gap=0是因为单词数量为1的时候间隙为0

            if(i == n or gapcnt == 0):
                row = " ".join(words[start:i])+" "*endcnt;#每两个单词中间插一个空格，然后后面空格沾满
                list.append(row);
                continue;
            ##一般情况
            avg = endcnt // gapcnt;  ##表示这一行每个间隙应该平均分配到的空格数量,不算起初的每个单词中间有的一个空格
            rem = endcnt % gapcnt;  ##表示前面有rem个间隙要多加一个，也就是京可能配平，配不平就均匀分给前面几个间隙
            space = " " * (avg + 1)  # avg+1表示已经算上了起初的一个空格
            row = (space + " ").join(words[start:start + rem + 1])+space+space.join(words[start + rem + 1:i]);
            list.append(row);
        return list;
