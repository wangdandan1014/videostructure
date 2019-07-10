package com.sensing.core.test;

public class Node {

	public int data;

	public Node next;

	public Node(int data) {
		this.data = data;
	}

	public int getData() {
		return data;
	}

	public void setData(int data) {
		this.data = data;
	}

	public Node getNext() {
		return next;
	}

	public void setNext(Node next) {
		this.next = next;
	}
	
	/**
     * 向链表添加数据
     *
     * @param value 要添加的数据
     */
    public static void addData(int value) {

//        //初始化要加入的节点
//        Node newNode = new Node(value);
//
//        //临时节点
//        Node temp = head;
//
//        // 找到尾节点
//        while (temp.next != null) {
//            temp = temp.next;
//        }
//
//        // 已经包括了头节点.next为null的情况了～
//        temp.next = newNode;

    }
	
	

}
