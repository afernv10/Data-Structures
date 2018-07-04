package ule.edi.histogram;

public interface UnorderedHistogram<T> extends Histogram<T> {
	
	/**
	 * Inserta un elemento como primero.
	 * 
	 * Si ya existe un elemento igual, se actualiza su
	 * contador de instancias en lugar de añadirlo como
	 * primero.
	 * 
	 * Por ejemplo, con [A (2)] se añade B para llegar a
	 * [B (1), A (2)]; si ahora se añade A se actualiza
	 * su contador y se tendrá [B (1), A (3)].
	 * 
	 * @param element elemento a insertar
	 * @throws NullPointerException si se inserta <code>null</code>
	 */
	public void addToFront(T element);
	
	/**
	 * Inserta varias instancias como primer elemento.
	 * 
	 * Si ya existe un elemento igual, se actualiza su
	 * contador de instancias en lugar de añadirlo como
	 * primero.
	 * 
	 * @param element elemento a insertar
	 * @param count número de instancias a insertar
	 * @throws NullPointerException si se inserta <code>null</code>
	 * @throws IllegalArgumentException si count es negativo
	 */
	public void addToFront(T element, long count);
	
	/**
	 * Inserta un elemento como último.
	 * 
	 * Si ya existe un elemento igual, se actualiza su
	 * contador de instancias en lugar de añadirlo como
	 * último.
	 * 
	 * Por ejemplo, con [A (2)] se añade B para llegar a
	 * [A (2), B (1)]; si ahora se añade A se actualiza
	 * su contador y se tendrá [A (3), B (1)].
	 * 
	 * @param element elemento a insertar
	 * @throws NullPointerException si se inserta <code>null</code>
	 */
	public void addToRear(T element);

	/**
	 * Inserta varias instancias como último elemento.
	 * 
	 * Si ya existe un elemento igual, se actualiza su
	 * contador de instancias en lugar de añadirlo como
	 * último.
	 * 
	 * @param element elemento a insertar
	 * @param count número de instancias a insertar
	 * @throws NullPointerException si se inserta <code>null</code>
	 * @throws IllegalArgumentException si count es negativo
	 */
    public void addToRear(T element, long count);

}
