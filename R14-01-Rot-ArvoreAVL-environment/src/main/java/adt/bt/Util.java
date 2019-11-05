package adt.bt;

import adt.bst.BSTNode;

public class Util {

	/**
	 * A rotacao a esquerda em node deve subir e retornar seu filho a direita
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T extends Comparable<T>> BSTNode<T> leftRotation(BSTNode<T> node) {
		BSTNode<T> pivot = null;

		if (!node.isEmpty()) {
			pivot = (BSTNode) node.getRight();
			pivot.setParent(node.getParent());

			if (!pivot.getParent().isEmpty()) {
				if (pivot.getParent().getRight().equals(node)) {
					pivot.getParent().setRight(pivot);
				} else {
					pivot.getParent().setLeft(pivot);
				}
			}

			node.setParent(pivot);
			node.setRight(pivot.getLeft());
			node.getRight().setParent(node);
			pivot.setLeft(node);
		}

		return pivot;
	}

	/**
	 * A rotacao a direita em node deve subir e retornar seu filho a esquerda
	 * 
	 * @param node
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T extends Comparable<T>> BSTNode<T> rightRotation(BSTNode<T> node) {
		BSTNode<T> pivot = null;

		if (!node.isEmpty()) {
			pivot = (BSTNode) node.getLeft();
			pivot.setParent(node.getParent());

			if (!pivot.getParent().isEmpty()) {
				if (pivot.getParent().getRight().equals(node)) {
					pivot.getParent().setRight(pivot);
				} else {
					pivot.getParent().setLeft(pivot);
				}
			}

			node.setParent(pivot);
			node.setLeft(pivot.getRight());
			node.getLeft().setParent(node);
			pivot.setRight(node);
		}

		return pivot;
	}


	public static <T extends Comparable<T>> T[] makeArrayOfComparable(int size) {
		@SuppressWarnings("unchecked")
		T[] array = (T[]) new Comparable[size];
		return array;
	}
}