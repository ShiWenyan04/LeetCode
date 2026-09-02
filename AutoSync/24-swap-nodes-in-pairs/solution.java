/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode h = new ListNode(0);
        ListNode p1 = null, p2 = null, temp = null;
        h.next = head;
        temp = h;
        while(temp.next!=null && temp.next.next!=null){ 
            p1 = temp.next;
            p2 = p1.next;
            temp.next = p2;
            p1.next = p2.next;
            p2.next = p1;
            temp = p1;
        }
        return h.next;
    }
}
