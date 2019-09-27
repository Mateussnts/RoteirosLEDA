package adt.stack;

import adt.linkedList.DoubleLinkedList;
import adt.linkedList.RecursiveDoubleLinkedListImpl;

public class StackRecursiveDoubleLinkedListImpl<T> implements Stack<T> {

	protected DoubleLinkedList<T> top;
	protected int size;

	public StackRecursiveDoubleLinkedListImpl(int size) {
		this.size = size;
		this.top = new RecursiveDoubleLinkedListImpl<T>();
	}

	@Override
	public void push(T element) throws StackOverflowException {
		if (!this.isFull()) {
			this.top.insertFirst(element);
		} else {
			throw new StackOverflowException();
		}
	}

	@Override
	public T pop() throws StackUnderflowException {
		if (!this.top.isEmpty()) {
			T resultado = this.top();
			this.top.removeFirst();
			return resultado;
		} else {
			throw new StackUnderflowException();
		}
	}

	@Override
	public T top() {
		return ((RecursiveDoubleLinkedListImpl<T>) this.top).getData();
	}

	@Override
	public boolean isEmpty() {
		return this.top.isEmpty();
	}

	@Override
	public boolean isFull() {
		boolean resultado = false;
		if (this.top.size() >= this.size) {
			return true;
		}
		return resultado;
	}
}