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
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        l1 = reverseList(l1);  // 反转链表l1
        l2 = reverseList(l2);  // 反转链表l2
        ListNode result = add(l1, l2, 0);  // 将两个链表相加
        return reverseList(result);  // 反转结果链表
    }

    // 反转链表的方法
   public ListNode reverseList(ListNode head) {
        ListNode newhead = new ListNode();
        if(head!=null){
            ListNode p = head.next;
            while(true){
                head.next= newhead.next;
                newhead.next = head;
                head = p;
                if(head == null){
                    break;
                }
                p = head.next;
            }
        }
        return newhead.next;
    }

    // 相加两个链表，带进位的处理
    public ListNode add(ListNode l1, ListNode l2, int carry) {
        if (l1 == null && l2 == null && carry == 0) {
            return null;  // 如果两个链表都为空且没有进位，返回null
        }
        
        int sum = carry;
        if (l1 != null) {
            sum += l1.val;  // 加上l1当前节点的值
            l1 = l1.next;  // 移动到l1的下一个节点
        }
        if (l2 != null) {
            sum += l2.val;  // 加上l2当前节点的值
            l2 = l2.next;  // 移动到l2的下一个节点
        }

        // 当前位的值和进位
        ListNode node = new ListNode(sum % 10);  // 当前位的值
        ListNode nextNode = add(l1, l2, sum / 10);  // 递归处理下一位，进位除以10
        node.next = nextNode;  // 将下一个节点链接到当前节点

        return node;  // 返回当前节点
    }
}
