package ule.edi.histogram;

import ule.edi.EmptyCollectionException;


/**
 * TAD 'Histogram'
 * 
 * Almacena objetos de tipo <code>T</code>, pero cuando varios objetos iguales (según su
 * método {@link #equals(Object)}) se añaden al contenedor, se mantiene una única referencia al objeto
 * y un contador de duplicados.
 * 
 * Excepciones:
 * 
 * 	-- No se permiten elementos <code>null</code>. Si a cualquier método que recibe un elemento se le pasa el
 * valor <code>null</code>, lanzará una excepción {@link NullPointerException}. 
 * 
 * 	-- Los valores de parámetros <code>count</code> deben ser mayores o iguales a cero, nunca negativos.
 * Si se recibe un valor negativo se lanzará {@link IllegalArgumentException}.
 *   
 * Ambas son ejemplos de "unchecked exceptions" 
 * {@link http://docs.oracle.com/javase/7/docs/api/java/lang/RuntimeException.html} 
 * que no hay que indicar en la signatura del método que las dispara.
 * 
 * Iterador:
 * 
 * Hereda del interface Iterable<T> por lo que una clase que implemente Histogram<T> tiene que implementar también el método:
 * public Iterator<T> iterator().
 * 
 * Los iteradores para la práctica no implementarán la operación {@link Iterator#remove()}.
 * 
 * Consultar la API de {@link Iterator} para saber qué excepciones deben lanzarse en los métodos cuando:  
 * 
 *   - Se solicita el siguiente elemento {@link Iterator#next()} y no existe.
 *   - Se invoca la operación {@link Iterator#remove()} pero no se ha implementado.
 *   
 * 	-- El iterador se desplaza por todos los elementos, uno a uno. Por ejemplo, con una
 * situación de [A (2), B (5)] donde el objeto A aparece 2 veces y B aparece 5, el
 * método {@link Iterator#next()} devolverá uno tras otro : A, A, B, B, B, B, B 
 *   
 * @author profesor
 * 
 * @param <T> tipo de elementos almacenados
 */
public interface Histogram<T> extends Iterable<T> {
	
	/**
	* Añade varias instancias de un elemento.
	* 
	* Por ejemplo, con una situación inicial de [XYZ (1), 123 (5)]
	* donde XYZ está una vez y 123 aparece cinco veces, al  añadir seis
	* instancias de ABC se tiene [ABC (6), XYZ (1), 123 (5)]; si ahora se
	* añaden dos instancias de 123 se tiene [ABC (6), XYZ (1), 123 (7)].
	*
	* @param element el elemento a añadir
	* @param times el número de instancias
	* @throws NullPointerException el elemento indicado es <code>null</code>
	* @throws IllegalArgumentException si <code>times</code> fuera negativo
	*/
	public void add(T element, long times);
	
	/**
	* Añade una instancia de un elemento.
	* 
	* @param element el elemento a añadir
	* @throws NullPointerException el elemento indicado es <code>null</code>
	*/
	public void add(T element);

	/**
	 * Elimina la primera aparición de un elemento.
     * 
     * Localiza el elemento igual al dado y elimina una aparición. Si no
     * se encuentra el elemento, no hace nada y devuelve null.
     * 
     * @param element
     * @return el elemento eliminado.
     * @throws NullPointerException el elemento indicado es <code>null</code>
     */
    public T remove(T element);

    /**
     * Elimina n apariciones de un elemento.
     * 
     * Por ejemplo, en una situación [A (5), B (1)] con remove(A, 3)
     * se llega a [A (2), B (1)] y se devuelve la referencia al objeto A.
     * 
     * Si no se encuentra el elemento, no hace nada y devuelve null. Si se
     * indica como número de apariciones a eliminar 0, devuelve null.
     * 
     * @param element elemento a eliminar
     * @param count número de instancias a eliminar
     * @return el último elemento eliminado.
     * @throws NullPointerException el elemento indicado es <code>null</code>
     */
    public T remove(T element, long count);

    /**
     * Devuelve y elimina todas las apariciones del elemento indicado.
     * 
     * Si no se encuentra el elemento, devuelve null.
     * 
	 * @return el elemento T eliminado
	 * @throws NullPointerException el elemento indicado es <code>null</code>
     */
    public T removeElementAll(T element);
    
    /**
     * Devuelve y elimina todas las apariciones del primer elemento.
     * 
     * Por ejemplo, con [A (2), B (8)] si se invoca a este método se
     * tendrá [B (8)] y se obtendrá A como resultado.
     * 
	 * @return el elemento T que se encontraba en la primera posición
     */
    public T removeAllFirst() throws EmptyCollectionException;
    
    /**
     * Devuelve y elimina todas las apariciones del último elemento.
     * 
     * Por ejemplo, con [A (2), B (8)] si se invoca a este método se
     * tendrá [A (2)] y se obtendrá B como resultado.
     *  
	 * @return el elemento T que se encontraba en la última posición
     */
    public T removeAllLast() throws EmptyCollectionException;
    
    /**
     * Devuelve el primer elemento.
     * 
     * Por ejemplo, con un [A (2), B (8)] se obtendrá A.
     * 
	 * @return el elemento T que se encuentra en la primera posición
	 * @throws EmptyCollectionException si no hay elementos
     */
    public T getFirst() throws EmptyCollectionException;
    
    /**
     * Devuelve el último elemento
     * 
     * Por ejemplo, con [A (2), B (8)] se obtendrá B.
     * 
	 * @return el elemento T que se encuentra en la última posición 
     * @throws EmptyCollectionException si no hay elementos
     */
    public T getLast() throws EmptyCollectionException;
    
    /**
    * Devuelve el número total de instancias almacenadas
    *  
    * Por ejemplo, para ["5€" (2), "10€" (8)] se indicará
    * un tamaño de 2 + 8 = 10. 
    * 
    * @return número total de instancias almacenadas
    */
    public long size();
    
    /**
    * Indica si el elemento está almacenado
    * 
    * Devuelve <code>true</code> si al menos existe una
    * instancia del elemento dado (es decir, existe 
    * un elemento 'x' tal que <code>x.equals(element)</code>)
    * y <code>false</code> en caso contrario.
    *  
    * @param element elemento a buscar
    * @return <code>true</code>/<code>false</code> según el resultado
    * @throws NullPointerException el elemento indicado es <code>null</code> 
    */
    public boolean contains(T element);
    
    /**
     * Devuelve el elemento situado en la posición indicada
     * 
     * Las posiciones empiezan en 1
     * 
     * Por ejemplo, con [A (2), B (8)] se tiene A en la posición
     * 1 y B en la posición 2.
     *  
	 * @param position qué posición se desea consultar
	 * @return el elemento T que se encuentra en esa posición
	 * @throws IndexOutOfBoundsException si la posición no es válida
     */
    public T getElementAt(long position);
    
    /**
     * Devuelve y elimina el elemento (todas las apariciones) situado en una posición
     * 
     * Las posiciones empiezan en 1
     * 
     * Por ejemplo, con [A (2), B (8)], si se llama al método para la
     * posición 2, devolverá B y dejará una nueva situación [A (2)].
     * 
	 * @param position del elemento a eliminar por completo
	 * @return el elemento T que se encontraba en esa posición
	 * @throws IndexOutOfBoundsException si la posición no es válida.
     */
    public T removeAllElementAt(long position);
    
    /**
    * Indica si no hay elementos
    *  
    * @return <code>true</code> si no hay elementos 
    */
    public boolean isEmpty();
    
    /**
    * Devuelve el número de instancias del elemento dado 
    * 
    * Es decir, el número de instancias del objeto 'x' tal que
    * <code>x.equals(element)</code>. Por ejemplo, con 
    * [A (2), B (8)] se indicará 8 si se pregunta por el
    * elemento B.
    *  
    * Si el elemento no está, se devuelve cero.
    *  
    * @param element el elemento a consultar
    * @return el número de instancias que hay de ese elemento
    * @throws NullPointerException el elemento indicado es <code>null</code>
    */
    public long count(T element);

    /**
     * Elimina todo el contenido y deja el almacén sin elementos
     */
    public void clear();

}
