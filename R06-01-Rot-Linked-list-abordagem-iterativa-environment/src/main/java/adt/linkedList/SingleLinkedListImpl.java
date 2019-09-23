package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;
	protected SingleLinkedListNode<T> tail;
	private int size = 0;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
		this.tail = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		if(head == null) {
			return true;
		}
		return false;
	} 

	@Override
	public int size() {
		return this.size;
	}

	@Override
	public T search(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented!");
	}

	@Override
	public void insert(T element) {
		head = new SingleLinkedListNode<>(element, head);
		if(size == 0) {
			tail = head;
		}
		size++;
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented!");
	}

	@Override
	public T[] toArray() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented!");
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}

}
