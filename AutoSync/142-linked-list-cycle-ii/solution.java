/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public ListNode detectCycle(ListNode head) {
        if(head == null || head.next == null){
            return null;
        }
        HashSet<ListNode>hashset = new HashSet<>();
        ListNode p = head,cur = head;
        int count;
        int index = 0;
        while(cur != null && !hashset.contains(cur)){
            hashset.add(cur);
            cur = cur.next;
        }
        if(cur == null){
            return null;
        }
        return cur;
    }
}
