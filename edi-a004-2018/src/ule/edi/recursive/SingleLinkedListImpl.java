package ule.edi.recursive;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class SingleLinkedListImpl<T> extends AbstractSingleLinkedListImpl<T> {

	@Override
	public void loadIntoStacks(Stack<T> forwards, Stack<T> backwards) {
		// implementar de forma recursiva, sin usar otras listas/pilas
		if(!isEmpty()){
			loadIntoStacksRec(forwards, backwards, header);
		}
	}
	
	private void loadIntoStacksRec(Stack<T> forwards, Stack<T> backwards, Node<T> actual){
		if(actual != null){
			forwards.push(actual.content);
			loadIntoStacksRec(forwards, backwards, actual.next);
			backwards.push(actual.content);
		}
	}
	
	/**
	 * Indica si los contenidos de ambas pilas son iguales.
	 * 
	 * Tras la llamada al método, las dos pilas deben quedar
	 * como estaban.
	 * 
	 * @param a
	 * @param b
	 * @return
	 */
	static <T> boolean sameContents(Stack<T> a, Stack<T> b) {
		// implementar de forma recursiva; ambas pilas no deben resultar modificadas
		if(a.isEmpty() && b.isEmpty()){
			return true;
		} else if(a.isEmpty() != b.isEmpty()){
			return false;
		} else {
			boolean res = false;
			T ele1 = a.pop();
			T ele2 = b.pop();
			if(ele1.equals(ele2)){
				if(!a.isEmpty() && !b.isEmpty()){
					res = sameContents(a, b);
				} else if(a.isEmpty() != b.isEmpty()){
					res = false;
				} else {
					res = true;
				}
			}
			a.push(ele1);
			b.push(ele2);
			return res;
		}
	}
	
	@Override
	public boolean palindrome() {
		// implementar de forma recursiva, con la ayuda de dos pilas
		if(isEmpty()){
			return true;
		} else {
			Stack<T> a = new StackImpl<T>(), b = new StackImpl<T>();
			return palindromeRec(a, b, header);
		}
	}
	
	private boolean palindromeRec(Stack<T> a, Stack<T> b, Node<T> actual) {
		loadIntoStacksRec(a, b, actual);
		return sameContents(a, b);
	}

	@Override
	public long maxPrefix(AbstractSingleLinkedListImpl<T> part) {
		// implementar de forma recursiva, sin usar otras listas/pilas
		if(isEmpty()){
			return 0L;
		} else {
			return maxPrefixRec(part, header, part.header, 0L, header);
		}
	}
	
	private long maxPrefixRec(AbstractSingleLinkedListImpl<T> part, Node<T> actual, Node<T> actualPart, long acumulado, Node<T> prevBest){
		
		boolean iguales = (actual.content == actualPart.content);

		if(iguales){
				
			if(actual.next != null){
				if(actualPart.next != null){
					return maxPrefixRec(part, actual.next, actualPart.next, acumulado+1, prevBest);
				} else {
					return acumulado+1;
				}
			} else {
				return acumulado+1;
			}			
		
		} else {
			
			if(actual.next != null){
				return maxPrefixRec(part, prevBest.next, part.header, 0L, prevBest.next);
			} else {
				return acumulado;
			}
		}
	}
	
	@Override
	public long positionOfLast(T element) {
		// implementar de forma recursiva, sin usar otras listas/pilas
		if(isEmpty()){
			return 0L;
		} else {
			Node <T> aux = header;
			return positionOfLastRec(aux, element, 1L, 0L);
		}
	}
	
	private long positionOfLastRec(Node <T> actual, T element, long index, long position){
		
		if(actual == null){
			return position;	// index 0 o cuando llega al final
		} else {
			if(actual.content == element){
				return positionOfLastRec(actual.next, element, index+1, index);
			} else {
				return positionOfLastRec(actual.next, element, index+1, position);
			}
		}
	}
	
	public SingleLinkedListImpl<T> removeConsecutiveDuplicates() {
		// implementar de forma recursiva, sin usar otras listas/pilas
		
		//	(para los tests)
		//
		//	por ejemplo, lS.removeConsecutiveDuplicates().toString()
		//
		if(isEmpty()){
			return this;
		} else {
			return removeConsecutiveDuplRec(header);
		}
		
	}
	
	private SingleLinkedListImpl<T> removeConsecutiveDuplRec(Node<T> actual){
		if(actual.next == null){
			return this;	
		} else {
			if(actual.content == actual.next.content){
				actual.next = actual.next.next;
				return removeConsecutiveDuplRec(actual);
			}
			return removeConsecutiveDuplRec(actual.next);
		}
	}

	@Override
	public boolean subsequence(AbstractSingleLinkedListImpl<T> a) {
		//	implementar de forma recursiva, sin usar otras listas/pilas
		if(a.header == null){
			return true;
		} else if(isEmpty()){
			return false;
		} else {
			return subsequenceRec(a, header, a.header); 
		}
	}
	
	private boolean subsequenceRec(AbstractSingleLinkedListImpl<T> a, Node<T> actual, Node<T> actualAux){
		
		boolean result = (actual.content == actualAux.content);
		
		if(result){
			if(actualAux.next != null && actual.next != null){
				return subsequenceRec(a, actual.next, actualAux.next);
			} else if(actualAux.next != null && actual.next == null){
				return false;
			} else { //if(actualAux.next == null){
				return result;
			}
		} else {
			if(actual.next != null){
				return subsequenceRec(a, actual.next, actualAux);
			} else {
				return result;
			}
		}
	}

	@SuppressWarnings("unchecked")
	public SingleLinkedListImpl(T ... elements) {
		// implementar	 de forma recursiva, sin usar otras listas/pilas
		for(T elem:elements){
			addToRear(elem);
		}
	}
	
	public SingleLinkedListImpl(Iterable<T> contents) {
		// implementar de forma recursiva, sin usar otras listas/pilas
		// ESTA BIEN????
		for(T cont:contents){
			addToRear(cont);
		}
	}
	
	private boolean isEmpty(){
		if(header == null){
			return true;
		}
		return false;
	}
	
	@Override
	public void addToRear(T element) {
		if(this.isEmpty()){
			header = new Node<T>(element); 
		} else {
			findRearRec(header).next = new Node<T>(element);
		}
	}
	
	private Node<T> findRearRec(Node<T> siguiente){
		if(siguiente.next == null){
			return siguiente;
		} else {
			return findRearRec(siguiente.next);
		}
	}
	
	@Override
	public Iterator<T> progIterator() {
		// Implementar
		return new SingleLinkedListProgIterator<T>(this.header);
	}
	
	public class SingleLinkedListProgIterator<T> implements Iterator<T>{
		
		private Node<T> current;
		private int nOperacion;
		
		public SingleLinkedListProgIterator(Node<T> header){
			this.current = header;
			this.nOperacion = 0;
		}
		
		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T result = current.content;
			nOperacion++;
			current = moverse(nOperacion, current);
			return result;
		}
		
		private Node<T> moverse(int n, Node<T> actual){
			for(int i = 0; i < n && (actual != null); i++){
				actual = actual.next;
			}
			return actual;
		}
		
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
		
	}
	
	public T max(Comparator<T> cmpf) {
		// implementar de forma recursiva, sin usar otras listas/pilas
		if(isEmpty()){
			throw new NoSuchElementException();
		} else {
			return maxRec(cmpf, header, header.next);
		}
	}
	
	private T maxRec(Comparator<T> cmpf, Node<T> uno, Node<T> dos){
		if(dos == null){
			return uno.content;
		} else {
			if(cmpf.compare(uno.content, dos.content) > 0){
				return maxRec(cmpf, uno, dos.next);
			} else {
				return maxRec(cmpf, dos, dos.next);
			}
		}
	}

	@Override
	public Iterator<T> iterator() {
		return new SingleLinkedListIterator<T>(this.header);
	}
	
	public class SingleLinkedListIterator<T> implements Iterator<T>{
		
		private Node<T> current;
		
		public SingleLinkedListIterator(Node<T> header){
			this.current = header;
		}
		
		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			}
			T result = current.content;
			current = current.next;
			return result;
		}
		
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
		
	}
	
}
