package adt.skipList;

public class SkipListImpl<T> implements SkipList<T> {

	protected SkipListNode<T> root;
	protected SkipListNode<T> NIL;

	protected int maxHeight;

	protected double PROBABILITY = 0.5;

	public SkipListImpl(int maxHeight) {
		this.maxHeight = maxHeight;
		root = new SkipListNode<T>(Integer.MIN_VALUE, maxHeight, null);
		NIL = new SkipListNode<T>(Integer.MAX_VALUE, maxHeight, null);
		connectRootToNil();
	}

	/**
	 * Faz a ligacao inicial entre os apontadores forward do ROOT e o NIL Caso
	 * esteja-se usando o level do ROOT igual ao maxLevel esse metodo deve
	 * conectar todos os forward. Senao o ROOT eh inicializado com level=1 e o
	 * metodo deve conectar apenas o forward[0].
	 */
	private void connectRootToNil() {
		for (int i = 0; i < maxHeight; i++) {
			root.forward[i] = NIL;
		}
	}

	
	@SuppressWarnings({"unchecked" })
	@Override
	public void insert(int key, T newValue, int height) {
		SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
		SkipListNode<T> aux = this.root;
		
		for (int i = this.maxHeight-1; i >= 0; i--) {
			while (aux.getForward(i).getKey() < key) {
				aux = aux.getForward(i);
			}
			update[i] = aux;
		}
		
		aux = aux.getForward(0);
		if(aux.key == key) {
			aux.setValue(newValue);
		}
		else {
			aux = new SkipListNode<T>(key, height, newValue);
			for (int i = 0; i < height; i++) {
				aux.forward[i] = update[i].forward[i];
				update[i].forward[i] = aux;
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void remove(int key) {
		SkipListNode<T>[] update = new SkipListNode[this.maxHeight];
		SkipListNode<T> aux = this.root;

		for (int i = maxHeight - 1; i >= 0; i--) {
			if (aux.forward[i] != this.NIL) {
				while (aux.forward[i].value != null && aux.forward[i].key < key)
					aux = aux.forward[i];
			}
			update[i] = aux;
		}
		aux = aux.getForward()[0];

		if (aux.key == key) {

			for (int i = 0; i < maxHeight; i++) {
				if (update[i].getForward()[i] != aux) {
					break;
				}
				update[i].getForward()[i] = aux.getForward()[i];
			}
		}
	}

	@Override
	public int height() {
		SkipListNode<T>[] forward = root.getForward();
		return forward.length - 1;
	}

	@Override
	public SkipListNode<T> search(int key) {
		SkipListNode<T> resultado = null;
		SkipListNode<T> aux = this.root;

		for (int i = this.maxHeight - 1; i >= 0; i--) {
			while (aux.getForward(i).getKey() < key) {
				aux = aux.getForward(i);
			}
		}
		aux = aux.getForward(0);
		if (aux.getKey() == key) {
			resultado = aux;
		}

		return resultado;
	}

	@Override
	public int size() {
		int size = 0;
		SkipListNode<T> aux = this.root.getForward(0);
		while (aux != NIL) {
			size++;
			aux = aux.getForward(0);
		}

		return size;
	}

	@SuppressWarnings("unchecked")
	@Override
	public SkipListNode<T>[] toArray() {
		SkipListNode<T>[] array = new SkipListNode[size() + 2];
		SkipListNode<T> aux = this.root;

		int index = 0;
		while (aux != null) {
			array[index++] = aux;
			aux = aux.getForward(0);
		}
		return array;
	}
}