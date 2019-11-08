package adt.avltree;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;
import adt.bt.Util;

/**
 * 
 * Implementacao de uma arvore AVL
 * 
 * @author Claudio Campelo
 *
 * @param <T>
 */

public class AVLTreeImpl<T extends Comparable<T>> extends BSTImpl<T> implements AVLTree<T> {

	// AUXILIARY
	protected int calculateBalance(BSTNode<T> node) {
		int resultado = 0;

		if (node != null && !node.isEmpty()) {
			BSTNode<T> nodeLeft = (BSTNode<T>) node.getLeft();
			BSTNode<T> nodeRight = (BSTNode<T>) node.getRight();
			resultado = super.height(nodeLeft) - super.height(nodeRight);
		}

		return resultado;
	}

	// AUXILIARY
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			boolean isRoot = false;
			if (node.getParent().isEmpty()) {
				isRoot = true;
			}
			int balance = this.calculateBalance(node);

			if (balance > 1) {
				if (super.height((BSTNode<T>) node.getLeft().getRight()) > super.height(
						(BSTNode<T>) node.getLeft().getLeft())) {
					node.setLeft(Util.leftRotation((BSTNode<T>) node.getLeft()));
				}
				if (isRoot) {
					this.root = Util.rightRotation(node);
				} else {
					if (this.isChildPerLeft(node)) {
						node.getParent().setLeft(Util.rightRotation(node));
					} else {
						node.getParent().setRight(Util.rightRotation(node));
					}
				}

			} else if (balance < -1) {
				if (super.height((BSTNode<T>) node.getRight().getLeft()) > super.height(
						(BSTNode<T>) node.getRight().getRight())) {
					node.setRight(Util.rightRotation((BSTNode<T>) node.getRight()));
				}
				if (isRoot) {
					this.root = Util.leftRotation(node);
				} else {
					if (this.isChildPerLeft(node)) {
						node.getParent().setLeft(Util.leftRotation(node));
					} else {
						node.getParent().setRight(Util.leftRotation(node));
					}
				}

			}
		}
	}

	// AUXILIARY
	protected void rebalanceUp(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			this.rebalance(node);
			this.rebalanceUp((BSTNode<T>) node.getParent());
		}
	}

	protected boolean isChildPerLeft(BSTNode<T> node) {
		boolean result = false;
		if (node != null && !node.isEmpty()) {
			BSTNode<T> parent = (BSTNode<T>) node.getParent();
			if (parent.getLeft().equals(node)) {
				result = true;
			}
		}
		return result;
	}

	@Override
	public void insert(T element) {
		super.insert(element);
		BSTNode<T> node = this.search(element);
		this.rebalanceUp(node);
	}

	@Override
	public void remove(T element) {
		BSTNode<T> node = this.search(element);
		this.remove(node);
	}

	private void remove(BSTNode<T> node) {
		if (!node.isEmpty()) {
			if (node.isLeaf()) {
				node.setData(null);
				node.setLeft(null);
				node.setRight(null);
				this.rebalanceUp((BSTNode<T>) node.getParent());
			} else if (!node.getLeft().isEmpty() && node.getRight().isEmpty()
					|| node.getLeft().isEmpty() && !node.getRight().isEmpty()) {
				if (!this.root.equals(node)) {
					if (this.isChildPerLeft(node)) {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setLeft(node.getLeft());
						} else {
							node.getParent().setLeft(node.getRight());
						}
					} else {
						if (!node.getLeft().isEmpty()) {
							node.getParent().setRight(node.getLeft());
						} else {
							node.getParent().setRight(node.getRight());
						}
					}
				} else {
					if (!node.getLeft().isEmpty()) {
						this.root = (BSTNode<T>) node.getLeft();
					} else {
						this.root = (BSTNode<T>) node.getRight();
					}
				}
				this.rebalanceUp(node);
			} else {
				BSTNode<T> sucessor = super.sucessor(node.getData());
				node.setData((T) sucessor.getData());
				this.remove(sucessor);
			}
		}
	}
}