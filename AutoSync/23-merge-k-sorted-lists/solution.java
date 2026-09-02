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
    public ListNode mergeKLists(ListNode[] lists) {
        ListNode p = null;
        for(int i = 0; i < lists.length; i++){
            p = mergeKLists2(p,lists[i]);
        }
        return p;
    }
    public ListNode mergeKLists2(ListNode l1,ListNode l2){
        ListNode p1 = null,p2=null;
        if(l1==null){
            return l2;
        }else if(l2 == null){
            return l1;
        }else{
            if(l1.val < l2.val){
                l1.next = mergeKLists2(l1.next,l2);
                return l1;
            }else{
                l2.next = mergeKLists2(l1,l2.next);
                return l2;
            }
        }
    }
}
