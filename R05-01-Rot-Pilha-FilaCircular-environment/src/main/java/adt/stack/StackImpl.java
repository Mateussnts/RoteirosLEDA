package adt.stack;

public class StackImpl<T> implements Stack<T> {

	private T[] array;
	private int top;

	
	public StackImpl(int size) {
		array = (T[]) new Object[size];
		top = -1;
	}

	@Override
	public T top() {
		if(this.isEmpty()) {
			return null;
		}
		return this.array[this.top];
	}

	@Override
	public boolean isEmpty() {
		if (top == -1)
			return true;
		return false;
	}

	@Override
	public boolean isFull() {
		return this.top == array.length -1;
	}

	@Override
	public void push(T element){
		if(this.top < this.array.length -1)
			this.array[++top] = element;
	}

	@Override
	public T pop(){
		if(this.isEmpty() == true) {
			return null;
		}
		return this.array[this.top --];
	}

}
