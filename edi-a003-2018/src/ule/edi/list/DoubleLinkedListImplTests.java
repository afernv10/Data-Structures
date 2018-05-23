package ule.edi.list;



import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.*;


public class DoubleLinkedListImplTests {

	private DoubleLinkedListImpl<String> lS;
	private DoubleLinkedListImpl<String> lSABC;

	@Before
	public void setup() {
		this.lS = new DoubleLinkedListImpl<String>();
	    this.lSABC=new DoubleLinkedListImpl<String>("A", "B", "C");
	}
	
	@Test
	public void testToStringVacio(){
		Assert.assertEquals(lS.toString(),"[]");		
	}
	
	@Test
	public void testToStringNoVacio(){
		Assert.assertEquals(lSABC.toString(),"[A, B, C]");		
	}
	
	@Test
	public void testAddToFront(){
		lS.addToFront("A");
		Assert.assertEquals("[A]", lS.toString());
		lSABC.addToFront("X");
		Assert.assertEquals("[X, A, B, C]", lSABC.toString());
	}
	
	@Test
	public void testAddToRear(){
		lS.addToRear("A");
		Assert.assertEquals("[A]", lS.toString());
		lSABC.addToRear("X");
		Assert.assertEquals("[A, B, C, X]", lSABC.toString());
	}
	
	@Test
	public void testRemoveFirst(){
		Assert.assertEquals("A", lSABC.removeFirst());
		Assert.assertEquals("[B, C]", lSABC.toString());
		Assert.assertEquals("B", lSABC.removeFirst());
		Assert.assertEquals("[C]", lSABC.toString());
		Assert.assertEquals("C", lSABC.removeFirst());
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveFirstEmpty(){
		lS.removeFirst();
	}
	
	@Test
	public void testRemoveLast(){
		Assert.assertEquals("C", lSABC.removeLast());
		Assert.assertEquals("[A, B]", lSABC.toString());
		Assert.assertEquals("B", lSABC.removeLast());
		Assert.assertEquals("[A]", lSABC.toString());
		Assert.assertEquals("A", lSABC.removeLast());
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testRemoveLastEmpty(){
		lS.removeLast();
	}
	
	@Test
	public void testDropPositive(){
		Assert.assertEquals("[A, B, C]", lSABC.toString());
		lSABC.drop(2);
		Assert.assertEquals("[C]", lSABC.toString());
	}
	
	@Test
	public void testDropNegative(){
		Assert.assertEquals("[A, B, C]", lSABC.toString());
		lSABC.drop(-2);
		Assert.assertEquals("[A]", lSABC.toString());
	}
	
	@Test
	public void testDropZero(){
		Assert.assertEquals("[A, B, C]", lSABC.toString());
		lSABC.drop(0);
		Assert.assertEquals("[A, B, C]", lSABC.toString());
	}
	
	@Test
	public void testDropMoreThanPosiblePositive(){
		Assert.assertEquals("[A, B, C]", lSABC.toString());
		lSABC.drop(4);
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test
	public void testDropMoreThanPosibleNegative(){
		Assert.assertEquals("[A, B, C]", lSABC.toString());
		lSABC.drop(-4);
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test
	public void testBreakAllPairs(){
		lSABC.breakAllPairs("X");
		Assert.assertEquals("[A, X, B, X, C]", lSABC.toString());
	}
	
	@Test
	public void testBreakAllPairsEmpty(){
		lS.breakAllPairs("X");
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test
	public void testBreakAllPairsOneElement(){
		lS.addToFront("A");
		lS.breakAllPairs("X");
		Assert.assertEquals("[A]", lS.toString());
	}
	
	@Test
	public void testIterator(){
		Iterator<String> it = lSABC.iterator();
		
		Assert.assertEquals("A", it.next());
		Assert.assertEquals("B", it.next());
		Assert.assertEquals("C", it.next());
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test
	public void testIteratorEmpty(){
		Iterator<String> it = lS.iterator();
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorEmptyException(){
		Iterator<String> it = lS.iterator();
		it.next();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testIteratorRemoveException(){
		Iterator<String> it = lS.iterator();
		it.remove();
	}
	
	@Test
	public void testReverseIterator(){
		Iterator<String> it = lSABC.reverseIterator();
		
		Assert.assertEquals("C", it.next());
		Assert.assertEquals("B", it.next());
		Assert.assertEquals("A", it.next());
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test
	public void testReverseIteratorEmpty(){
		Iterator<String> it = lS.reverseIterator();
		
		Assert.assertFalse(it.hasNext());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testReverseIteratorEmptyException(){
		Iterator<String> it = lS.reverseIterator();
		it.next();
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testReverseIteratorRemoveException(){
		Iterator<String> it = lS.reverseIterator();
		it.remove();
	}
	
	@Test
	public void testDropToListPositive(){
		Assert.assertEquals("[A, B]", lSABC.dropToList(2).toString());
		Assert.assertEquals("[C]", lSABC.toString());
	}
	
	@Test
	public void testDropToListPositiveMoreIndex(){
		Assert.assertEquals("[A, B, C]", lSABC.dropToList(4).toString());
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test
	public void testDropToListNegative(){
		Assert.assertEquals("[B, C]", lSABC.dropToList(-2).toString());
		Assert.assertEquals("[A]", lSABC.toString());
	}
	
	@Test
	public void testDropToListNegativeMoreIndex(){
		Assert.assertEquals("[A, B, C]", lSABC.dropToList(-4).toString());
		Assert.assertEquals("[]", lSABC.toString());
	}
	
	@Test
	public void testDropToListZero(){
		Assert.assertEquals("[]", lSABC.dropToList(0).toString());
		Assert.assertEquals("[A, B, C]", lSABC.toString());
	}
	
	@Test
	public void testDropToListEmpty(){
		Assert.assertEquals("[]", lS.dropToList(1).toString());
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test
	public void testDropToListEmptyZero(){
		Assert.assertEquals("[]", lS.dropToList(0).toString());
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test
	public void testPalindrome(){
		Assert.assertFalse(lSABC.palindrome());
		Assert.assertTrue(lS.palindrome());
	}
	
	@Test
	public void testPalindrome2(){
		DoubleLinkedListImpl<String> pal = new DoubleLinkedListImpl<String>("A", "A");
		Assert.assertTrue(pal.palindrome());
		pal.addToRear("B");
		pal.addToRear("B");
		pal.addToRear("C");
		pal.addToRear("B");
		pal.addToRear("B");
		pal.addToRear("A");
		pal.addToRear("A");
		Assert.assertTrue(pal.palindrome());
	}
	
	@Test
	public void testPalindrome3(){
		DoubleLinkedListImpl<String> pal = new DoubleLinkedListImpl<String>("A", "A");
		Assert.assertTrue(pal.palindrome());
		pal.addToRear("B");
		pal.addToRear("B");
		pal.addToRear("C");
		pal.addToRear("X");
		pal.addToRear("B");
		pal.addToRear("A");
		pal.addToRear("A");
		Assert.assertFalse(pal.palindrome());
	}
	
	@Test
	public void testFlipHalvesOdd(){
		lSABC.flipHalves();
		Assert.assertEquals("[A, B, C]", lSABC.toString());
	}
	
	@Test
	public void testFlipHalvesPair(){
		lSABC.addToRear("D");
		lSABC.flipHalves();
		Assert.assertEquals("[C, D, A, B]", lSABC.toString());
	}
	
	@Test
	public void testFlipHalves2(){
		DoubleLinkedListImpl<String> lS1 = new DoubleLinkedListImpl<String>("A", "B", "C", "C", "B", "A");
		lS1.flipHalves();
		Assert.assertEquals("[C, B, A, A, B, C]", lS1.toString());
	}
	
	@Test
	public void testFlipHalvesOnly2Elements(){
		DoubleLinkedListImpl<String> lS1 = new DoubleLinkedListImpl<String>("A", "A");
		lS1.flipHalves();
		Assert.assertEquals("[A, A]", lS1.toString());
	}
	
	@Test
	public void testFlipHalvesOnlyOne(){
		DoubleLinkedListImpl<String> lS1 = new DoubleLinkedListImpl<String>("A");
		lS1.flipHalves();
		Assert.assertEquals("[A]", lS1.toString());
	}
	
	@Test
	public void testMaxPrefix(){
		Assert.assertEquals(3, lSABC.maxPrefix(new DoubleLinkedListImpl<String>("A", "B", "C")));
	}
	
	@Test
	public void testMaxPrefix2(){
		Assert.assertEquals(1, lSABC.maxPrefix(new DoubleLinkedListImpl<String>("C", "B", "A")));
	}
	
	@Test
	public void testMaxPrefixNoContain(){
		Assert.assertEquals(0, lSABC.maxPrefix(new DoubleLinkedListImpl<String>("X", "B", "A")));
	}
	
	@Test
	public void testMaxPrefixLargerThanList(){
		Assert.assertEquals(3, lSABC.maxPrefix(new DoubleLinkedListImpl<String>("A", "B", "C", "X")));
	}
}
