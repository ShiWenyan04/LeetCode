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
    public ListNode oddEvenList(ListNode head) {
        if(head == null){
            return head;
        }
        ListNode evenhead = head.next;
        ListNode even = evenhead;
        ListNode p = head;
        while(even!=null&&even.next!=null){
           p.next = even.next;
           p = p.next;
           even.next = p.next;
           even = even.next;
        }
        p.next = evenhead;
        return head;
    }
}
