package ule.edi.tree;

import java.util.List;

/**
 * Árbol binario.
 * 
 * @author profesor
 *
 * @param <T>
 */
public abstract class AbstractBinaryTreeADT<T> extends AbstractTreeADT<T> {
	
	//	Como árbol binario, tiene dos sub-árboles binarios
	//	"izquierdo" y "derecho"
	//
	//	Podrían ser vacíos
	protected AbstractBinaryTreeADT<T> leftSubtree;
	protected AbstractBinaryTreeADT<T> rightSubtree;
	
	@Override
	public int getMaxDegree() {
		return 2;
	}

	@Override
	public TreeADT<T> getSubtree(int n) {
		//	El sub-árbol izquierdo es el "0"
		switch (n) {
		case 0:
			return leftSubtree;
		case 1:
			return rightSubtree;
		}
		
		throw new IllegalStateException("getSubtree(n) on a binary tree needs n in {0,1}");
	}

	/**
	 * Búsqueda en un árbol, no necesariamente binario de búsqueda.
	 * 
	 * @param element el elemento a buscar
	 * @param elementsChecked la lista de elementos comprobados durante la búsqueda
	 * @return <tt>true</tt> si el árbol contiene al elemento dado
	 */
	public boolean findInBinaryTree(T element, List<T> elementsChecked) {
		
		if (! isEmpty()) {
			//	Comprueba el valor en la raíz del sub-árbol actual
			elementsChecked.add(getContent());
			
			if (getContent().equals(element)) {
				//	El sub-árbol actual contiene el elemento
				return true;
			} else {
				//	Hay que seguir buscando en sub-árboles
				if (leftSubtree.findInBinaryTree(element, elementsChecked)) {
					return true;
				} else {
					//	En la rama izquierda no está, quizá en la derecha
					return rightSubtree.findInBinaryTree(element, elementsChecked);
				}
			}
		} else {
			//	No está en un árbol vacío
			return false;
		}
	}
	
}
