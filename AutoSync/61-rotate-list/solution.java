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
    public ListNode rotateRight(ListNode head, int k) {
        if(head==null){
            return null;
        }
        ListNode h = new ListNode();
        ListNode p=null,c=null;
        int count =1;
        h.next=head;
        c=h.next;
        while(c.next!=null){
            count+=1;
            c=c.next;
        }
        int i =1;
        p=head;
        while(p.next!=null){
            if((i == count-k)&&(k<=count)||(k>count)&&i==count-(k%count)){
                h.next=p.next;
                c.next=head;
                p.next = null;
                break;
            }
            p=p.next;
            i++;
        }
        return h.next;
    }
    
}
