package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		if (this.head.data == null)
			return true;
		return false;
	}

	@Override
	public int size() {
		int size = 0;
		SingleLinkedListNode<T> aux = this.head;
		while (!aux.isNIL()) {
			size += 1;
			aux = aux.next;
		}
		return size;
	}

	@Override
	public T search(T element) {
		if (this.head.data.equals(element)) {
			return this.head.data;
		} else {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL() && !aux.data.equals(element)) {
				aux = aux.next;
			}
			if (!aux.isNIL()) {
				return aux.data;
			}
		}
		return null;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			SingleLinkedListNode<T> auxHead = this.head;
			SingleLinkedListNode<T> newHead;
			SingleLinkedListNode<T> newNode;
			if (this.head.isNIL()) {
				newHead = new SingleLinkedListNode<>(element, this.head);
				this.head = newHead;
			} else {
				while (!auxHead.next.isNIL()) {
					auxHead = auxHead.next;
				}
				newNode = new SingleLinkedListNode<>(element, new SingleLinkedListNode<>());
				newNode.next = auxHead.next;
				auxHead.next = newNode;
			}
		}
	}

	@Override
	public void remove(T element) {
		if (!this.isEmpty()) {
			if (this.head.data.equals(element)) {
				this.head = this.head.next;
			} else {
				SingleLinkedListNode<T> aux = this.head;
				SingleLinkedListNode<T> previous = aux;
				while (!aux.isNIL() && aux.data != element) {
					previous = aux;
					aux = aux.next;
				}
				if (!aux.isNIL()) {
					previous.next = aux.next;
				}
			}
		}
	}

	@Override
	public T[] toArray() {
		@SuppressWarnings("unchecked")
		T[] resultado = (T[]) new Object[this.size()];
		SingleLinkedListNode<T> aux = this.head;
		int count = 0;
		while (!aux.isNIL()) {
			resultado[count] = aux.data;
			count += 1;
			aux = aux.next;
		}
		return resultado;
	}

	public SingleLinkedListNode<T> getHead() {
		return this.head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}