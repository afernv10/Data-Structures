package ule.edi.recursive;

public class StackImpl<T> implements Stack<T> {

	public StackImpl() {
		
	}
	
	public StackImpl(T ...ts) {
		for (T t : ts) {
			push(t);
		}
	}
	
	@Override
	public boolean isEmpty() {
		return (contents.isEmpty());
	}

	@Override
	public T pop() {
		return contents.removeLast();
	}

	@Override
	public T peek() {
		return contents.getLast();
	}

	@Override
	public void push(T element) {
		// TODO Auto-generated method stub
		contents.add(element);
	}

	@Override
	public void clear() {
		contents.clear();
	}
	
	@Override
	public String toString() {
		return contents.toString() + ">";
	}

	private java.util.LinkedList<T> contents = new java.util.LinkedList<T>();
}
