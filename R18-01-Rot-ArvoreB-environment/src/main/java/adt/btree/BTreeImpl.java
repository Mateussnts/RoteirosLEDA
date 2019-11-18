package adt.btree;

import java.util.ArrayList;

public class BTreeImpl<T extends Comparable<T>> implements BTree<T> {

	protected BNode<T> root;
	protected int order;
	protected int size;

	public BTreeImpl(int order) {
		this.order = order;
		this.root = new BNode<T>(order);
		this.size = 0;
	}

	@Override
	public BNode<T> getRoot() {
		return this.root;
	}

	@Override
	public boolean isEmpty() {
		return this.root.isEmpty();
	}

	@Override
	public int height() {
		return height(this.root);
	}

	private int height(BNode<T> node) {
		if (node.isEmpty()) {
			return -1;
		} else {
			if (node.getChildren().size() > 0) {
				return 1 + height(node.getChildren().get(0));
			} else {
				return 0;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public BNode<T>[] depthLeftOrder() {
		ArrayList<BNode<T>> array = new ArrayList<>();

		if (!root.isEmpty()) {
			depthLeftOrder(array, root);
		}

		BNode<T>[] retorno = new BNode[array.size()];

		for (int i = 0; i < array.size(); i++) {
			retorno[i] = array.get(i);
		}

		return retorno;
	}

	private void depthLeftOrder(ArrayList<BNode<T>> array, BNode<T> node) {
		array.add(node);

		if (node.isLeaf()) {
			return;
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			depthLeftOrder(array, node.getChildren().get(i));
		}
	}

	@Override
	public int size() {
		return size;
	}

	@Override
	public BNodePosition<T> search(T element) {
		return search(root, element);
	}

	private BNodePosition<T> search(BNode<T> node, T element) {
		int indice = 0;

		while ((indice != node.size()) && (!node.getElementAt(indice).equals(element))) {
			indice++;
		}

		if (node.getElementAt(indice).equals(element)) {
			return new BNodePosition<>(node, indice);
		} else {
			return new BNodePosition<>();
		}
	}

	@Override
	public void insert(T element) {
		if (element == null)
			return;

		if (root.isEmpty()) {
			BNode<T> node = new BNode<>(order);
			node.addElement(element);
			root = node;
		} else {
			insert(root, element);
		}

		this.size++;
	}

	private void insert(BNode<T> node, T element) {
		if (node.isLeaf()) {
			if (node.isFull()) {
				node.addElement(element);
				split(node);
			} else {
				node.addElement(element);
			}

		} else {
			int indice = 0;

			while ((indice != node.size()) && (node.getElementAt(indice).compareTo(element) < 0)) {
				indice++;
			}

			if (indice == node.size()) {
				insert(node.getChildren().get(node.size()), element);
			} else {
				insert(node.getChildren().get(indice), element);
			}
		}
	}

	private void split(BNode<T> node) {
		T middle = middle(node);

		BNode<T> rightNode = createRightNode(node);

		removeElementsInNode(node, middle, rightNode);

		for (int i = node.size() + 1; i < node.getChildren().size(); i++) {
			rightNode.getChildren().add(node.getChildren().get(i));
			node.getChildren().get(i).setParent(rightNode);
		}

		for (int i = 0; i < node.getChildren().size(); i++) {
			node.getChildren().remove(rightNode.getChildren().get(i));
		}

		BNode<T> parent = node.getParent();

		if (parent == null) {
			BNode<T> newRoot = new BNode<>(order);
			root = newRoot;

			root.getChildren().add(node);

			parent = root;
		}

		node.setParent(parent);
		rightNode.setParent(parent);

		if (parent.isFull()) {
			parent.addElement(middle);

			int indexMiddle = parent.getElements().indexOf(middle);
			parent.getChildren().add(indexMiddle + 1, rightNode);

			split(parent);
		} else {
			parent.addElement(middle);

			int indexMiddle = parent.getElements().indexOf(middle);
			parent.getChildren().add(indexMiddle + 1, rightNode);
		}
	}

	private T middle(BNode<T> node) {
		int middle = (node.size() - 1) / 2;

		return node.getElementAt(middle);
	}

	private void removeElementsInNode(BNode<T> node, T middle, BNode<T> rightNode) {
		node.removeElement(middle);

		for (int i = 0; i < rightNode.size(); i++) {
			node.removeElement(rightNode.getElementAt(i));
		}
	}

	private BNode<T> createRightNode(BNode<T> node) {
		BNode<T> rightNode = new BNode<>(order);

		int indexControl = 0;

		if (order % 2 != 0) {
			indexControl = 1;
		}

		for (int i = (node.size() / 2) + indexControl; i < node.size(); i++) {
			rightNode.addElement(node.getElementAt(i));
		}

		return rightNode;
	}

	// NAO PRECISA IMPLEMENTAR OS METODOS ABAIXO
	@Override
	public BNode<T> maximum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public BNode<T> minimum(BNode<T> node) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}

	@Override
	public void remove(T element) {
		// NAO PRECISA IMPLEMENTAR
		throw new UnsupportedOperationException("Not Implemented yet!");
	}
}