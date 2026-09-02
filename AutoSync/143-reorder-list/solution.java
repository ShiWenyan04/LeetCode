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
    public void reorderList(ListNode head) {
        if (head == null || head.next==null) {
            return;
        }
        ListNode c, p;
        ListNode dummy1 = new ListNode(-1);
        ListNode dummy2 = new ListNode(-1);
        p = c = head;
        int mid = 0,count = 0;
        while(c!=null){//计数
            count++;
            c=c.next;
        }
        mid = count/2;//中间节点
        int index = 1;
        ListNode temp ;
        while( p!=null){//将mid后半部分的结点全部头插到新链表
            if(index > mid){
                temp = p.next;
                p.next = dummy2.next;
                dummy2.next = p;
                p=temp;
            }else{
                p = p.next;
            }
            index++;
        }
        p = head;//将p指针移到链表1的头结点
        ListNode p2 = dummy2.next;//将p指针移到链表2的首结点
        while( p2.next!=null){//隔空插入连接
            temp = p2.next;
            p2.next = p.next;
            p.next = p2;
            p2 = temp;
            p = p.next.next;
        }
    }
}
