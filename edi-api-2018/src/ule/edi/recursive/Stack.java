package ule.edi.recursive;

public interface Stack<T> {

	public boolean isEmpty();
	
	public T pop();
	
	public T peek();
	
	public void push(T element);
	
	public void clear();
}
