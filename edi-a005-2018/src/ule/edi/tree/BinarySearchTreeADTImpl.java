package ule.edi.tree;


import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Stack;

import ule.edi.recursive.StackImpl;

/**
 * Árbol binario de búsqueda (binary search tree, BST).
 * 
 * El código fuente está en UTF-8, y la constante 
 * EMPTY_TREE_MARK definida en AbstractTreeADT del
 * proyecto API debería ser el símbolo de conjunto vacío: ∅
 * 
 * Si aparecen caracteres "raros", es porque
 * el proyecto no está bien configurado en Eclipse para
 * usar esa codificación de caracteres.
 *
 * En el toString() que está ya implementado en AbstractTreeADT
 * se usa el formato:
 * 
 * 		Un árbol vacío se representa como "∅". Un árbol no vacío
 * 		como "{(información raíz), sub-árbol 1, sub-árbol 2, ...}".
 * 
 * 		Por ejemplo, {A, {B, ∅, ∅}, ∅} es un árbol binario con 
 * 		raíz "A" y un único sub-árbol, a su izquierda, con raíz "B".
 * 
 * El método render() también representa un árbol, pero con otro
 * formato; por ejemplo, un árbol {M, {E, ∅, ∅}, {S, ∅, ∅}} se
 * muestra como:
 * 
 * M
 * |  E
 * |  |  ∅
 * |  |  ∅
 * |  S
 * |  |  ∅
 * |  |  ∅
 * 
 * Cualquier nodo puede llevar asociados pares (clave,valor) para
 * adjuntar información extra. Si es el caso, tanto toString() como
 * render() mostrarán los pares asociados a cada nodo.
 * 
 * Con {@link #setTag(String, Object)} se inserta un par (clave,valor)
 * y con {@link #getTag(String)} se consulta.
 * 
 * 
 * Con <T extends Comparable<? super T>> se pide que exista un orden en
 * los elementos. Se necesita para poder comparar elementos al insertar.
 * 
 * Si se usara <T extends Comparable<T>> sería muy restrictivo; en
 * su lugar se permiten tipos que sean comparables no sólo con exactamente
 * T sino también con tipos por encima de T en la herencia.
 * 
 * @param <T>
 *            tipo de la información en cada nodo, comparable.
 */
public class BinarySearchTreeADTImpl<T extends Comparable<? super T>> extends
		AbstractBinaryTreeADT<T> {

	/**
	 * Devuelve el árbol binario de búsqueda izquierdo.
	 */
	protected BinarySearchTreeADTImpl<T> getLeftBST() {
		//	El atributo leftSubtree es de tipo AbstractBinaryTreeADT<T> pero
		//	aquí se sabe que es además de búsqueda binario
		//
		return (BinarySearchTreeADTImpl<T>) leftSubtree;
	}

	private void setLeftBST(BinarySearchTreeADTImpl<T> left) {
		this.leftSubtree = left;
	}
	
	/**
	 * Devuelve el árbol binario de búsqueda derecho.
	 */
	protected BinarySearchTreeADTImpl<T> getRightBST() {
		return (BinarySearchTreeADTImpl<T>) rightSubtree;
	}

	private void setRightBST(BinarySearchTreeADTImpl<T> right) {
		this.rightSubtree = right;
	}
	
	/**
	 * Árbol BST vacío
	 */
	public BinarySearchTreeADTImpl() {
		
		setContent(null);
		
		setLeftBST(null);
		setRightBST(null);
	}

	private BinarySearchTreeADTImpl<T> emptyBST() {
		return new BinarySearchTreeADTImpl<T>();
	}
	
	/**
	 * Inserta todos los elementos de una colección en el árbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements
	 *            valores a insertar.
	 */
	public void insert(Collection<T> elements) {
		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
		// Implementar el método
		for(T elemNull:elements){
			if(elemNull == null){
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}
		
		for(T elem:elements){
			insert(elem);
		}
	}

	/**
	 * Inserta todos los elementos de un array en el árbol.
	 * 
	 * No se permiten elementos null.
	 * 
	 * @param elements elementos a insertar.
	 */
	@SuppressWarnings("unchecked")
	public void insert(T ... elements) {
		//	O todos o ninguno; si alguno es 'null', ni siquiera se comienza a insertar
	    // Implementar el método
		for(T elemNull:elements){
			if(elemNull == null){
				throw new IllegalArgumentException("No se aceptan elementos nulos");
			}
		}
		
		for(T elem:elements){
			insert(elem);
		}
	}
	
	/**
	 * Inserta de forma recursiva (como hoja) un nuevo elemento en el árbol de búsqueda.
	 * 
	 * No se permiten elementos null. Si el elemento ya existe en el árbol NO lo inserta.
	 * 
	 * @param element
	 *            valor a insertar.
	 */
	public void insert(T element) {
		//	No se admiten null
		if (element == null) {
			throw new IllegalArgumentException("No se aceptan elementos nulos");
		} else{
			// Implementar el método
			if(isEmpty()){
				setContent(element);
				
				// añadimos hijos vacios a dcha e izq
				setLeftBST(emptyBST());
				setRightBST(emptyBST());
			} else {
				// valor de comparación con raíz del árbol
				int compare = element.compareTo(content);
				
				if(compare == 0){
					// element ya está en árbol
					return;
				}
				if(compare < 0){
					// es menor, irá a la izq
					getLeftBST().insert(element);
				}
				if(compare > 0){
					// es mayor, irá a la dcha
					getRightBST().insert(element);
				}
			}
		}
	}
	
	
	/**
	 * Calcula y devuelve el número de nodos del árbol 
	 *  Si el árbol es vacío devolverá 0
	 * Además, etiqueta cada nodo con su posición en preorden, con clave "preorden" y valor el entero correspondiente a su posición en preorden
	 * 
	 * Por ejemplo, el árbol
	 * 
	 * {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * devuelve 6 y queda etiquetado como 
	 * 
	 *   {50 [(preorden, 1)], {30 [(preorden, 2)], {10 [(preorden, 3)], ∅, ∅}, {40 [(preorden, 4)], ∅, ∅}}, {80 [(preorden, 5)], {60 [(preorden, 6)], ∅, ∅}, ∅}}
	 * 
	 * @return número de nodos del árbol
	 */	
	public int nOfNodesTagPreorden() {	
	// Implementar el método
		if(isEmpty()){
			return 0;
		} else {
			int[] cont = new int[1];
			cont[0] = 0;
			return nOfNodesTagPreordenRec(cont);
		}	
	}
	
	private int nOfNodesTagPreordenRec(int[] count) {

		if (!isEmpty()) {
			count[0]++;
			this.setTag("preorden", count[0]);
			getLeftBST().nOfNodesTagPreordenRec(count);
			getRightBST().nOfNodesTagPreordenRec(count);
		}
		return count[0];
	}
	
    /*private int nOfNodesTagPreordenRec(int[] cont){
    	if(content == null){
    		return cont[0];
    	} else {
    		setTag("preorden", cont[0]);
    		
    		if(!isLeaf()) cont[0] = cont[0] +1;
        	
    		cont[0] = getLeftBST().nOfNodesTagPreordenRec(cont);
        	cont[0] = getRightBST().nOfNodesTagPreordenRec(cont);
        	return cont[0];
    	}
    	
    }*/

	
	/**
	 * 
	 * Etiqueta cada nodo con su posición en el recorrido descendente, con la etiqueta "descend"
	 * 
	 * Por ejemplo, el árbol
	 * 
	 * {50, {30, {10, ∅, ∅}, {40, ∅, ∅}}, {80, {60, ∅, ∅}, ∅}}
	 * 
	 * devuelve 6 y queda etiquetado como 
	 * 
	 *   {50 [(descend, 3)], 
                 {30 [(descend, 5)], {10 [(descend, 6)], ∅, ∅},{40 [(descend, 4)], ∅, ∅}},
                 {80 [(descend, 1)], {60 [(descend, 2)], ∅, ∅}, ∅}}
	 * 
*
	 */	
	public void tagDescend() {
		//TODO implementar el método
		int[] nTag = new int[1];
		nTag[0] = 1;
		tagDescendRec(nTag);
	}
	
	private void tagDescendRec(int[] nTag){
		if(content != null){
			
			getRightBST().tagDescendRec(nTag);
			setTag("descend", nTag[0]);
			nTag[0] = nTag[0]+1;
			getLeftBST().tagDescendRec(nTag);
			
		}
	}
	
	
	
	/**
	 * Comprueba si es binario de búsqueda.
	 * 
	 * @return  true si es binario de búsqueda, falso si no lo es
	 */
	public boolean isBST() {
		// Implementar método
		if(isEmpty() || isLeaf()){
			return true;
		} else {
			return isBSTRec(null, null);
		}
	}
	
	private boolean isBSTRec(T minLimit, T maxLimit){
		if(isEmpty()){
			return true;
		} else {
			
			if(maxLimit != null && content.compareTo(maxLimit) > 0){
				return false;
			}
			if(minLimit != null && content.compareTo(minLimit) < 0){
				return false;
			}
			
			return getLeftBST().isBSTRec(minLimit, content) && getRightBST().isBSTRec(content, maxLimit);
		}

		
	}
	
	/*
	 * // tanto a izquierda como a dcha no comprobará los 
		if(subtree.getContent().compareTo(minLimit) < 0 || subtree.getContent().compareTo(maxLimit) > 0){
			return false;
		}
		
		// en el valor minimo por la izq ponemos el mismo al que vamos
		// lo mismo en el valor maximo por la derecha
		return isBSTRec(subtree.getLeftBST(), subtree.getLeftBST().content, subtree.content) && isBSTRec(subtree.getRightBST(), subtree.content, subtree.getRightBST().content);
	 */
	
	/**
	 * Devuelve el sub-árbol indicado. (para tests)
	 * path será el camino para obtener el sub-arbol. Está formado por 0 y 1.
	 * Si se codifica "bajar por la izquierda" como "0" y
	 * "bajar por la derecha" como "1", el camino desde un 
	 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
	 * cadena de 0s y 1s que indica cómo llegar desde N hasta M.
     *
     * Se define también el camino vacío desde un nodo N hasta
     * él mismo, como cadena vacía.
	 * 
	 * Si el subarbol no existe lanzará la excepción NoSuchElementException.
	 * 
	 * @param path
	 * @return
	 * @throws NoSuchElementException si el subarbol no existe
	 */
	public BinarySearchTreeADTImpl<T> getSubtreeWithPath(String path) {
		//TODO implementar el método
		if(isEmpty()){
			throw new NoSuchElementException();
		} else {
			char[] steps = path.toCharArray();
			if(steps.length == 0){
				return this;
			} else {
				return getSubtreeWithPathRec(steps, 0);
			}
		}
	}	
	
	private BinarySearchTreeADTImpl<T> getSubtreeWithPathRec(char[] steps, int n) {
		if(isEmpty()){
			throw new NoSuchElementException();
		} else if(n == steps.length ){
			return this;
		} else {
			return ((BinarySearchTreeADTImpl<T>) getSubtree(Character.getNumericValue(steps[n]))).getSubtreeWithPathRec(steps, n+1);
		}
	}

	/**	
	 * Devuelve un iterador que recorre los elementos del arbol en anchura
	 * Además etiqueta cada nodo con su nivel (raíz en nivel 1).
	 * 
	 * Por ejemplo, con el árbol
	 * 
	 * 		{D, {B, {A, ∅, ∅}, {C, ∅, ∅}}, {E, ∅, {F, ∅, ∅}}}
	 * 
	 * se dejaría etiquetado como
	 * 
	 * 		{D [(level, 1)],
	 * 			{B [(level, 2)],
	 * 				{A [(level, 3)], ∅, ∅}, {C [(level, 3)], ∅, ∅}},
	 * 			{E [(level, 2)], ∅, {F [(level, 3)], ∅, ∅}}}
	 * 
	 * y devolvería el iterador que recorrería los ndos en el orden: D, B, E, A, C, F
	 * 
	 * 		
	 * 
	 * @return iterador para el recorrido en anchura 
	 */
	public Iterator<T> iteratorANDTagBreadthOrder() {
		
		Queue<BinarySearchTreeADTImpl<T>> cola = new LinkedList<>();
		LinkedList<T> resultados = new LinkedList<>();
		cola.add(this);
		setTag("level", new Integer(1));
		
		while(!cola.isEmpty()){
			BinarySearchTreeADTImpl<T> temp = cola.poll();
			Integer n = (Integer) temp.getTag("level");
			if(temp.getContent() != null){
				resultados.add(temp.getContent());
				cola.add(temp.getLeftBST());
				cola.add(temp.getRightBST());
				temp.getLeftBST().setTag("level", n+1);
				temp.getRightBST().setTag("level", n+1);
			}
		}
		
		return resultados.iterator();
	}
	


	
}

