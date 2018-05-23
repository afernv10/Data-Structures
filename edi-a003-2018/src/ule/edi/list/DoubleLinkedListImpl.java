package ule.edi.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoubleLinkedListImpl<T> implements DoubleLinkedList<T> {
	/**
	 * Nodo doblemente enlazado.
	 * 
	 * Como es estática, no tiene en ámbito el parámetro 'T' de la
	 * clase que la contiene. El parámetro 'G' será sustituído por
	 * un tipo particular cuando se use el nodo, por ejemplo:
	 * 
	 * 		Node<T> header;
	 * 
	 * en la lista.
	 *
	 * @param <G>
	 */
	static class Node<G> {

		Node(G element) {
			this.content = element;
			this.previous = null;
			this.next = null;
		}
		
		//	dato a almacenar en el nodo
		G content;
		
		Node<G> previous;
		
		Node<G> next;
	}

	/**
	 * Apunta al primer nodo de la lista; null si vacía.
	 */
	private Node<T> header;
	
	/**
	 * Apunta al último nodo de la lista; null si vacía.
	 */
	private Node<T> tail;
	
	/**
	 * Construye una lista vacía.
	 */
	public DoubleLinkedListImpl() {
		header = tail = null;
		
	}
	
	/**
	 * Construye una lista con los elementos dados.
	 * 
	 * Java creará un array 'elements' con los dados en la
	 * llamada al constructor; por ejemplo:
	 * 
	 * 	x = new DoubleLinkedList<String>("A", "B", "C");
	 * 
	 * ejecuta este método con un array [A, B, C] en 
	 * 'elements'.
	 * 
	 * @param elements
	 */
	@SafeVarargs
	public DoubleLinkedListImpl(T ... elements) {
		for(T elem:elements){
			addToRear(elem);
		}
	}
	
	/**
	 * Construye una lista a partir de otra.
	 * 
	 * Las listas tienen nodos independientes, con los mismos
	 * contenidos.
	 */
	public DoubleLinkedListImpl(DoubleLinkedListImpl<T> other) {
		Node<T> auxOther = other.header;
		
		while(auxOther != null){
			addToRear(auxOther.content);
			auxOther = auxOther.next;
		}
		
	}
	
	/**
	 * Construye una lista dado el primer nodo. Los nodos
	 * deben estar correctamente enlazados, y pasan a
	 * formar parte de la lista.
	 * 
	 * Es decir, basta con que header y tail apunten
	 * correctamente a los nodos dados.
	 */
	private DoubleLinkedListImpl(Node<T> contents) {
		header = contents;
		header.previous = null;
		
		Node<T> aux = header;
		while(aux.next != null){
			aux = aux.next;
		}
		tail = aux;
	}
	
	
	
	@Override
	public void addToFront(T element) {
		Node<T> aux = new Node<T>(element);
		if(header == null){
			tail = aux;
		} else {
			aux.next = header;
			header.previous = aux;
		}
		header = aux;
	}

	@Override
	public void addToRear(T element) {
		Node<T> aux = new Node<T>(element);
		if(header == null){
			header = aux;
		} else {
			aux.previous = tail;
			tail.next = aux;
		}
		tail = aux;
	}

	@Override
	public T removeFirst() {
		if(header==null){
			throw new NoSuchElementException();
		} else {
			T elem = header.content;
			if(tail.content.equals(elem)){
				tail = header = null;
			} else {
				header = header.next;
				header.previous = null;
			}
			return elem;
		}
	}

	@Override
	public T removeLast() {
		if (header==null){
			throw new NoSuchElementException();
		} else {
			T elem = tail.content;
			if (tail==header){
				tail = header = null;
			} else {
				tail = tail.previous;
				tail.next = null;
			}
			return elem;
		}
	}
	
	@Override
	public String toString() {
		
		if (header != null) {
			StringBuffer rx = new StringBuffer();
			rx.append("[");
			Node<T> i = header;
			while (i != null) {
				rx.append(i.content);
				rx.append(", ");
				i = i.next;
			}
			rx.delete(rx.length() - 2, rx.length());
			rx.append("]");
			
			return rx.toString();
		} else {
			return "[]";
		}
	}
	
    @Override
	public Iterator<T> iterator() {
		
		return new DoubleLinkedListIterator<T>(this.header);
	}
    
    public class DoubleLinkedListIterator<T> implements Iterator<T> {
    	
    	private Node<T> current;
    	
    	public DoubleLinkedListIterator(Node<T> header){
			current = header;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if (!hasNext()){
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

	@Override
	public Iterator<T> reverseIterator() {
		return new DoubleLinkedListIteratorReverse<T>(this.tail);
	}
	
	public class DoubleLinkedListIteratorReverse<T> implements Iterator<T> {
    	
    	private Node<T> current;
    	
    	public DoubleLinkedListIteratorReverse(Node<T> tail){
			current = tail;
		}

		@Override
		public boolean hasNext() {
			return this.current != null;
		}

		@Override
		public T next() {
			if (!hasNext()){
				throw new NoSuchElementException();
			}
			T result = current.content;
			current = current.previous;
			return result;
		}
		
		@Override
		public void remove() throws UnsupportedOperationException {
			throw new UnsupportedOperationException();
		}
    }
	
	/**
	 * Elimina los n primeros elementos (últimos si n es negativo)
	 * 
	 * Modifica esta lista, al eliminar los n primeros elementos
	 * (para n positivo) o los |n| últimos elementos (para n
	 * negativo). Si |n| es mayor que el número de elementos de esta
	 * lista, quedará vacía.
	 * 
	 * Ejemplos:
	 * 	[A, B, C].drop(+2) la convierte en [C]
	 * 	[A, B, C].drop(-2) en [A]
	 * 
	 * @param n
	 */		
	public void drop(long n) {
		
		if(n>0){
			Node<T> aux = header;
			while(aux != null && n != 0){
				aux = aux.next;
				removeFirst();
				n--;
			}
		} else if(n<0){
			Node<T> aux = tail;
			while(aux != null && n != 0){
				aux = aux.previous;
				removeLast();
				n++;
			}
		}
	}
	
	
	/**
	 * Saca los n primeros (n últimos si negativo) a otra lista
	 * 
	 * Modifica esta lista, al sacar los n primeros elementos
	 * (para n positivo) o los |n| últimos elementos (para n
	 * negativo) a una nueva lista resultado. Si |n| es mayor
	 * que el número de elementos de esta lista, quedará vacía,
	 * y en el resultado estarán todos ellos.
	 * 
	 * Ejemplos:
	 * 	[A, B, C].dropToList(2) la convierte en [C] y retorna
	 * una lista [A, B]
	 * 
	 * Si la lista es vacía, devolverá una lista vacía.
	 * @param n
	 */
	public DoubleLinkedListImpl<T> dropToList(long n) {
		Node<T> aux;
		if(header == null){
			return new DoubleLinkedListImpl<T>();
		} else {
			if(n>0){
				aux = header;
				DoubleLinkedListImpl<T> positiva = new DoubleLinkedListImpl<T>();
				for(int i = 0; i < n; i++){
					if(aux == null){
						return new DoubleLinkedListImpl<T>(positiva.header);
					}
					positiva.addToRear(removeFirst());
					aux = aux.next;
				}
				return new DoubleLinkedListImpl<T>(positiva);
			} else if(n<0){
				aux = tail;
				DoubleLinkedListImpl<T> negativa = new DoubleLinkedListImpl<T>();
				for(int i = 0; i < -n; i++){
					if(aux == null){
						return new DoubleLinkedListImpl<T>(negativa.header);
					}
					negativa.addToFront(removeLast());
					aux = aux.previous;
				}
				return new DoubleLinkedListImpl<T>(negativa);
			}
			// se le pasa un 0
			return new DoubleLinkedListImpl<T>();
		}	
	}
	
	/**
	 * Indica la longitud del mayor prefijo de part en esta lista.
	 * 
	 * Ejemplos:
	 * 
	 *  [A, B, A, B, C], con part=[B, C, X], contiene el prefijo de longitud 2 
	 *  (el prefijo [B, C]), pero no el de longitud 3.
	 *
	 *  [A, B, A, B], con part=[B, C, X], contiene el prefijo de longitud 1 
	 *  (el prefijo [B]).
	 *  
	 *  [A, B, A, B, C, X, A], con part=[B, C, X], contiene el prefijo de longitud 3 
	 *  (el prefijo [B, C, X]).
	 *  
	 *  [A, D, J], con part=[B, C, X], contiene el prefijo de longitud 0 
	 *  (el prefijo []).
	 *  
	 * @param part (nunca es vacía)
	 * @return
	 */
	public int maxPrefix(DoubleLinkedListImpl<T> part) {
		
		Node<T> aux = header;
		Node<T> auxPart;
		int cont = 0, result = 0;
		
		while(aux != null){
			Node<T> guia = aux;
			auxPart = part.header;
			
			while(auxPart != null && aux != null && auxPart.content == aux.content){
				
				cont++;
				auxPart = auxPart.next;
				aux = aux.next;
			}
			
			aux = guia.next;
			if(cont>result){
				result = cont;
			}
			cont = 0;
		}
		
		return result;
		
	}
	
	
	/**
	 * Inserta un nodo con el separador entre cada par de nodos. (Inserta un nodo detrás de cada nodo, excepto del último)
	 * 
	 * Si la lista es vacía, no hace nada 
	 * 
	 * @param separator
	 */
	public void breakAllPairs(T separator) {
		if(header != null){
			Node<T> aux = header;
			while(aux.next != null){
				Node<T> sep = new Node<T>(separator);
				sep.next = aux.next;
				sep.previous = aux;
				aux.next.previous = sep;
				aux.next = sep;
				// siguiente nodo no nuevo
				aux = sep.next;
			}
		}
	}
	
	/**
	 * Intercambia mitades en esta lista.
	 * 
	 * Si las mitades son de igual longitud (lista de longitud par)
	 * las intercambia; si no, no hace nada.
	 * 
	 * Por ejemplo, [A, B, C, D] pasa a [C, D, A, B]. 
	 * [A, B, C] no se modifica.
	 * 
	 */
	public void flipHalves() {
		// implementar sin contar el número de nodos (solamente se puede recorrer la lista 1 vez)
		Node<T> auxPrincipio = header;
		Node<T> auxFinal = tail;
		while(auxPrincipio != auxFinal){
			if(auxPrincipio.next == auxFinal){
				tail.next = header;
				header.previous = tail;
				header = auxFinal;
				auxFinal.previous = null;
				tail = auxPrincipio;
				tail.next = null;
				// el cambio se ha realizado, para parada los ponemos iguales
				auxPrincipio = null;
				auxFinal = null;
				
			}
			
			if(auxPrincipio != null){
				auxPrincipio = auxPrincipio.next;
				auxFinal = auxFinal.previous;
			}
			
		}
	
	}	
	
	/**
	 * Comprueba que la lista es palindroma. Es decir, tiene el mismo contenido de izqda a derecha que de derecha a izqda.
	 * 
	 * La lista vacía si es palíndroma.
	 * 
	 */
	public boolean palindrome() {
		
		if(header == null){
			// lista vacía
			return true;
		} else {
			Iterator<T> front = iterator();
			Iterator<T> back = reverseIterator();
			
			T ele1 = front.next();
			T ele2 = back.next();
			
			while(ele1==ele2){
				if(!front.hasNext()){
					return true;
				} else{
					ele1 = front.next();
					ele2 = back.next();
				}
			}
			return false;
		}
	}
}
