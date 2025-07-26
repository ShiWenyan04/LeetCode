#include <iostream>
#include <vector>
#include <queue>
#include <unordered_set>

using namespace std;

// 结构体记录当前位置和已走步数
struct Point {
    int index;
    int step;
    Point(int i, int s) : index(i), step(s) {}
};

int minimumJumps(vector<int>& forbidden, int a, int b, int x) {
    // 使用哈希集合存储禁止位置
    unordered_set<int> forbiddenSet;
    for (int f : forbidden) {
        forbiddenSet.insert(f);
    }

    // BFS队列，初始位置为原点(0)，步数为0
    queue<Point> q;
    q.emplace(0, 0);

    // 记录已访问过的位置
    unordered_set<int> visited;

    while (!q.empty()) {
        int m = q.size();
        for (int i = 0; i < m; ++i) {
            Point front = q.front();
            q.pop();

            // 如果到达目标位置，返回步数
            if (front.index == x) {
                return front.step;
            }

            // 检查当前位置是否有效（未被禁止且未访问过）
            if (front.index >= 0 && !forbiddenSet.count(front.index) &&
                !visited.count(front.index) && front.index <= 6000) {

                // 标记为已访问
                visited.insert(front.index);

                // 向前跳a步
                int next1 = front.index + a;
                if (next1 >= 0 && next1 <= 6000 && !visited.count(next1) && !forbiddenSet.count(next1)) {
                    q.emplace(next1, front.step + 1);
                }

                // 向前跳a步再向后跳b步（需要先检查a步是否有效）
                int nextA = front.index + a;
                if (nextA >= 0 && nextA <= 6000 && !forbiddenSet.count(nextA)) {
                    int next2 = nextA - b;
                    if (next2 >= 0 && next2 <= 6000 && !visited.count(next2) && !forbiddenSet.count(next2)) {
                        q.emplace(next2, front.step + 2);
                    }
                }
            }
        }
    }

    // 无法到达目标位置
    return -1;
}

int main() {
    // 示例测试
    vector<int> forbidden = {14, 4, 18, 1, 15};
    int a = 3, b = 15, x = 9;
    cout << minimumJumps(forbidden, a, b, x) << endl; // 应输出 3
    return 0;
}
