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
    public boolean isPalindrome(ListNode head) {
        // 思路：先找到链表的中点
        ListNode mid = midNode(head);
        // 再将链表的后半部分进行反转，即中点到末尾
        ListNode later = halfNode(mid);
        // 最后对两个链表进行回文判断,由于中点是算进后半部分的，所以长一点的链表只能是后一个，只需要判断前一个链表的指针是否为空即可。
        ListNode n = head,m=later;
        while(n!=null && m!= null){
            if(n.val!= m.val){
                return false;
            }
            n = n.next;
            m = m.next;
        }
        return true;
    }
    public ListNode midNode(ListNode head){
        ListNode p1=head,p2=head;
        while(p1.next!=null){
            p1=p1.next;
            if(p1.next!=null){
                p1 = p1.next;
            }
            p2 = p2.next;
        }
        return p2;
    }
    public ListNode halfNode(ListNode head){
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
