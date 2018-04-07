package ule.edi.histogram;

import java.util.Iterator;
import java.util.NoSuchElementException;

import ule.edi.EmptyCollectionException;

public class UnorderedSingleLinkedHistogram<T> implements UnorderedHistogram<T> {

	protected static class Node<D> {

		Node() {
			this.content = null;
			this.count = 0;
			this.next = null;
		}

		Node(D content) {
			this.content = content;
			this.count = 1;
			this.next = null;
		}

		Node(D content, long count) {
			this.content = content;
			this.count = count;
			this.next = null;
		}

		D content;

		long count;

		Node<D> next;
	}
	
	// este es el primer nodo de la lista
	protected Node<T> header = null;

	public UnorderedSingleLinkedHistogram() {

	}
	
	@Override
	public String toString() {
		if (header!= null) {
			StringBuffer rx = new StringBuffer();
			rx.append("[");
			Node<T> n = header;
			while (n != null) {
				rx.append(n.content);
				rx.append(" (" + n.count + "), ");
				n = n.next;
			}
			rx.delete(rx.length() - 2, rx.length());
			rx.append("]");
			return rx.toString();
		} else {
			return "[]";
		}
	}

	@Override
	public void add(T element, long times) {
		if(times < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			if(isEmpty() && times >= 1){
				header = new Node<T>(element, times);
			} else {
				for(int i = 0; i < times; i++){
					add(element);
				}
			}
			
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void add(T element) {
		if(element != null){
			if(isEmpty()){
				header = new Node<T>(element);
			} else if(!contains(element)){
				Node<T> aux = new Node<T>(element);
				aux.next = header;
				header = aux;
			} else {
				
				if(header.content.equals(element)){
					header.count++;
				} else {
					Node<T> aux = header;
					Node<T> siguiente = aux.next;
					while(!siguiente.content.equals(element)){
						aux = aux.next;
						siguiente = aux.next;
					}
					siguiente.count++;
				}
			}
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T remove(T element) {
		if(element != null){
			Node<T> devolver = new Node<T>();
			if(!isEmpty() && contains(element)){
				if(header.content.equals(element)){
					header.count--;
					devolver = header;
					if(header.count == 0){
						header = header.next;
					}
				} else { 
					Node<T> aux = header;
					Node<T> siguiente = aux.next;
					while(!siguiente.content.equals(element)){
						aux = aux.next;
						siguiente = aux.next;
					}
					siguiente.count--;
					devolver = siguiente;
					if(siguiente.count == 0){
						siguiente = siguiente.next;
						aux.next = siguiente;
					}
				}
			}
			return devolver.content;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T remove(T element, long count) {
		if(count == 0){
			return null;
		} else if(count < 0){
			throw new IllegalArgumentException();
		} else if(element != null){
			T devolver = null;
			if(!isEmpty() && contains(element)){
				if(header.content == element){
					if(count > header.count){
						count = header.count;
					}
				} else {
					Node<T> aux = header;
					while(!aux.next.content.equals(element)){
						aux = aux.next;
					}
					if(count > aux.next.count){
						count = aux.next.count;
					}
				}
				for( int i = 0; i < count; i++){
					devolver = remove(element);
				}
			}
			return devolver;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T removeElementAll(T element) {
		if(element != null) {
			T devolver = remove(element, count(element));
			return devolver;
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public T removeAllFirst() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty Single Linked List.");
		} else { 
			T devolver = removeElementAll(getFirst());
			return devolver;
		}
	}

	@Override
	public T removeAllLast() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty Single Linked List.");
		} else { 
			T devolver = removeElementAll(getLast());
			return devolver;
		}
	}

	@Override
	public T getFirst() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty Single Linked List");
		} else {
			return header.content;
		}
	}

	@Override
	public T getLast() throws EmptyCollectionException {
		if(isEmpty()){
			throw new EmptyCollectionException("Empty Single Linked List");
		} else {
			Node<T> last = header;
			while(last.next != null){
				last = last.next;
			}
			return last.content;
		}
	}
	
	// precondicion: la lista no estará vacía
	private Node<T> getLastNode() {
		Node<T> last = header;
		while(last.next != null){
			last = last.next;
		}
		return last;
	}

	@Override
	public long size() {
		long cont = 0;
		Node<T> last = header;
		while(last != null){
			cont+= last.count;
			last = last.next;
		}
		return cont;
	}

	@Override
	public boolean contains(T element) {
		if(element != null){
			Node<T> aux = header;
			while(aux != null){
				if(aux.content.equals(element)){
					return true;
				}
				aux = aux.next;
			}
			return false;
		} else {
			throw new NullPointerException();
		}	
	}
	
	private int numberOfElements(){
		int cont = 0;
		Node<T> aux = header;
		while(aux != null){
			cont++;
			aux = aux.next;
		}
		return cont;
	}

	@Override
	public T getElementAt(long position) {
		if(position < 1 || position > numberOfElements()){
			throw new IndexOutOfBoundsException();
		} else {
			Node<T> aux = header;
			for(int node = 1; node < (int) position; node++){
				aux = aux.next;
			}
			return aux.content;
		}
	}

	@Override
	public T removeAllElementAt(long position) {
		if(position < 1 || position > numberOfElements()){
			throw new IndexOutOfBoundsException();
		} else {
			return removeElementAll(getElementAt(position));
		}
	}

	@Override
	public boolean isEmpty() {
		// o probando que header es null o mirando size()
		// if(size() != 0){
		if(header == null){
			return true;
		}
		return false;
	}

	@Override
	public long count(T element) {
		if(element != null){
			
			if(header.content.equals(element)){
				return header.count;
			} else if(contains(element)){
				Node<T> aux = header;
				Node<T> siguiente = aux.next;
				while(!siguiente.content.equals(element)){
					aux = siguiente; // o aux.next
					siguiente = aux.next;
				}
				return siguiente.count;
			} else {
				return 0;
			}
			
		} else {
			throw new NullPointerException();
		}
	}

	@Override
	public void clear() {
		Node<T> aux = header;
		while(aux != null){
			aux = aux.next;
		}
		header = null;
		
	}

	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator<T>(this.header);
	}
	
	public class LinkedListIterator<T> implements Iterator<T> {
		
		private Node<T> current;
		private int nItem;
		private long acumulador;
		
		public LinkedListIterator(Node<T> header){
			this.current = header;
			nItem = 0;
			acumulador = 0;
		}
		@Override
		public boolean hasNext() {
			//return this.current != null;
			return this.current != null && nItem < size();
		}

		@Override
		public T next() {
			if(!hasNext()){
				throw new NoSuchElementException();
			} else {
				nItem++;
				T aux = null;
				if(acumulador == 0){
					acumulador += current.count;
				}
				
				if(nItem>acumulador){
					current = current.next;
					aux = current.content;
					acumulador+=current.count;
				} else{
					aux = current.content;
				}
				
				return aux;
			}
		}
		
		@Override
		public void remove() throws UnsupportedOperationException{
			// La operación remove no está soportada en esta colección
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public void addToFront(T element) {
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
			if(isEmpty()){
				header = new Node<T>(element);
			} else {
				if(!contains(element)){
			
					Node<T> aux = new Node<T>(element);
					Node<T> last = null;
					
					last = getLastNode();
					
					last.next = aux;
				} else {
					add(element);
				}
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
			for(int i = 0; i < count; i++){
				addToRear(element);
			}
		} else {
			throw new NullPointerException();
		}
	}
}
