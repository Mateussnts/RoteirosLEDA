package adt.linkedList;

public class DoubleLinkedListImpl<T> extends SingleLinkedListImpl<T> implements
		DoubleLinkedList<T> {

	protected DoubleLinkedListNode<T> last;

	public DoubleLinkedListImpl() {
		this.last = new DoubleLinkedListNode<>();
		super.setHead(this.last);
	}
	
	@Override
	public void insert(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>();
			DoubleLinkedListNode<T> newLast = new DoubleLinkedListNode<>(
					element, nil,
					this.last);
			
			if (super.isEmpty()) {
				this.last = newLast;
				super.setHead(this.last);
			}
			else {
				this.last.setNext(newLast);
				this.last = newLast;
			}
		}
	}
	
	@Override
	public void insertFirst(T element) {
		if (element != null) {
			DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>();
			DoubleLinkedListNode<T> newHead = new DoubleLinkedListNode<>(
					element, (DoubleLinkedListNode<T>)super.getHead(),
					nil);
			
			if (super.isEmpty()) {
				this.last = newHead;
				super.setHead(this.last);
			}
			else {
				((DoubleLinkedListNode<T>)super.getHead()).setPrevious(newHead);
				super.setHead(newHead);
			}
		}
	}

	@Override
	public void removeFirst() {
		if (!super.isEmpty()) {
			DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>();
			
			if (this.size() > 1) {
				DoubleLinkedListNode<T> newHead = (DoubleLinkedListNode<T>) 
						((DoubleLinkedListNode<T>)super.getHead()).getNext();
				super.setHead(newHead);
				((DoubleLinkedListNode<T>)super.getHead()).setPrevious(nil);
			}
			else if (this.size() == 1) {
				super.setHead(nil);
				this.last = (DoubleLinkedListNode<T>) 
						super.getHead();
			}
		}
	}

	@Override
	public void removeLast() {
		if (!super.isEmpty()) {
			DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>();
			
			if (this.size() > 1) {
				DoubleLinkedListNode<T> newLast = this.last.getPrevious();
				this.last = newLast;
				this.last.setNext(nil);
			}
			else if (this.size() == 1) {
				super.setHead(nil);
				this.last = (DoubleLinkedListNode<T>)
						super.getHead();
			}
		}
	}
	
	@Override
	public void remove(T element) {
		DoubleLinkedListNode<T> nil = new DoubleLinkedListNode<>();
		
		if (!super.isEmpty()) {
			if (super.getHead().getData().equals(element)) {
				super.setHead(super.getHead().getNext());
				((DoubleLinkedListNode<T>)super.getHead()).setPrevious(nil);
			}
			else if (this.last.getData().equals(element)) {
				this.last = this.last.getPrevious();
				this.last.setNext(nil);
			}
			DoubleLinkedListNode<T> auxHead = (DoubleLinkedListNode<T>) 
					super.getHead();
			DoubleLinkedListNode<T> auxLast = this.last;
			for (int i = 0; i < super.size()/2; i++) {
				if (auxHead.getData().equals(element)) {
					while (!auxHead.getNext().isNIL()) {
						auxHead.setData(auxHead.getNext().getData());
						auxHead = (DoubleLinkedListNode<T>) auxHead.getNext();
					}
					auxHead.setData(null);
					this.last = (DoubleLinkedListNode<T>) 
							this.last.getPrevious();
					this.last.setNext(nil);
					break;
				}
				else if (auxLast.getData().equals(element)) {
					while (!auxLast.getPrevious().isNIL()) {
						auxLast.setData(auxLast.getPrevious().getData());
						auxLast = auxLast.getPrevious();
					}
					auxLast.setData(null);
					super.setHead(super.getHead().getNext());
					((DoubleLinkedListNode<T>)super.getHead())
					.setPrevious(nil);
					break;
				}
				auxHead = (DoubleLinkedListNode<T>) auxHead.getNext();
				auxLast = auxLast.getPrevious();
			}
		}
	}
	
	public DoubleLinkedListNode<T> getLast() {
		return last;
	}

	public void setLast(DoubleLinkedListNode<T> last) {
		this.last = last;
	}
}
