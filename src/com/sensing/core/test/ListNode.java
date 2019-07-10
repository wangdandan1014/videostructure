package com.sensing.core.test;

public class ListNode {

	public int data;

	public ListNode next;

	public ListNode(int data) {
		this.data = data;
	}
	
	public ListNode reverse1(ListNode head) {
        // 当为空或者本节点为末尾节点的时候
        if (head == null || head.next == null)
            return head;
        ListNode temp = head.next;
        ListNode reversedHead = reverse1(head.next);
        // 获取先前的下一个节点，让该节点指向自身
        temp.next = head;
        // 破坏以前自己指向下一个节点
        head.next = null;
        // 层层传递给最上面的
        return reversedHead;
    }
}
