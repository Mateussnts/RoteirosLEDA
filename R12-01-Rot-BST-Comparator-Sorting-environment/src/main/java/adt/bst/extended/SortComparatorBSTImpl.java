package adt.bst.extended;

import java.util.Comparator;

import adt.bst.BSTImpl;
import adt.bst.BSTNode;

/**
 * Implementacao de SortComparatorBST, uma BST que usa um comparator interno em suas funcionalidades
 * e possui um metodo de ordenar um array dado como parametro, retornando o resultado do percurso
 * desejado que produz o array ordenado. 
 * 
 * @author Adalberto
 *
 * @param <T>
 */
public class SortComparatorBSTImpl<T extends Comparable<T>> extends BSTImpl<T> implements SortComparatorBST<T> {

	private Comparator<T> comparator;
	
	public SortComparatorBSTImpl(Comparator<T> comparator) {
		super();
		this.comparator = comparator;
	}

	@Override
	public T[] sort(T[] array) {
		
		while(!this.root.isEmpty()) {
			this.remove(this.root.getData());
		}
		for(int i = 0; i < array.length; i++) {
			this.insert(array[i]);
		}
		
		return this.order();
	}

	@SuppressWarnings("unchecked")
	@Override
	public T[] reverseOrder() {
		T[] reverseOrd = (T[]) new Comparable[this.size()];
		if (this.isEmpty())
			return reverseOrd;
		reverseOrder(reverseOrd, root, 0);
		return reverseOrd;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private int reverseOrder(T[] reverseOrd, BSTNode<T> node, int index) {
		if (!node.getRight().isEmpty())
			index = reverseOrder(reverseOrd, (BSTNode) node.getRight(), index);
		
		reverseOrd[index++] = node.getData();
		
		if (!node.getLeft().isEmpty())
			index = reverseOrder(reverseOrd, (BSTNode) node.getLeft(), index);

		return index;
		
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}
	
	public BSTNode<T> search(T element) {
		return this.search(root, element);
	}
	
	private BSTNode<T> search(BSTNode<T> node, T element) {

		if (node.isEmpty() || element == null)
			return new BSTNode<T>();

		if (node.getData().equals(element))
			return node;

		if (node.getLeft() != null && (this.comparator.compare(node.getData(), element) > 0))
			return this.search((BSTNode<T>) node.getLeft(), element);
		else
			return this.search((BSTNode<T>) node.getRight(), element);
	}
	
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
			} else {
				if (this.comparator.compare(node.getData(), element) > 0) {
					this.insert((BSTNode<T>) node.getLeft(), element);
				} else if (this.comparator.compare(node.getData(), element) < 0) {
					this.insert((BSTNode<T>) node.getRight(), element);
				}
			}
		}
	}
}