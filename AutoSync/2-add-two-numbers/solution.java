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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2){
        int carry = 0;
        int sum = 0;
        ListNode head = null,tail = null;
        while(l1!=null||l2!=null){
            int n1 = 0,n2 = 0;
            if(l1==null){
                n1 = 0;
            }else {
                 n1 = l1.val;
            }
            if(l2 == null){
                n2 = 0;
            }else{
                 n2 = l2.val;
            }
            sum = (n1+n2)+carry;
            if(head == null){
                head = tail = new ListNode(sum%10);
            }else{
                tail.next = new ListNode(sum%10);
                tail=tail.next;
            }
            carry = sum/10;
            if (l1 != null) {
                l1 = l1.next;
            }
            if (l2 != null) {
                l2 = l2.next;
            }
        }
        if (carry > 0) {
            tail.next = new ListNode(carry);
        }
        return head;
    }
}
