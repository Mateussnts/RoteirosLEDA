package adt.queue;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.DoubleLinkedListImpl;

public class QueueDoubleLinkedListImpl<T> implements Queue<T> {

	protected DoubleLinkedList<T> list;
	protected int size;

	public QueueDoubleLinkedListImpl(int size) {
		this.size = size;
		this.list = new DoubleLinkedListImpl<T>();
	}

	@Override
	public void enqueue(T element) throws QueueOverflowException {
		if (!this.isFull()) {
			this.list.insert(element);
		}
		else {
			throw new QueueOverflowException();
		}
	}

	@Override
	public T dequeue() throws QueueUnderflowException {
		if (!this.list.isEmpty()) {
			T resultado = this.head();
			this.list.removeFirst();
			return resultado;
		}
		else {
			throw new QueueUnderflowException();
		}
	}

	@Override
	public T head() {
		return (((DoubleLinkedListImpl<T>) this.list).getHead())
		.getData();
	}

	@Override
	public boolean isEmpty() {
		return this.list.isEmpty();
	}

	@Override
	public boolean isFull() {
		boolean resultado = false;
		if (this.list.size() >= this.size) {
			return true;
		}
		return resultado;
	}
}