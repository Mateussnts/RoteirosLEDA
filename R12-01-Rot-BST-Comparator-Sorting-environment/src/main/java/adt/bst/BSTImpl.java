package adt.bst;

public class BSTImpl<T extends Comparable<T>> implements BST<T> {

	protected BSTNode<T> root;

	public BSTImpl() {
		root = new BSTNode<T>();
	}

	public BSTNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return root.equals(null);
	}

	@Override
	public int height() {
		return altura(this.root);
	}

	private int altura(BSTNode<T> node) {
		int contleft = -1;
		int contRight = -1;

		if (!node.isEmpty()) {
			contleft = 1 + altura((BSTNode<T>) node.getLeft());
			contRight = 1 + altura((BSTNode<T>) node.getRight());
		}
		if (contleft >= contRight) {
			return contleft;
		}
		return contRight;
	}

	@Override
	public BSTNode<T> search(T element) {
		if (isEmpty())
			return new BSTNode<T>();
		else {
			return search(this.root, element);
		}
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {
		if(!node.isEmpty()) {
			
			if(node.getData().equals(element)) {
				return node;
			}
			
			else if(node.getData().compareTo(element) > 0){
				return search((BSTNode<T>) node.getLeft(),element);
			}
			else {
				return search((BSTNode<T>) node.getRight(),element);
			}
		}
		return new BSTNode<>();
	}

	@Override
	public void insert(T element) {
		if(element != null) {
			inserir(this.root,element);
		}
	}

	
	private void inserir(BSTNode<T> node, T element) {
		if(node.isEmpty()) {
			node.setData(element);
			node.setLeft(new BSTNode<>());
			node.setRight(new BSTNode<>());
			node.getLeft().setParent(node);
			node.getRight().setParent(node);	
		}
		
		else if(node.getData().compareTo(element) > 0) {
			inserir((BSTNode<T>) node.getLeft(), element);
		}
		
		else if(node.getData().compareTo(element) < 0) {
			inserir((BSTNode<T>) node.getRight(), element);
		}
	}

	@Override
	public BSTNode<T> maximum() {
		if(isEmpty()) {
			return null;
		}
		else {
			return maximo(this.root);
		}
	}

	private BSTNode<T> maximo(BSTNode<T> node) {
		if(node.isEmpty()) {
			return null;
		}
		else if(node.getRight().isEmpty()) {
			return node;
		}
		else {
			return maximo((BSTNode<T>) node.getRight());
		}
	}

	@Override
	public BSTNode<T> minimum() {
		if(isEmpty()) {
			return null;
		}
		else {
			return minimo(this.root);
		}
	}

	private BSTNode<T> minimo(BSTNode<T> node) {
		if(node.isEmpty()) {
			return null;
		}
		else if(node.getLeft().isEmpty()){
			return node;
		}
		else {
			return minimo((BSTNode<T>) node.getLeft());
		}
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		 BSTNode<T> node = search(element);
		 if(node.isEmpty() || element == null || this.getRoot().isEmpty()) {
			 return null;
		 }
		 else if(!node.getRight().isEmpty()) {
			 return minimo((BSTNode<T>) node.getRight());
		 }
		 else {
			 return sucessor(node,element);
		 }
	}

	@SuppressWarnings("unchecked")
	private BSTNode<T> sucessor(BSTNode<T> node, T element) {
		if(node.getParent() == null) {
			return null;
		}
		else if(node.getParent() != null && node.getParent().getData().compareTo(element) < 0) {
			return sucessor((T) node.getRight());
		}
		else {
			return (BSTNode<T>) node.getParent();
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public void remove(T element) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] preOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] order() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	@Override
	public T[] postOrder() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Not implemented yet!");
	}

	/**
	 * This method is already implemented using recursion. You must understand how
	 * it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft()) + size((BSTNode<T>) node.getRight());
		}
		return result;
	}

}
