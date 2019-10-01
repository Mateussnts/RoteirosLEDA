package adt.hashtable.closed;

import java.util.LinkedList;
import java.util.List;

import adt.hashtable.hashfunction.HashFunction;
import adt.hashtable.hashfunction.HashFunctionClosedAddress;
import adt.hashtable.hashfunction.HashFunctionClosedAddressMethod;
import adt.hashtable.hashfunction.HashFunctionFactory;
import util.Util;

public class HashtableClosedAddressImpl<T> extends AbstractHashtableClosedAddress<T> {

	/**
	 * A hash table with closed address works with a hash function with closed
	 * address. Such a function can follow one of these methods: DIVISION or
	 * MULTIPLICATION. In the DIVISION method, it is useful to change the size
	 * of the table to an integer that is prime. This can be achieved by
	 * producing such a prime number that is bigger and close to the desired
	 * size.
	 * 
	 * For doing that, you have auxiliary methods: Util.isPrime and
	 * getPrimeAbove as documented bellow.
	 * 
	 * The length of the internal table must be the immediate prime number
	 * greater than the given size. For example, if size=10 then the length must
	 * be 11. If size=20, the length must be 23. You must implement this idea in
	 * the auxiliary method getPrimeAbove(int size) and use it.
	 * 
	 * @param desiredSize
	 * @param method
	 */

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public HashtableClosedAddressImpl(int desiredSize, HashFunctionClosedAddressMethod method) {
		int realSize = desiredSize;

		if (method == HashFunctionClosedAddressMethod.DIVISION) {
			realSize = this.getPrimeAbove(desiredSize); // real size must the
			// the immediate prime
			// above
		}
		initiateInternalTable(realSize);
		HashFunction function = HashFunctionFactory.createHashFunction(method,
				realSize);
		this.hashFunction = function;
	}

	public static void main(String[] args) {
		HashtableClosedAddressImpl<Integer> hash = new HashtableClosedAddressImpl<>(10,
				HashFunctionClosedAddressMethod.DIVISION);
		System.out.println(hash.getPrimeAbove(20));
	}

	// AUXILIARY
	/**
	 * It returns the prime number that is closest (and greater) to the given
	 * number. You can use the method Util.isPrime to check if a number is
	 * prime.
	 */
	int getPrimeAbove(int number) {
		while (!Util.isPrime(++number));
		return number;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void insert(T element) {
		if (element != null) {
			int index = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			if (table[index] == null) {
				List linkedList = new LinkedList<T>();
				linkedList.add(element);
				table[index] = linkedList;
			} 
			else {
				((List) table[index]).add(element);
				COLLISIONS++;
			}
			elements++;
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void remove(T element) {
		if (element != null) {
			int index = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			if (table[index] != null) {
				List linkedList = (List) table[index];
				if (linkedList.remove(element)) {
					elements--;
					if (!linkedList.isEmpty()) {
						COLLISIONS--;
					}
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public T search(T element) {
		T resultado = null;
		if (element != null) {
			int index = ((HashFunctionClosedAddress<T>) hashFunction).hash(element); 
			if (table[index] != null) {
				List linkedList = (List) table[index];
				resultado = (T) linkedList.get(linkedList.indexOf(element));
			}
		}
		return resultado;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public int indexOf(T element) {
		int resultado = -1;
		if (element != null) {
			int index = ((HashFunctionClosedAddress<T>) hashFunction).hash(element);
			if (table[index] != null) {
				if (((List)table[index]).contains(element)) {
					resultado = index;
				}
			}
		}
		return resultado;
	}
}