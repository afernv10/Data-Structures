package ule.edi.list;

import java.util.Iterator;

public interface DoubleLinkedList<T> extends Iterable<T> {

	/**
	 * Inserta un elemento como primero.
	 * @param element a insertar
	 */
	public void addToFront(T element);
	
	/**
	 * Inserta un elemento como último.
	 * @param element a insertar
	 */
	public void addToRear(T element);

	/**
	 * Elimina el primer elemento.
	 * @return el elemento que es eliminado
	 * @throws NoSuchElementException si vacía
	 */
	public T removeFirst();

	/**
	 * Elimina el último elemento.
	 * @return el elemento que es eliminado
	 * @throws NoSuchElementException si vacía
	 */
	public T removeLast();
	


	/**
	 * Devuelve un iterador que recorre la lista en orden inverso.
	 * 
	 * Por ejemplo, para una lista x con elementos [A, B, C, D, E]
	 * 
	 * el iterador creado con x.reverseIterator() devuelve en sucesivas llamadas a next(): E, D, C, B y A.
	 * 
	 * @return iterador para orden inverso.
	 */
	public Iterator<T> reverseIterator();
}
