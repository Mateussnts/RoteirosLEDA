package adt.linkedList;

import java.util.Arrays;

public class RecursiveSingleLinkedListImpl<T> implements LinkedList<T> {

	protected T data;
	protected RecursiveSingleLinkedListImpl<T> next;

	public RecursiveSingleLinkedListImpl() {

	}

	public RecursiveSingleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next) {
		this.data = data;
		this.next = next;
	}

	@Override
	public boolean isEmpty() {
		if (data == null)
			return true;
		return false;
	}

	@Override
	public int size() {
		if (isEmpty())
			return 0;
		return 1 + next.size();
	}

	@Override
	public T search(T element) {
		if (isEmpty())
			return null;
		else {
			if (data == element)
				return data;
			else
				return next.search(element);
		}
	}

	@Override
	public void insert(T element) {
		if (isEmpty()) {
			data = element;
			next = new RecursiveSingleLinkedListImpl<T>();
		} else {
			next.insert(element);
		}
	}

	@Override
	public void remove(T element) {
		if (isEmpty()) {
			// ...
		} else {
			if (data == element) {
				data = next.data;
				next = next.next;
			} else {
				next.remove(element);
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		T[] result = null;
		if (!this.isEmpty()) {
			result = (T[]) new Object[1];
			result[0] = this.data;
			result = this.juntaArrays(result, this.next.toArray());
		}
		if (result == null) {
			result = (T[]) new Object[0];
		}
		return result;
	}

	public T[] juntaArrays(T[] first, T[] second) {
		T[] result = Arrays.copyOf(first, first.length + second.length);
		System.arraycopy(second, 0, result, first.length, second.length);
		return result;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public RecursiveSingleLinkedListImpl<T> getNext() {
		return next;
	}

	public void setNext(RecursiveSingleLinkedListImpl<T> next) {
		this.next = next;
	}

}
