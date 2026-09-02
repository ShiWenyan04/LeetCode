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
    public ListNode deleteDuplicates(ListNode head) {
        if(head == null){
            return null;
        }
        ListNode tou = new ListNode(0,head);
        ListNode p;
        p = tou;
        while(p.next!=null&&p.next.next!=null){
            if(p.next.val == p.next.next.val){
                int data = p.next.val;
                while( p.next!=null&&p.next.val == data ){
                    p.next=p.next.next;
                }
            } else{
                p = p.next;
            }
        }
        return tou.next;
    }
}
