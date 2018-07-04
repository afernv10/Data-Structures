package ule.edi.tree;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class BinarySearchTreeADTTests {

    /*
	* ∅
    */
	private BinarySearchTreeADTImpl<Integer> TE = null;
	
	/*
	* 1
	* |  ∅
	* |  2
	* |  |  ∅
	* |  |  3
	* |  |  |  ∅
	* |  |  |  4
	* |  |  |  |  ∅
	* |  |  |  |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> T1234 = null;
	
	/*
	* 4
	* |  3
	* |  |  2
	* |  |  |  1
	* |  |  |  |  ∅
	* |  |  |  |  ∅
	* |  |  |  ∅
	* |  |  ∅
	* |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> T4321 = null;

	/*
	* 50
	* |  20
	* |  |  10
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  30
	* |  |  |  ∅
	* |  |  |  ∅
	* |  80
	* |  |  70
	* |  |  |  ∅
	* |  |  |  ∅
	* |  |  90
	* |  |  |  ∅
	* |  |  |  ∅
    */	
	private BinarySearchTreeADTImpl<Integer> TC3 = null;

	/*
	* 10
	* |  5
	* |  |  ∅
	* |  |  ∅
	* |  20
	* |  |  ∅
	* |  |  30
	* |  |  |  ∅
	* |  |  |  ∅
	*/
	private BinarySearchTreeADTImpl<Integer> TEx = null;

	/*
	 * 10
	 * |  5
	 * |  |  ∅
	 * |  |  7
	 * |  |  |  6
	 * |  |  |  |  ∅
	 * |  |  |  |  ∅
	 * |  |  |  ∅
	 * |  15
	 * |  |  ∅
	 * |  |  ∅
	 * 
	 */
	private BinarySearchTreeADTImpl<Integer> TV1 = null;
	
	private BinarySearchTreeADTImpl<Integer> prueba = null;

	@Before
	public void setupBSTs() {
		
		TE = new BinarySearchTreeADTImpl<Integer>();
		
		T1234 = new BinarySearchTreeADTImpl<Integer>();
		T1234.insert(1,2,3,4);
		
		T4321 = new BinarySearchTreeADTImpl<Integer>();
		T4321.insert(4, 3, 2, 1);
		
		TC3 = new BinarySearchTreeADTImpl<Integer>();
		TC3.insert(50, 20, 80, 10, 30, 70, 90);
		
		TEx = new BinarySearchTreeADTImpl<Integer>();
		TEx.insert(10, 20, 30, 5);
		
		TV1 = new BinarySearchTreeADTImpl<Integer>();
		TV1.insert(10, 5, 7, 6, 15);
		
		prueba = new BinarySearchTreeADTImpl<Integer>();
		prueba.insert(50, 30, 10, 80, 60);
		
	}
	
	@Ignore
	@Test
	public void showToStringAndRender() {
	
		//	No es un test, es un ejemplo; necesita 'insert' hecho
		System.out.println(TC3.toString());
		System.out.println(TC3.render());
	}
	
	@Test
	public void testInsert(){
		TE.insert(1, 2, 3);
		Assert.assertEquals("{1, ∅, {2, ∅, {3, ∅, ∅}}}", TE.toString());
	}
	
	@Test
	public void testInsertCollection(){
		LinkedList<Integer> collection = new LinkedList<Integer>();
		collection.add(1);
		collection.add(2);
		collection.add(3);
		TE.insert(collection);
		Assert.assertEquals("{1, ∅, {2, ∅, {3, ∅, ∅}}}", TE.toString());
	}
	
	@Test
	public void testInsertSameElem(){
		TE.insert(1, 2, 2, 3);
		Assert.assertEquals("{1, ∅, {2, ∅, {3, ∅, ∅}}}", TE.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertExceptionArray(){
		TE.insert(1, 2, null);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertExceptionOne(){
		Integer elemNull = null;
		TE.insert(elemNull);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testInsertExceptionCollection(){
		LinkedList<Integer> collection = new LinkedList<Integer>();
		collection.add(1);
		collection.add(null);
		TE.insert(collection);
	}
	
	@Test
	public void testIsBST(){
		Assert.assertTrue(T1234.isBST());
		Assert.assertTrue(T4321.isBST());
		Assert.assertTrue(TC3.isBST());
		Assert.assertTrue(TEx.isBST());
		Assert.assertTrue(TV1.isBST());
		Assert.assertTrue(prueba.isBST());
	}
	
	@Test
	public void testIsBSTChangeRI(){
		prueba.getRightBST().getLeftBST().setContent(20);
		Assert.assertFalse(prueba.isBST());
	}
	
	@Test
	public void testIsBSTChangeRD(){
		prueba.getLeftBST().getRightBST().setContent(60);
		Assert.assertFalse(prueba.isBST());
	}
	
	@Test
	public void testIsBSTEmpty(){
		Assert.assertTrue(TE.isBST());
	}
	
	@Test
	public void testIsBSTLeaf(){
		TE.insert(1);
		Assert.assertTrue(TE.isBST());
	}
	
	@Test
	public void testNOfNodesTagPreorden(){
		Assert.assertEquals(4, T1234.nOfNodesTagPreorden());
		Assert.assertEquals("{1 [(preorden, 1)], ∅, {2 [(preorden, 2)], ∅, {3 [(preorden, 3)], ∅, {4 [(preorden, 4)], ∅, ∅}}}}", T1234.toString());
	}
	
	@Test
	public void testNOfNodesTagPreordenTC(){
		Assert.assertEquals(7, TC3.nOfNodesTagPreorden());
		Assert.assertEquals("{50 [(preorden, 1)], {20 [(preorden, 2)], {10 [(preorden, 3)], ∅, ∅}, {30 [(preorden, 4)], ∅, ∅}}, {80 [(preorden, 5)], {70 [(preorden, 6)], ∅, ∅}, {90 [(preorden, 7)], ∅, ∅}}}", TC3.toString());
	}
	
	@Test
	public void testNOfNodesTagPreordenEx(){
		Assert.assertEquals(4, TEx.nOfNodesTagPreorden());
		Assert.assertEquals("{10 [(preorden, 1)], {5 [(preorden, 2)], ∅, ∅}, {20 [(preorden, 3)], ∅, {30 [(preorden, 4)], ∅, ∅}}}", TEx.toString());
	}
	
	@Test
	public void testNOfNodesTagPreordenTV1(){
		Assert.assertEquals(5, TV1.nOfNodesTagPreorden());
		Assert.assertEquals("{10 [(preorden, 1)], {5 [(preorden, 2)], ∅, {7 [(preorden, 3)], {6 [(preorden, 4)], ∅, ∅}, ∅}}, {15 [(preorden, 5)], ∅, ∅}}", TV1.toString());
	}
	
	@Test
	public void testNOfNodesTagPreordenEmpty(){
		Assert.assertEquals(0, TE.nOfNodesTagPreorden());
		Assert.assertEquals("∅", TE.toString());
	}
	
	@Test
	public void testTagDescend(){
		Assert.assertEquals("{1, ∅, {2, ∅, {3, ∅, {4, ∅, ∅}}}}", T1234.toString());
		T1234.tagDescend();
		Assert.assertEquals("{1 [(descend, 4)], ∅, {2 [(descend, 3)], ∅, {3 [(descend, 2)], ∅, {4 [(descend, 1)], ∅, ∅}}}}", T1234.toString());
		prueba.tagDescend();
		Assert.assertEquals("{50 [(descend, 3)], {30 [(descend, 4)], {10 [(descend, 5)], ∅, ∅}, ∅}, {80 [(descend, 1)], {60 [(descend, 2)], ∅, ∅}, ∅}}", prueba.toString());
		T4321.tagDescend();
		Assert.assertEquals("{4 [(descend, 1)], {3 [(descend, 2)], {2 [(descend, 3)], {1 [(descend, 4)], ∅, ∅}, ∅}, ∅}, ∅}", T4321.toString());
	}
	
	@Test
	public void testTagDescendEmpty(){
		TE.tagDescend();
		Assert.assertEquals("∅", TE.toString());
	}
	
	@Test
	public void testBinarySearchTreeADTImpl(){
		Assert.assertEquals("{3, ∅, {4, ∅, ∅}}", T1234.getSubtreeWithPath("11").toString());
	}
	
	@Test
	public void testBSTADTImplEmptyPath(){
		Assert.assertEquals(T1234.toString(), T1234.getSubtreeWithPath("").toString());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testBSTADTImplEmptyTree(){
		TE.getSubtreeWithPath("1001");
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testBSTADTImplPathNotFound(){
		T1234.getSubtreeWithPath("0");
	}
	
	@Test
	public void testIteratorBSTAndTagBreadthOrder(){
		Iterator<Integer> it = T1234.iteratorANDTagBreadthOrder();
		
		Assert.assertEquals(1, it.next().intValue());
		Assert.assertEquals(2, it.next().intValue());
		Assert.assertEquals(3, it.next().intValue());
		Assert.assertEquals(4, it.next().intValue());
		
		Assert.assertEquals("{1 [(level, 1)], ∅, {2 [(level, 2)], ∅, {3 [(level, 3)], ∅, {4 [(level, 4)], ∅, ∅}}}}", T1234.toString());
	}
	
	@Test
	public void testIteratorBSTAndTagBreadthOrderTC3(){
		Iterator<Integer> it = TC3.iteratorANDTagBreadthOrder();
		
		Assert.assertEquals(50, it.next().intValue());
		Assert.assertEquals(20, it.next().intValue());
		Assert.assertEquals(80, it.next().intValue());
		Assert.assertEquals(10, it.next().intValue());
		Assert.assertEquals(30, it.next().intValue());
		Assert.assertEquals(70, it.next().intValue());
		Assert.assertEquals(90, it.next().intValue());
		
		Assert.assertEquals("{50 [(level, 1)], {20 [(level, 2)], {10 [(level, 3)], ∅, ∅}, {30 [(level, 3)], ∅, ∅}}, {80 [(level, 2)], {70 [(level, 3)], ∅, ∅}, {90 [(level, 3)], ∅, ∅}}}", TC3.toString());
	}
	
	@Test
	public void testIteratorBSTAndTagBreadthOrderTEx(){
		Iterator<Integer> it = TEx.iteratorANDTagBreadthOrder();
		
		Assert.assertEquals(10, it.next().intValue());
		Assert.assertEquals(5, it.next().intValue());
		Assert.assertEquals(20, it.next().intValue());
		Assert.assertEquals(30, it.next().intValue());
		
		Assert.assertEquals("{10 [(level, 1)], {5 [(level, 2)], ∅, ∅}, {20 [(level, 2)], ∅, {30 [(level, 3)], ∅, ∅}}}", TEx.toString());
	}
	
	@Test
	public void testIteratorBSTAndTagEmptyTree(){
		Iterator<Integer> it = TE.iteratorANDTagBreadthOrder();
		
		Assert.assertFalse(it.hasNext());
	}

}

