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
        ListNode p = head,t = head;
        while(t!=null&&t.next!=null){
            p = p.next;
            t = t.next.next;
            if(p == t){
                t = head;
                while(p != t){
                    p = p.next;
                    t = t.next;
                }
                return p;
            }
        }
        return null;
    }
}