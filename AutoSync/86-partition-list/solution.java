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
    public ListNode partition(ListNode head, int x) {
        ListNode dummy1 = new ListNode(0);//建立一个新结点，专门将小于x的结点连接在一起
        ListNode dummy2 = new ListNode(0,head);//原链表的头节点，存放剩余的值
        ListNode cur,p1,p2,temp;
        p1 = dummy1;
        p2 = dummy2;
        while(p2.next!=null){
            if(p2.next.val<x){//p的val小于x,将p挪至dummy之后；
                temp = p2.next;
                p2.next = temp.next;
                temp.next = null;
                p1.next = temp;
                p1 = p1.next;
            }else{
                p2 = p2.next;
            }
        }
        p1.next = dummy2.next;
        return dummy1.next;
    }
}
