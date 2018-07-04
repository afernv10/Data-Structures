package ule.edi.recursive;

import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public abstract class AbstractSingleLinkedListImpl<T> implements SingleLinkedList<T> {

	//	operaciones fijadas en API; pueden requerir parámetros con ".header" o nodos

	/**
	 * Indica la posición del último elemento dado (posiciones empiezan en 1).
	 * 
	 * Si no estuviera, devolverá cero.
	 *
	 * Ejemplo: [A, B, C, B, C] la última B está en la posición 4
	 * 
	 * @param element
	 * @return
	 */
	public abstract long positionOfLast(T element);
	
	/**
	 * Elimina duplicados consecutivos.
	 * 
	 * Ejemplo: [A, A, B, A, A, B] >> [A, B, A, B]
	 * 
	 * @return
	 */
	public abstract AbstractSingleLinkedListImpl<T> removeConsecutiveDuplicates();
	
	/**
	 * Indicar si a es subsecuencia de esta lista.
	 * 
	 * a es subsecuencia de x si todos los de a están en x en el mismo orden.
	 * 
	 * Ejemplos:	[A, C] lo es de	[A, B, C, D]; 
	 *              [A, C, Z] no lo es de [A, B, C, D].
	 * 
	 * @param a
	 * @return
	 */
	public abstract boolean subsequence(AbstractSingleLinkedListImpl<T> a);
	
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
	public abstract long maxPrefix(AbstractSingleLinkedListImpl<T> part);

	/**
	 * Comprueba que la lista es palindroma.
	 * 
	 * Es decir, tiene el mismo contenido de izqda a derecha que de derecha a izqda.
	 * 
	 * La lista vacía si es palíndroma.
	 */
	public abstract boolean palindrome();
	
	/**
	 * Carga los elementos de esta lista en dos pilas.
	 * 
	 * La lista no se modifica.
	 * 
	 * Ejemplo: [A, B, C] cargará [A, B, C]> en la primera pila y
	 * [C, B, A]> en la segunda, donde ']>' es la cima de la pila.
	 * 
	 * @param forwards
	 * @param backwards
	 */
	public abstract void loadIntoStacks(Stack<T> forwards, Stack<T> backwards);
	
	/**
	 * Iterador que en cada next, avanza un elemento mas. 
	 * Devuelve el primer elemento, el segundo (+1), el cuarto (+2), el septimo (+3), el onceavo (+4), etc.. 
	 * Devuelve un iterador que devuelve para una lista [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18]
	 * next() -> 1
	 * next() -> 2
	 * next() -> 4
	 * next() -> 7
	 * next() ->11
	 * next() ->16
	 * hasNext() -> false
	 * 
	 * NO SE PERMITE PASAR TODOS LOS ELEMENTOS A UNA ESTRUCTURA AUXILIAR Y RECORRER LA ESTRUCTURA AUXILIAR
	 * 
	 * @return iterador progresivo
	 */
	public abstract Iterator<T> progIterator();
	
	/**
	 * Busca el máximo elemento en la lista.
	 * 
	 * Para comparar elementos, recurre al comparador
	 * pasado como parámetro.
	 * 
	 * @param cmpf
	 * @return
	 * @throws NoSuchElementException si está vacía
	 */
	public abstract T max(Comparator<T> cmpf);
	
	//	estructura de datos y métodos ya implementados

	static class Node<G> {
		
		public Node(G element) {
			this.content = element;
			this.next = null;
		}
		
		G content;
		
		Node<G> next;
	}
	
	protected Node<T> header;

	
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

	

}
