package ule.edi.histogram;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayHistogram<T> implements UnorderedHistogram<T> {

	private static final int NOT_FOUND = -1;
	
	private static final int INITIAL_ARRAY_SIZE = 2;
	
	private T[] contents;
	
	private long[] counters;
	
	//	primera disponible a la derecha
	private int tail;
	
	@SuppressWarnings("unchecked")
	public UnorderedArrayHistogram() {
		this.contents = (T[]) new Object [ INITIAL_ARRAY_SIZE ];
		this.counters = new long [ INITIAL_ARRAY_SIZE ];
		this.tail = 0;
	}
	
	@Override
	public String toString() {
		if (! isEmpty()) {
			StringBuffer rx = new StringBuffer();
			rx.append("[");
			for (int n = 0; n < tail; ++n) {
				rx.append(contents[n]);
				rx.append(" (" + counters[n] + "), ");
			}
			rx.delete(rx.length() - 2, rx.length());
			rx.append("]");
			return rx.toString();
		} else {
			return "[]";
		}
	}
	
	@SuppressWarnings("unchecked")
	private void expandCapacity(){
		T[] larger = (T[]) (new Object[contents.length*2]);
		long[] moreCounter = new long[counters.length*2];
		
		for(int index = 0; index < contents.length; index++){
			larger[index] = contents[index];
			moreCounter[index] = counters[index];
		}
		
		contents = larger;
		counters = moreCounter;
	}
	
	@Override
	public void add(T element, long times) {
		if(times < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			for(int i = 0; i < times; i++){
				add(element);
			}
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void add(T element) {
		
		if(element != null){
			if(!contains(element)){
				
				if(tail == contents.length){
					expandCapacity();
				}
				
				reorderWhenAddFront(0);
				contents[0] = element;
				counters[0] += 1;
				tail++;
			} else {
				for(int i = 0; i < tail; i++){
					if(contents[i].equals(element)){
						counters[i] += 1;
					}
				}
			}
		} else {
			throw new NullPointerException();
		}	
	}
	
	private void reorderWhenAddFront(int n){
		for(int i = tail; i > n; i--){
			contents[i] = contents[i-1];
			counters[i] = counters[i-1];
			counters[i-1] = 0;
		}
	}
	
	/**
	 * 
	 * @param n
	 */
	private void moveWhenRemove(int n){
		for(int i = n; i < tail -1; i++){
			this.contents[i] = this.contents[i+1];
			counters[i] = counters[i+1];
			this.contents[i+1] = null;	// bien??
			counters[i+1] = 0;	// bien??
		}
	}

	@Override
	public T remove(T element) {
		// y comprobacion de que no es empty????
		
		if(element != null) {
			T aux = null;
			for(int i = 0; i < tail; i++){
				if(contents[i].equals(element)){
					aux = contents[i];
					counters[i] = counters[i] - 1;
					if(counters[i] == 0){
						contents[i] = null;
						moveWhenRemove(i);
						tail--;
					}
					
					return aux;	// o hacemos variable aux con contents[i] antes de eliminar?
				}
			}
			// si no encuentra niguno
			return aux;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T remove(T element, long count) {
		// y comprobacion de que no es empty????
		
		if(count == 0){
			return null;
		} else if(count < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			
			for(int i = 0; i < tail; i++){
				if(contents[i].equals(element)){
					if(counters[i] < count){
						count = counters[i];
					}
					T aux = contents[i];
					for(int j = 0; j < count; j++){
						aux = remove(element);
					}
					return aux;
				}
			}
			return null;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T removeElementAll(T element) {
		
		if(element != null) {
			for(int i = 0; i<tail; i++){
				if(element.equals(contents[i])){
					T aux = remove(element, count(contents[i]));
					return aux;
				}
			}

			return null;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T removeAllFirst() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty trying to remove first element.");
		} else { 
			T firstElem = removeElementAll(contents[0]);
			return firstElem;
		}
	}

	@Override
	public T removeAllLast() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty trying to remove last element.");
		} else {
			T lastElem = removeElementAll(contents[tail-1]);
			return lastElem;
		}
	}

	@Override
	public T getFirst() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Impossible to get the first element, empty collection.");
		} else {
			return contents[0];
		}
	}

	@Override
	public T getLast() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Impossible to get the last element, empty collection.");
		} else {
			// tail es el ultimo elemento = tail es el numero de elementos
			return contents[tail-1];
		}
	}

	@Override
	public long size() {
		
		long cont = 0;
		
		for(int i = 0; i < counters.length; i++){
			cont+= counters[i];
		}
		
		return cont;
	}

	@Override
	public boolean contains(T element) {
		if(element != null) {
			int search = NOT_FOUND;
			for(int i = 0; i<tail; i++){
				if(contents[i].equals(element)){
					search = i;
				}
			}
			return (search != NOT_FOUND);
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T getElementAt(long position) {
		if(position < 1 || position > tail){
			throw new IndexOutOfBoundsException();
		} else {
			return contents[(int) position-1];
		}
	}

	@Override
	public T removeAllElementAt(long position) {
		if(position < 1 || position > tail){
			throw new IndexOutOfBoundsException();
		} else {
			return removeElementAll(contents[(int)position-1]);
		}
	}

	@Override
	public boolean isEmpty() {
		// o hacerlo comprobando que no hay null en contents
		if(size() == 0){
			return true;
		}
		return false;
	}

	@Override
	public long count(T element) {
		if(element != null){
			for(int i = 0; i < tail; i++){
				if(contents[i].equals(element)){
					return counters[i];
				}
			}
			return 0;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void clear() {
		for(int i = 0; i<tail; i++){
			contents[i] = null;
			counters[i] = 0;
		}
		tail = 0;	
	}

	@Override
	public Iterator<T> iterator() {
		return new ArrayIterator<T> (contents, counters, tail);
	}
	
	public class ArrayIterator<T> implements Iterator<T>{
		private int count;
		private int current;
		private T[] items;
		private long[] ocurrences;
		private int nItem;
		
		public ArrayIterator (T[] collection, long[] nElements, int size){
			items = collection;
			ocurrences = nElements;
			count = size;
			current = 0;
			nItem = 0;
		}
		
		private int numberTotalInstances(){
			int total = 0;
			for(int i = 0; i < count; i++){
				total += (int) ocurrences[i];
			}
			return total;
		}
		
		@Override
		public boolean hasNext() {
			return ((current < count-1) || nItem != numberTotalInstances());
		}
		@Override
		public T next() throws NoSuchElementException {
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			nItem++;
			int each = 0;
			for(int i = 0; i <= current; i++){
				each += (int) ocurrences[i];
			}
			
			if(nItem <= each){
				return items[current];
			} else {
				current++;
				return items[current];
			}
			
		}
		
		@Override
		public void remove() throws UnsupportedOperationException{
			//	// La operación remove no está soportada en esta colección
			throw new UnsupportedOperationException();
		}
	}

	@Override
	public void addToFront(T element) {

		//if(element.equals(null)){
			//throw new NullPointerException();
		//} else {
			//add(element);
		//}	
		if(element != null){
			add(element);
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void addToFront(T element, long count) {
		if(count < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			add(element, count);
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void addToRear(T element) {
		if(element != null){
			
			if(!contains(element)){
				
				if(tail == contents.length){
					expandCapacity();
				}
				
				tail++;	
				contents[tail-1] = element;
				counters[tail-1] += 1;
				
			} else {
				add(element);
			}
		
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void addToRear(T element, long count) {
		if(count < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			if(!contains(element)){
				for(int i = 0; i < count; i++){
					addToRear(element);
				}
			} else {
				add(element, count);
			}
		} else {
			throw new NullPointerException();
		}
		
	}
}
