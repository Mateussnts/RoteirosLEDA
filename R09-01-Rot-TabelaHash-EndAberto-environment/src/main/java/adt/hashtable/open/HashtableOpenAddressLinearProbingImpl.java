package adt.hashtable.open;

import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionLinearProbing;

public class HashtableOpenAddressLinearProbingImpl<T extends Storable> extends
		AbstractHashtableOpenAddress<T> {

	public HashtableOpenAddressLinearProbingImpl(int size,
			HashFunctionClosedAddressMethod method) {
		super(size);
		hashFunction = new HashFunctionLinearProbing<T>(size, method);
		this.initiateInternalTable(size);
	}

	@Override
	public void insert(T element) {
		if(this.isFull())
			throw new HashtableOverflowException();
	
		if(element != null) {
			if(this.search(element) != null)
				return;
			
			int valor = 0;
			int hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);
			while (valor < table.length) {
 				if (table[hashIndex] == null || deletedElement.equals(table[hashIndex])) {
                	table[hashIndex] = element;
                 	super.elements++;
                 	return;
              	} 
 				else {
                 	valor++;
                 	hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);
                 	super.COLLISIONS++;
              	}
			}
		}
	}

	@Override
	public void remove(T element) {
		if(this.isEmpty())
			throw new HashtableOverflowException();

		int hashIndex = indexOf(element);
		if (hashIndex >= 0) {
			table[hashIndex] = new DELETED();
			super.elements--;
		}
	}

	@Override
	public T search(T element) {
		if (element != null) {
			int valor = 0;
			int hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);

			while (valor < table.length) {
				if (table[hashIndex] != null && table[hashIndex].equals(element)) {
					return element;
				} 
				else {
					valor++;
					hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);
				}
			}
		}
		return null;
	}

	@Override
	public int indexOf(T element) {
		if (this.search(element) != null) {
			int valor = 0;
			int hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);
			
			while (valor < table.length) {
				if (table[hashIndex].equals(element)) {
					return hashIndex;
				} 
				else {
					valor++;
					hashIndex = ((HashFunctionLinearProbing<T>) hashFunction).hash(element, valor);
				}
			}
		}
		return -1;
	}
}