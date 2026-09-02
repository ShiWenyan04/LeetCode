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
    public ListNode reverseKGroup(ListNode head, int k) {
        if(head.next == null||k==1){
            return head;
        }
        ListNode dummy1 = new ListNode(0);
        ListNode dummy2 = new ListNode(0,head);
        ListNode p,tail,temp = null,temp1,c;
        c=head;//计数的指针
        int count = 0;
        while(c!=null){//计数
            count++;
            c=c.next;
        }
        p = dummy2.next;
        tail = dummy1;
        int i = 1;
        int y = 1;//计算剩下的节点数
        while(p!=null && count-y+1 >=k){
            temp = p;//temp为每一组反转后的最后一个值，便于将tail引到链表末尾，然后再头插
            while(i <=k && p!=null){
                temp1 = p.next;//保存原数组的地址
                p.next= tail.next;
                tail.next = p;
                p = temp1;//p回到原数组
                i++;

            }
            y+=k;
            tail = temp;//tail位于新链表的末尾，便于下一组的头插
            i=1;
        }
        if(p != null){
            tail.next = p; 
        }
        return dummy1.next;
    }
}
