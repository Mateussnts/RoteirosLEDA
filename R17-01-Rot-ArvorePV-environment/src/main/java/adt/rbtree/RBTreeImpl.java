package adt.rbtree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;
import adt.rbtree.RBNode.Colour;

public class RBTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements RBTree<T> {

	public RBTreeImpl() {
		this.root = new RBNode<T>();
	}

	protected int blackHeight() {
		return blackHeight((RBNode<T>) this.root);
	}

	private int blackHeight(RBNode<T> node) {
		if (node.isEmpty())
			return 0;

		int height;

		if (isBlack(node))
			height = 1;
		else
			height = 0;

		return height + Math.max(blackHeight((RBNode<T>) node.getLeft()), blackHeight((RBNode<T>) node.getRight()));
	}

	private boolean isBlack(RBNode<T> node) {
		if (node != null)
			return node.getColour() == Colour.BLACK;
		return true;
	}

	protected boolean verifyProperties() {
		boolean resp = verifyNodesColour() && verifyNILNodeColour() 
						&& verifyRootColour() && verifyChildrenOfRedNodes()
						&& verifyBlackHeight();

		return resp;
	}

	/**
	 * The colour of each node of a RB tree is black or red. This is guaranteed
	 * by the type Colour.
	 */
	private boolean verifyNodesColour() {
		return true; // already implemented
	}

	/**
	 * The colour of the root must be black.
	 */
	private boolean verifyRootColour() {
		return this.isBlack((RBNode<T>) getRoot()); // already
																// implemented
	}

	/**
	 * This is guaranteed by the constructor.
	 */
	private boolean verifyNILNodeColour() {
		return true; // already implemented
	}

	/**
	 * Verifies the property for all RED nodes: the children of a red node must
	 * be BLACK.
	 */
	private boolean verifyChildrenOfRedNodes() {
		return verifyChildrenOfRedNodes((RBNode<T>) getRoot());
	}

	private boolean verifyChildrenOfRedNodes(RBNode<T> node) {
		if (node.isEmpty()) return true;
		
		if (!this.isBlack(node)) {
			if (!this.isBlack((RBNode<T>) node.getLeft()) || !this.isBlack((RBNode<T>) node.getRight()))
				return false;
		}
		return this.verifyChildrenOfRedNodes((RBNode<T>) node.getRight())
				&& this.verifyChildrenOfRedNodes((RBNode<T>) node.getLeft());
	
	}

	/**
	 * Verifies the black-height property from the root. The method blackHeight
	 * returns an exception if the black heights are different.
	 */
	private boolean verifyBlackHeight() {
		int leftHeight = verifyBlackHeight((RBNode<T>) this.getRoot().getLeft(), 0);
		int rightHeight = verifyBlackHeight((RBNode<T>) this.getRoot().getRight(), 0);

		if (rightHeight == leftHeight) return true;
			return false;
	}

	private int verifyBlackHeight(RBNode<T> node, int height) {
		if (node == null || node.isEmpty())
			return height++;

		if(this.isBlack(node))
			height++;
		
		return Math.max(verifyBlackHeight((RBNode<T>) node.getLeft(), height),
				verifyBlackHeight((RBNode<T>) node.getRight(), height));
	}

	@Override
	public void insert(T value) {
		if (value != null)
			this.insert((RBNode<T>) this.getRoot(), value);
	}
	
	private RBNode<T> insert(RBNode<T> node, T value) {
		RBNode<T> insert = node;
		if (node.isEmpty()) {
			
			node.setData(value);
			node.setColour(Colour.RED);
			node.setLeft(new RBNode<T>());
			node.getLeft().setParent(node);
			node.setRight(new RBNode<T>());
			node.getRight().setParent(node);
			if (node.getParent() == null)
				// mantendo o padrao de nodes nil upside down
				node.setParent(new BSTNode<T>());

			fixUpCase1(node);

		} else {
			int comparation = node.getData().compareTo(value);
			
			if (comparation == 1)
				insert = insert((RBNode<T>) node.getLeft(), value);
			else if (comparation == -1)
				insert = insert((RBNode<T>) node.getRight(), value);
		}
		
		return insert;
	}


	@Override
	public RBNode<T>[] rbPreOrder() {
		return this.rbPreOrder((RBNode<T>) this.getRoot());
	}

	@SuppressWarnings("unchecked")
	private RBNode<T>[] rbPreOrder(RBNode<T> node) {
		RBNode<T>[] array = new RBNode[0];
		
		if (node.isEmpty())
			return array;
		
		RBNode<T>[] left = this.rbPreOrder((RBNode<T>) node.getLeft());
		RBNode<T>[] right = this.rbPreOrder((RBNode<T>) node.getRight());
		
		array = (RBNode<T>[]) new RBNode[1 + left.length + right.length];
		int index = 0;
		array[index++] = node;
		
		for(RBNode<T> element : left)
			array[index++] = element;
		for(RBNode<T> element : right)
			array[index++] = element;
		
		return array;
		
	}

	// FIXUP methods
	protected void fixUpCase1(RBNode<T> node) {
		if (node.equals(this.getRoot()))
			node.setColour(Colour.BLACK);
		else
			this.fixUpCase2(node);
	}

	protected void fixUpCase2(RBNode<T> node) {
		if (!this.isBlack((RBNode<T>) node.getParent()))
			this.fixUpCase3(node);
	}

	protected void fixUpCase3(RBNode<T> node) {
		RBNode<T> uncle;
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandParent = (RBNode<T>) parent.getParent();
		
		if (this.isLeftChild(parent))
			uncle = (RBNode<T>) grandParent.getRight();
		else
			uncle = (RBNode<T>) grandParent.getLeft();

		if (!this.isBlack(uncle)) {
			parent.setColour(Colour.BLACK);
			uncle.setColour(Colour.BLACK);
			grandParent.setColour(Colour.RED);
			this.fixUpCase1(grandParent);
		}
		else {
			this.fixUpCase4(node);
		}
	}

	protected void fixUpCase4(RBNode<T> node) {
		RBNode<T> next = node;
		
		if (!this.isLeftChild(node) && this.isLeftChild((RBNode<T>) node.getParent())) {
			Util.leftRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getLeft();
		}
		else if (this.isLeftChild(node) && !this.isLeftChild((RBNode<T>) node.getParent())) {
			Util.rightRotation((BSTNode<T>) node.getParent());
			next = (RBNode<T>) node.getRight();
		}
		this.fixUpCase5(next);
	}

	protected void fixUpCase5(RBNode<T> node) {
		RBNode<T> parent = (RBNode<T>) node.getParent();
		RBNode<T> grandFather = (RBNode<T>) parent.getParent();

		parent.setColour(Colour.BLACK);
		grandFather.setColour(Colour.RED);

		RBNode<T> newNode;
		if (isLeftChild(node))
			newNode = (RBNode<T>) Util.rightRotation(grandFather);
		else
			newNode = (RBNode<T>) Util.leftRotation(grandFather);

		if (grandFather.getData().equals(this.getRoot().getData()))
			this.root = newNode;
	}

	private boolean isLeftChild(RBNode<T> node) {
		if (node.getParent().getLeft().equals(node))
			return true;
		return false;
	}
}