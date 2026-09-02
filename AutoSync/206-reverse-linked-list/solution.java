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
    public ListNode reverseList(ListNode head) {
        ListNode p1=head,p2 = head;
        head = null;
        while(p1!=null){
            p2 = p1.next;
            p1.next = head;
            head = p1;
            p1 = p2;
        }
        return head;
    }
}
