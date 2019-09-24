package adt.linkedList;

public class SingleLinkedListImpl<T> implements LinkedList<T> {

	protected SingleLinkedListNode<T> head;

	public SingleLinkedListImpl() {
		this.head = new SingleLinkedListNode<T>();
	}

	@Override
	public boolean isEmpty() {
		boolean retorno = false;
		if (this.head.getData() == null) {
			retorno = true;
		}
		return retorno;
	}

	@Override
	public int size() {
		int resultado = 0;
		
		SingleLinkedListNode<T> aux = this.head;
		while (!aux.isNIL()) {				
			resultado++;
			aux = aux.getNext();
		}
		return resultado;
	}

	@Override
	public T search(T element) {
		T resultado = null;
		if (!this.isEmpty()) {
			SingleLinkedListNode<T> aux = this.head;
			while (!aux.isNIL()) {
				if (aux.getData().equals(element)) {
					resultado = aux.getData();
					break;
				}
				aux = aux.getNext();
			}
		}
		return resultado;
	}

	@Override
	public void insert(T element) {
		if (element != null) {
			SingleLinkedListNode<T> nil = new SingleLinkedListNode<>();
			
			if (this.size() > 0) {
				SingleLinkedListNode<T> aux = this.head;
				while (!aux.getNext().isNIL()) {
					aux = aux.getNext();
				}
				aux.getNext().setData(element);	
				aux.getNext().setNext(nil);
			}
			else {
				this.head.setData(element);
				this.head.setNext(nil);
			}
		}
	}

	@Override
	public void remove(T element) {
		if (element != null && !this.isEmpty()) {
			if (this.head.getData().equals(element)) {
				this.head = this.head.getNext();
			}
			else {
				SingleLinkedListNode<T> placeHolder = 
						new SingleLinkedListNode<T>();
				SingleLinkedListNode<T> aux = this.getHead();

				while (!aux.isNIL() && !aux.getData().equals(element)) {
					placeHolder = aux;
					aux = aux.getNext();
				}
				
				if (!aux.isNIL()) {
					placeHolder.setNext(aux.getNext());
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] toArray() {
		int size = this.size();
		T[] resultado = (T[]) new Object[size];

		SingleLinkedListNode<T> aux = this.head;
		for (int i = 0; i < size; i++) {
			resultado[i] = aux.getData();
			aux = aux.getNext();
		}
		return resultado;
	}

	public SingleLinkedListNode<T> getHead() {
		return head;
	}

	public void setHead(SingleLinkedListNode<T> head) {
		this.head = head;
	}
}