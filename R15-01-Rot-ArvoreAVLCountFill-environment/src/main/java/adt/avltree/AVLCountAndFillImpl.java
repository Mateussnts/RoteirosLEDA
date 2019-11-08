package adt.avltree;

import java.util.Arrays;

import adt.bst.BSTNode;
import adt.bt.Util;

public class AVLCountAndFillImpl<T extends Comparable<T>> extends
		AVLTreeImpl<T> implements AVLCountAndFill<T> {

	private int LLcounter;
	private int LRcounter;
	private int RRcounter;
	private int RLcounter;

	public AVLCountAndFillImpl() {
		
	}

	@Override
	public int LLcount() {
		return LLcounter;
	}

	@Override
	public int LRcount() {
		return LRcounter;
	}

	@Override
	public int RRcount() {
		return RRcounter;
	}

	@Override
	public int RLcount() {
		return RLcounter;
	}

	@Override
	protected void rebalance(BSTNode<T> node) {
		if (node != null && !node.isEmpty()) {
			boolean isRoot = false;
			if (node.getParent().isEmpty()) {
				isRoot = true;
			}
			int balance = this.calculateBalance(node);

			if (balance > 1) {
				boolean LROcurrence = false;
				if (super.height((BSTNode<T>) node.getLeft().getRight()) >
					super.height((BSTNode<T>) node.getLeft().getLeft())) {
					node.setLeft(Util.leftRotation((BSTNode<T>) node.getLeft()));
					this.LRcounter++;
					LROcurrence = true;
				}
				if (isRoot) {
					this.root = Util.rightRotation(node);
					if (!LROcurrence) {
						this.LLcounter++;
					}
				} else {
					if (super.isChildPerLeft(node)) {
						node.getParent().setLeft(Util.rightRotation(node));
					} else {
						node.getParent().setRight(Util.rightRotation(node));
					}
					if (!LROcurrence) {
						this.LLcounter++;
					}
				}

			} else if (balance < -1) {
				boolean RLOcurrence = false;
				if (super.height((BSTNode<T>) node.getRight().getLeft()) > 
					super.height((BSTNode<T>) node.getRight().getRight())) {
					node.setRight(Util.rightRotation((BSTNode<T>) node.getRight()));
					this.RLcounter++;
					RLOcurrence = true;
				}
				if (isRoot) {
					this.root = Util.leftRotation(node);
					if (!RLOcurrence) {
						this.RRcounter++;
					}
				} else {
					if (super.isChildPerLeft(node)) {
						node.getParent().setLeft(Util.leftRotation(node));
					} else {
						node.getParent().setRight(Util.leftRotation(node));
					}
					if (!RLOcurrence) {
						this.RRcounter++;
					}
				}

			}
		}
	}
	
	@Override
	public void fillWithoutRebalance(T[] array) {
		if (array != null && array.length >= 1) {
			Arrays.parallelSort(array);
			mergeLikeInsertion(array, 0, array.length - 1);
		}
	}
	
	private void mergeLikeInsertion(T[] array, int leftIndex, int rightIndex) {
		if (leftIndex <= rightIndex) {

			int middle = (rightIndex + leftIndex) / 2;
			super.insert(array[middle]);

			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					mergeLikeInsertion(array, leftIndex, middle-1);
				}
			});

			Thread thread_2 = new Thread(new Runnable() {
				@Override
				public void run() {
					mergeLikeInsertion(array, middle + 1, rightIndex);
				}
			});

			thread.start();
			thread_2.start();
		}
	}
}