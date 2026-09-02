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
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode head2 = null,temp = null;
        head2 = head;
        int i = 0;
        while(head != null){
            head = head.next;
            i++;
        }
        head = head2;
        for(int j = 0; j < i-n-1;j++){
            head2 = head2.next;
        }
        if(n == i){
            return head.next;
        }if(i == 2 && n == 1){
            head2.next = null; 
            return head;
        }
        head2.next = head2.next.next;
        return head;
    }
}
