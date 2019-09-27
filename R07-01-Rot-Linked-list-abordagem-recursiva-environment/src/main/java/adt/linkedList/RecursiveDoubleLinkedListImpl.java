package adt.linkedList;

public class RecursiveDoubleLinkedListImpl<T> extends RecursiveSingleLinkedListImpl<T> implements DoubleLinkedList<T> {

	protected RecursiveDoubleLinkedListImpl<T> previous;

	public RecursiveDoubleLinkedListImpl() {

	}

	public RecursiveDoubleLinkedListImpl(T data, RecursiveSingleLinkedListImpl<T> next,
			RecursiveDoubleLinkedListImpl<T> previous) {
		super(data, next);
		this.previous = previous;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			if (this.isEmpty()) {
				insertFirst(element);
			} else {
				this.next.insert(element);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (!isEmpty() && element != null) {
			if (this.data.equals(element)) {
				this.removeFirst();
			} else {
				this.next.remove(element);
			}
		}
	}

	@Override
	public void insertFirst(T element) {
		if (element != null) {
			if (!this.isEmpty()) {
				next = new RecursiveDoubleLinkedListImpl<T>(this.data, this.next, this);
				((RecursiveDoubleLinkedListImpl<T>) this.next).setPrevious((RecursiveDoubleLinkedListImpl<T>) next);
				setData(element);
				setNext(next);
			} else {
				next = new RecursiveDoubleLinkedListImpl<T>();
				setData(element);
				this.previous = ((RecursiveDoubleLinkedListImpl<T>) next);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!this.isEmpty()) {
			this.data = this.next.data;
			if (!this.next.isEmpty()) {
				this.next = this.next.next;
				((RecursiveDoubleLinkedListImpl<T>) this.next).previous = this;
			}
		}
	}

	@Override
	public void removeLast() {
		if (!isEmpty()) {
			if (this.next.isEmpty()) {
				this.next = null;
				this.data = null;
			} else {
				((RecursiveDoubleLinkedListImpl<T>) this.next).removeLast();
			}
		}
	}

	public RecursiveDoubleLinkedListImpl<T> getPrevious() {
		return previous;
	}

	public void setPrevious(RecursiveDoubleLinkedListImpl<T> previous) {
		this.previous = previous;
	}
}
