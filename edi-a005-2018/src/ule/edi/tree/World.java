package ule.edi.tree;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 * Un mundo es un árbol binario. 
 * En cada nodo de un mundo se almacena una lista de entidades, cada una con su tipo y
 * cardinalidad. Ver {@link Entity}.
 * 
 * Si se codifica "bajar por la izquierda" como "L" y
 * "bajar por la derecha" como "R", el camino desde un 
 * nodo N hasta un nodo M (en uno de sus sub-árboles) será la
 * cadena de Ls y Rs que indica cómo llegar desde N hasta M.
 *
 * Se define también el camino vacío desde un nodo N hasta
 * él mismo, como cadena vacía.
 * 
 * Por ejemplo, el mundo:
 * 
 * {[F(1)], {[F(1)], {[D(2), P(1)], ∅, ∅}, {[C(1)], ∅, ∅}}, ∅}
 * 
 * o lo que es igual:
 * 
 * [F(1)]
 * |  [F(1)]
 * |  |  [D(2), P(1)]
 * |  |  |  ∅
 * |  |  |  ∅
 * |  |  [C(1)]
 * |  |  |  ∅
 * |  |  |  ∅
 * |  ∅
 * 
 * contiene un bosque (forest) en "", otro en "L", dos dragones y una princesa en "LL" y
 * un castillo en "LR".
 * @param <T>
 * 
 */
public class World extends AbstractBinaryTreeADT<LinkedList<Entity>> {

	/**
	 * Devuelve el mundo al que se llega al avanzar a la izquierda.
	 * 
	 * @return
	 */
	protected World travelLeft() {
		
		return (World) leftSubtree;
	}

	private void setLeft(World left) {
		
		this.leftSubtree = left;
	}
	
	/**
	 * Devuelve el mundo al que se llega al avanzar a la derecha.
	 * 
	 * @return
	 */
	protected World travelRight() {
		
		return (World) rightSubtree;
	}

	private void setRight(World right) {
		
		this.rightSubtree = right;
	}
	
	private World() {
		
		//	Crea un mundo vacío
		this.content = null;
		this.leftSubtree = this.rightSubtree = null;
	}
	
	public static World createEmptyWorld() {
		
		return new World();
	}
	
	/**
	 * Inserta la entidad indicada en este árbol.
	 * 
	 * La inserción se produce en el nodo indicado por la dirección; todos
	 * los nodos recorridos para alcanzar aquél que no estén creados se
	 * inicializarán con una entidad 'forest'.
	 * 
	 * La dirección se supondrá correcta, compuesta de cero o más Ls y Rs.
	 * 
	 * Dentro de la lista del nodo indicado, la inserción de nuevas entidades
	 * se realizará al final, como último elemento.
	 * 
	 * Por ejemplo, en un árbol vacío se pide insertar un 'dragón' en la
	 * dirección "LL". El resultado final será:
	 * 
     * [F(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     * 
     * La dirección "" indica la raíz, de forma que insertar un 'guerrero' en
     * "" en el árbol anterior genera:
     * 
     * [F(1), W(1)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     * 
     * La inserción tiene en cuenta la cardinalidad, de forma que al volver a
     * insertar un guerrero en "" se tiene:
     * 
     * [F(1), W(2)]
     * |  [F(1)]
     * |  |  [D(1)]
     * |  |  |  ∅
     * |  |  |  ∅
     * |  |  ∅
     * |  ∅
     *  
	 * @param address dirección donde insertar la entidad.
	 * @param e entidad a insertar.
	 */
	public void insert(String address, Entity e) {
		
		//TODO implementar el metodo
		char[] steps = address.toCharArray();
		World w = this;	// mundo en el que estamos
		
		w = toAddress(this, steps);
		
		if(w.isEmpty()){
			w.content = new LinkedList<Entity>();
			w.content.add(e);
			w.setLeft(new World());
			w.setRight(new World());
		} else {
			if(w.content.indexOf(e) != -1){
				int posWorld = w.content.indexOf(e);	// miramos la posicion en el mundo, pos lista
				long countThisPos = w.content.get(posWorld).getCount();
				w.content.get(posWorld).setCount(countThisPos + e.getCount());
			} else {
				 w.content.add(e);	// no había una entidad igual
			}
		}
	}
	
	private World toAddress(World world, char[] steps) {
		for(int i = 0; i < steps.length; i++){
			if((world.isEmpty() || content == null) && i < steps.length){
				// crearemos Forest hasta llegar a la dirección
				world.content = new LinkedList<Entity>();
				world.content.add(new Entity(Entity.FOREST));
				world.setLeft(new World());
				world.setRight(new World());
			}
			if(steps[i] == 'L') world = world.travelLeft();
			if(steps[i] == 'R') world = world.travelRight();
		}
		
		return world;	
	}
	
	/**
	 * Indica cuántas entidades del tipo hay en este mundo.
	 * 
	 * @param type tipo de entidad.
	 * @return cuántas entidades de ese tipo hay en este mundo.
	 */
	public long countEntity(int type) {
		if(isEmpty()){
			return 0;
		} else {
			int index = content.indexOf(new Entity(type));
			if(index != -1){
				return content.get(index).getCount() + travelLeft().countEntity(type) + travelRight().countEntity(type);
			} else {
				if(travelLeft() == null && travelRight() == null){
					return 0;
				} else {
					return travelLeft().countEntity(type) + travelRight().countEntity(type);
				}
				
			}
		}
	}

	
	/**
	 * Construye y devuelve el arbol suma de this y other.
	 * El árbol suma tiene la estructura combinada de ambos árboles y, en los casos donde dos nodos coincidían, 
	 * el valor del nodo resultado es la suma del contenido de los dos nodos originales.
	 * 
	 * 
	 *por ejemplo para el mundo {[F(1)], {[F(1)], {[D(2), P(1)], ∅, ∅}, {[C(1)], ∅, ∅}}, ∅}
	 *si other es {[F(1)], {[P(2)], ∅, ∅}, [D(1)]}
	 * 
	 * el resultado será: {[F(2)], {[F(1), P(2)], {[D(2), P(1)], ∅, ∅}, {[C(1)], ∅, ∅}}, [D(1)]}
	 * 
	 * @param other arbol para sumar a this
	 * @return arbol suma.
	 */
	public World add(World other){
		
		if(isEmpty() != other.isEmpty()){
			return (isEmpty()) ? other : this;
		}
		World res = new World();
		res = cloneTree(this);
		res.mergeWorlds(other);
		return res;
		//TODO Implementar el método		
	}
	
	private World(World root){
		this.content = root.content;
		this.leftSubtree = this.rightSubtree = null;
	}
	
	private World cloneTree(World world) {
		if(world.isEmpty()) return createEmptyWorld();
		World nuevo = new World(world);
		nuevo.leftSubtree = cloneTree(world.travelLeft());
		nuevo.rightSubtree = cloneTree(world.travelRight());
		return nuevo;
	}

	private void mergeWorlds(World other) {
		
		if(!other.isEmpty() && (content != null || other.content != null)){
			if(isEmpty()){
				for(int i = 0; i < other.content.size(); i++){
					insert("", other.content.get(i));
				}
			} else if(other.isEmpty()){
				for(int i = 0; i < this.content.size(); i++){
					insert("", this.content.get(i));
				}
			} else {
				
				for(int i = 0; i < other.content.size(); i++){
					if(isEmpty()) insert("", other.content.get(i));
					else if(other.isEmpty()) insert("", this.content.get(i));
					else insert("", other.content.get(i));
				}
				
			}
			travelLeft().mergeWorlds(other.travelLeft());
			travelRight().mergeWorlds(other.travelRight());
		}

	}

	/**
	 * Calcula el camino a la princesa accesible mas lejana de la raiz 
	 * 
	 * Una princesa es accesible si en el camino desde la raiz hasta ella no aparece ningún Dragón
	 * 
	 * Si hay empate se dará prioridad a la rama izquierda
	 * 
	 * El camino estará formado por L's y R's
	 * 
	 * Si la princesa accesible más cercana es la propia raiz se devolverá la cadena vacía.
	 * Si el arbol es vacío se disparará la excepción EmptyCollectionException
	 * 
	 * 
	 * @return el camino desde la raiz hasta la princesa
	 */
	public String getPathToFurthestAccessPrinces() {
		
		/*if(isEmpty()){
			return null;
		} else if(content.indexOf(Entity.PRINCESS) == -1){
			return null;
		} else {
			int index = Entity.PRINCESS;
			return getPathToFurthestPrincessRec(new StringBuilder(), 0, index);
		}*/
		return null;
	}
	
	/*private String getPathToFurthestPrincessRec(StringBuilder s, int cont, int princesa) {
		if(!isEmpty()){
			int compare = content.indexOf(princesa);
			if(compare != -1){
				cont++;
			}
		}
		return string;
	}*/
	
	@Override
	public String toString() {
		if (!isEmpty()) {
			// Construye el resultado de forma eficiente
			StringBuffer result = new StringBuffer();

			// Raíz
			Collections.sort(content);
			result.append("{" + content.toString());

			if (!tags.isEmpty()) {
				result.append(" [");

				List<String> sk = new LinkedList<String>(tags.keySet());

				Collections.sort(sk);
				for (String k : sk) {
					result.append("(" + k + ", " + tags.get(k) + "), ");
				}
				result.delete(result.length() - 2, result.length());
				result.append("]");
			}

			// Y cada sub-árbol
			for (int i = 0; i < getMaxDegree(); i++) {
				result.append(", " + getSubtree(i).toString());
			}
			// Cierra la "}" de este árbol
			result.append("}");

			return result.toString();
		} else {
			return AbstractTreeADT.EMPTY_TREE_MARK;
		}
	}
	
	
}
