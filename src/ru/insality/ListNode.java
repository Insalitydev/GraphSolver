package ru.insality;

public class ListNode {

	public int data;
	public int weight;
	
	
	public ListNode(int data, int weight){
		this.data = data;
		this.weight = weight;
	}
	
	public ListNode(int data){
		this(data, 1);
	}
	
}
