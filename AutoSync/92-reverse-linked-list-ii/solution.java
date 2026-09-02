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
    public ListNode reverseBetween(ListNode head, int left, int right) {
        //将left至right的链表片段利用头插法进行重新排序，然后再插入原来的列表
        if(head.next==null){
            return head;
        }
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0,head);
        ListNode temp,p1,p2,tail;
        p1 = dummy2;
        p2 = head;
        tail = dummy2;
        int i = 0;
        while(p2.next!=null){
            if(i == left-1){//利用头插法，将其连接在dummy1之后
            tail = p2;
            do{
                i++;
                temp = p2.next;
                p2.next=dummy1.next;
                dummy1.next = p2;
                p1.next = temp;
                p2 = p1.next;
            }while(i !=right && left != right);
            break;
        }else{
            p1 = p1.next;
            p2 = p2.next;
        }
        i++;
    }
        if(dummy1.next != null){
            tail.next = p1.next;
            p1.next = dummy1.next;
        }
        return dummy2.next;
    }
}
