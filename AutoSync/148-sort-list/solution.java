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
    public ListNode sortList(ListNode head) {
        return sortList(head,null);
    }
    public ListNode sortList(ListNode head, ListNode tail){
        if(head == null){
            return head;
        }
        if(head.next == tail){
            head.next = null;
            return head;
        }
        ListNode slow = head,fast = head;
        while(fast != tail){
            slow = slow.next;
            fast = fast.next;
            if(fast != tail){
                fast = fast.next;
            }
        }
        ListNode mid = slow;
        ListNode list1 = sortList(head,mid);
        ListNode list2 = sortList(mid,tail);
        ListNode sorted = merge(list1,list2);
        return sorted;
    }
    public ListNode merge(ListNode head1,ListNode head2){
        ListNode p = new ListNode(0);
        ListNode t = p, t1 = head1, t2 = head2;
        while(t1 != null && t2!=null){
            if(t1.val < t2.val){
                t.next = t1;
                t1 = t1.next;
            }else{
                t.next = t2;
                t2 = t2.next;
            }
            t = t.next;
        }
        if(t1!=null){
            t.next = t1;
        }else if(t2!=null){
            t.next = t2;
        }
        return p.next;
    }

}
