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
		return root.isEmpty();
	}

	@Override
	public int height() {
		return this.height(root);
	}
	
	protected int height(BSTNode<T> node) {
		if (node.isEmpty())
			return -1;

		return Math.max(this.height((BSTNode<T>) node.getLeft()), 
				this.height((BSTNode<T>) node.getRight())) + 1;
	}

	@Override
	public BSTNode<T> search(T element) {
		return this.search(root, element);
	}

	private BSTNode<T> search(BSTNode<T> node, T element) {

		if (node.isEmpty() || element == null)
			return new BSTNode<T>();

		if (node.getData().equals(element))
			return node;

		if (node.getLeft() != null && element.compareTo(node.getData()) < 0)
			return this.search((BSTNode<T>) node.getLeft(), element);
		else
			return this.search((BSTNode<T>) node.getRight(), element);
	}

	@Override
	public void insert(T element) {
		this.insert(root, element);
	}

	private void insert(BSTNode<T> node, T element) {
		if (element != null) {
			if (node.isEmpty()) {
				node.setData(element);
				node.setLeft(new BSTNode<T>());
				node.setRight(new BSTNode<T>());

				if (node.getParent() == null) {
					node.setParent(new BSTNode<T>());
				}

				node.getLeft().setParent(node);
				node.getRight().setParent(node);
			}
			else {
				if (element.compareTo(node.getData()) < 0) {
					this.insert((BSTNode<T>) node.getLeft(), element);
				} 
				else if (element.compareTo(node.getData()) > 0) {
					this.insert((BSTNode<T>) node.getRight(), element);
				}
			}
		}
	}

	@Override
	public BSTNode<T> maximum() {
		return maximum(root);

	}

	private BSTNode<T> maximum(BSTNode<T> node) {
		if (node.isEmpty())
			return null;
		if (node.getRight().isEmpty())
			return node;
		return this.maximum((BSTNode<T>) node.getRight());
	}

	@Override
	public BSTNode<T> minimum() {
		return minimum(root);
	}

	private BSTNode<T> minimum(BSTNode<T> node) {
		if (node.isEmpty())
			return null;
		if (node.getLeft().isEmpty())
			return node;
		return this.minimum((BSTNode<T>) node.getLeft());
	}

	@Override
	public BSTNode<T> sucessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		if (!node.getRight().isEmpty()) {
			return minimum((BSTNode<T>) node.getRight());
		} 
		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			while (!parent.isEmpty() && node.equals(parent.getRight())) {
				node = parent;
				parent = (BSTNode<T>) parent.getParent();
			}
			if (parent.isEmpty()) {
				return null;
			}
			return parent;
		}
	}

	@Override
	public BSTNode<T> predecessor(T element) {
		BSTNode<T> node = this.search(element);

		if (node.isEmpty())
			return null;

		if (!node.getLeft().isEmpty()) {
			return maximum((BSTNode<T>) node.getLeft());
		}
		else {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			while (!parent.isEmpty() && node.equals(parent.getLeft())) {
				node = parent;
				parent = (BSTNode<T>) parent.getParent();
			}
			if (parent.isEmpty()) {
				return null;
			}
			return parent;
		}
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = search(element);
		if (node.isEmpty()) 
			return;
		remove(node);
	}

	private void remove(BSTNode<T> node) {
		if (node.isEmpty()) 
			return;
		if (!node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
			BSTNode<T> newNode = sucessor(node.getData());
			node.setData(newNode.getData());
			remove(newNode);
		}
		else {
			BSTNode<T> newNode = (BSTNode<T>) node.getLeft();
			
			if (newNode.isEmpty())
				newNode = (BSTNode<T>) node.getRight();
			
			node.setData(newNode.getData());
			node.setRight(newNode.getRight());
			node.setLeft(newNode.getLeft());
			
			if (!node.isEmpty() && node.getRight() != null) 
				node.getRight().setParent(node);
			if (!node.isEmpty() && node.getLeft() != null) 
				node.getLeft().setParent(node);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] preOrder() {
		T[] preOrd = (T[]) new Comparable[this.size()];
		if (this.isEmpty())
			return preOrd;
		preOrder(preOrd, root, 0);
		return preOrd;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int preOrder(T[] preOrd, BSTNode<T> node, int index) {
		preOrd[index++] = node.getData();
		
		if (!node.getLeft().isEmpty())
			index = preOrder(preOrd, (BSTNode) node.getLeft(), index);
		
		if (!node.getRight().isEmpty())
			index = preOrder(preOrd, (BSTNode) node.getRight(), index);
		
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] order() {
		T[] inOrd = (T[]) new Comparable[this.size()];
		if (this.isEmpty())
			return inOrd;
		order(inOrd, root, 0);
		return inOrd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int order(T[] inOrd, BSTNode<T> node, int index) {
		if (!node.getLeft().isEmpty()) 
			index = order(inOrd,(BSTNode) node.getLeft(), index);
		
		inOrd[index++] = node.getData();
		
		if (!node.getRight().isEmpty()) 
			index = order(inOrd,(BSTNode) node.getRight(), index);
		return index;
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] postOrder() {
		T[] posOrd  = (T[]) new Comparable[this.size()];
		if (this.isEmpty()) 
			return posOrd;
		postOrder(posOrd, root, 0);
		return posOrd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int postOrder(T[] posOrd, BSTNode<T> node, int index) {
		if (!node.getLeft().isEmpty()) 
			index = postOrder(posOrd,(BSTNode) node.getLeft(), index);
		
		if (!node.getRight().isEmpty()) 
			index = postOrder(posOrd,(BSTNode) node.getRight(), index);
		
		posOrd[index++] = node.getData();
		return index;
	}

	/**
	 * This method is already implemented using recursion. You must understand
	 * how it work and use similar idea with the other methods.
	 */
	@Override
	public int size() {
		return size(root);
	}

	private int size(BSTNode<T> node) {
		int result = 0;
		// base case means doing nothing (return 0)
		if (!node.isEmpty()) { // indusctive case
			result = 1 + size((BSTNode<T>) node.getLeft())
					+ size((BSTNode<T>) node.getRight());
		}
		return result;
	}
}