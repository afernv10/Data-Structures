package ule.edi.histogram;

import java.util.Iterator;
import java.util.NoSuchElementException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import ule.edi.EmptyCollectionException;

public class UnorderedArrayHistogramTests {
	
	private UnorderedHistogram<String> lS;
	
	@Before
	public void setupFixture() {
		
		this.lS = new UnorderedArrayHistogram<String>();
	}
	
	@Test
	public void testAdd() {
		lS.add("A");
		Assert.assertEquals("[A (1)]", lS.toString());
		lS.add("A");
		Assert.assertEquals("[A (2)]", lS.toString());
		lS.add("B");
		Assert.assertEquals("[B (1), A (2)]", lS.toString());
		lS.add("B");
		Assert.assertEquals("[B (2), A (2)]", lS.toString());
		lS.add("C");
		Assert.assertEquals("[C (1), B (2), A (2)]", lS.toString());
		lS.add("A");
		Assert.assertEquals("[C (1), B (2), A (3)]", lS.toString());
	}
	
	
	@Test
	public void testAddTimes(){
		lS.add("A", 1);
		Assert.assertEquals("[A (1)]", lS.toString());
		lS.add("A", 1);
		Assert.assertEquals("[A (2)]", lS.toString());
		lS.add("B", 1);
		Assert.assertEquals("[B (1), A (2)]", lS.toString());
		lS.add("B", 4);
		Assert.assertEquals("[B (5), A (2)]", lS.toString());
		lS.add("C", 1);
		Assert.assertEquals("[C (1), B (5), A (2)]", lS.toString());
		lS.add("A", 2);
		Assert.assertEquals("[C (1), B (5), A (4)]", lS.toString());
	}
	
	@Test
	public void testAddTimes0(){
		lS.add("A", 0);
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddNull() throws Exception {
		lS.add(null);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddTimesNull() throws Exception {
		lS.add(null, 3);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddTimesNegative() throws Exception {
		lS.add("A", -1);
	}
	
	@Test
	public void testIsEmptyTrue(){
		Assert.assertTrue(lS.isEmpty());
		Assert.assertEquals(0, lS.size());
	}
	
	@Test
	public void testIsEmptyFalse(){
		lS.add("A");
		Assert.assertFalse(lS.isEmpty());
		Assert.assertEquals(1, lS.size());
	}
	
	@Test
	public void testToStringEmpty(){
		Assert.assertTrue(lS.isEmpty());
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test
	public void testSizeZero(){
		Assert.assertEquals(0, lS.size());
	}
	
	@Test
	public void testSize(){
		lS.add("A", 3);
		lS.add("B", 4);
		Assert.assertEquals(7, lS.size());
	}
	
	@Test
	public void testCount(){
		lS.add("A", 3);
		lS.add("B", 2);
		lS.add("C", 5);
		Assert.assertEquals(3, lS.count("A"));
		Assert.assertEquals(2, lS.count("B"));
		Assert.assertEquals(5, lS.count("C"));
	}
	
	@Test
	public void testCountZero(){
		lS.add("A", 3);
		Assert.assertEquals(0, lS.count("B"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testCountNull() throws Exception {
		lS.count(null);
	}
	
	@Test
	public void testClear(){
		for(int i = 0; i<5; i++){
			lS.add(""+ i);
		}
		
		Assert.assertFalse(lS.isEmpty());
		lS.clear();
		Assert.assertTrue(lS.isEmpty());
	}
	
	@Test
	public void testClearEmpty(){
		lS.clear();
	}
	
	@Test
	public void testContains(){
		lS.add("A");
		Assert.assertTrue(lS.contains("A"));
		Assert.assertFalse(lS.contains("B"));
	}
	
	@Test
	public void testContainsNothing(){
		Assert.assertFalse(lS.contains("A"));
	}
	
	@Test(expected = NullPointerException.class)
	public void testContainsNull() throws Exception {
		lS.contains(null);
	}
	
	@Test
	public void testGetFirst() throws EmptyCollectionException{
		
		for(int i = 1; i <= 8; i++){
			lS.add("" + i);
		}
		
		Assert.assertEquals("8", lS.getFirst());
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testGetFirstEmpty() throws EmptyCollectionException{
		lS.getFirst();
	}
	
	@Test
	public void testGetLast() throws EmptyCollectionException{
		
		for(int i = 1; i <= 8; i++){
			lS.add("" + i);
		}
		
		Assert.assertEquals("1", lS.getLast());
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testGetLastEmpty() throws EmptyCollectionException{
		lS.getLast();
	}
	
	@Test
	public void testGetElementAt(){
		
		for(int i = 8; i>0; i--){
			lS.add("" + i);
		}
		
		Assert.assertEquals("[1 (1), 2 (1), 3 (1), 4 (1), 5 (1), 6 (1), 7 (1), 8 (1)]", lS.toString());
		Assert.assertEquals("1", lS.getElementAt(1));
		Assert.assertEquals("2", lS.getElementAt(2));
		Assert.assertEquals("3", lS.getElementAt(3));
		Assert.assertEquals("4", lS.getElementAt(4));
		Assert.assertEquals("5", lS.getElementAt(5));
		Assert.assertEquals("6", lS.getElementAt(6));
		Assert.assertEquals("7", lS.getElementAt(7));
		Assert.assertEquals("8", lS.getElementAt(8));
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetElementAtZeroPosition(){
		
		for(int i = 8; i>0; i--){
			lS.add("" + i);
		}
		
		lS.getElementAt(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetElementAtNegativePosition(){
		lS.getElementAt(-1);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testGetElementAtOutOfRange(){
		
		for(int i = 8; i>0; i--){
			lS.add("" + i);
		}
		
		lS.getElementAt(9);
	}
	
	@Test
	public void testAddToFront(){
		lS.addToFront("A");
		lS.addToFront("B");
		Assert.assertEquals("[B (1), A (1)]", lS.toString());
	}
	
	@Test
	public void testAddToFrontRepeat(){
		lS.addToFront("A");
		lS.addToFront("B");
		lS.addToFront("A");
		Assert.assertEquals("[B (1), A (2)]", lS.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddToFrontNull() throws Exception {
		lS.addToFront(null);
	}
	
	@Test
	public void testAddToFrontTimes(){
		lS.addToFront("A", 2);
		lS.addToFront("B", 5);
		Assert.assertEquals("[B (5), A (2)]", lS.toString());
	}
	
	@Test
	public void testAddToFrontTimesRepeat(){
		lS.addToFront("A", 2);
		lS.addToFront("B", 5);
		lS.addToFront("A", 4);
		Assert.assertEquals("[B (5), A (6)]", lS.toString());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddToFrontTimesNegative() throws Exception {
		lS.addToFront("A", -1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddToFrontTimesNull() throws Exception {
		lS.addToFront(null, 3);
	}
	
	@Test
	public void testAddToRear(){
		lS.addToRear("A");
		lS.addToRear("B");
		Assert.assertEquals("[A (1), B (1)]", lS.toString());
	}
	
	@Test
	public void testAddToRearSame(){
		lS.addToRear("A");
		lS.addToRear("B");
		Assert.assertEquals("[A (1), B (1)]", lS.toString());
		lS.addToRear("A");
		Assert.assertEquals("[A (2), B (1)]", lS.toString());
	}
	
	@Test
	public void testAddToRearExpand(){
		lS.addToRear("A");
		lS.addToRear("B");
		Assert.assertEquals("[A (1), B (1)]", lS.toString());
		lS.addToRear("C");
		Assert.assertEquals("[A (1), B (1), C (1)]", lS.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddToRearNull(){
		lS.addToRear(null);
	}
	
	@Test
	public void testAddToRearTimes(){
		lS.addToRear("A", 2);
		lS.addToRear("B", 8);
		Assert.assertEquals("[A (2), B (8)]", lS.toString());
		lS.addToRear("X", 3);
		Assert.assertEquals("[A (2), B (8), X (3)]", lS.toString());
		lS.addToRear("Z", 3);
		Assert.assertEquals("[A (2), B (8), X (3), Z (3)]", lS.toString());
	}
	
	@Test
	public void testAddToRearTimesSame(){
		lS.addToRear("A", 2);
		lS.addToRear("B", 8);
		lS.addToRear("A", 3);
		Assert.assertEquals("[A (5), B (8)]", lS.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testAddToRearTimesNull(){
		lS.addToRear(null, 2);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddToRearTimesNegative(){
		lS.addToRear("A", -1);
	}
	
	@Test
	public void testRemoveInstance(){
		lS.add("A", 3);
		lS.add("B", 2);
		lS.add("C", 1);
		Assert.assertEquals("A", lS.remove("A"));
		Assert.assertEquals("[C (1), B (2), A (2)]", lS.toString());
		Assert.assertEquals("B", lS.remove("B"));
		Assert.assertEquals("[C (1), B (1), A (2)]", lS.toString());
		Assert.assertEquals("C", lS.remove("C"));
		Assert.assertEquals("[B (1), A (2)]", lS.toString());
	}
	
	@Test
	public void testRemoveNoExist(){
		lS.add("A", 2);
		lS.add("B");
		Assert.assertNull(lS.remove("C"));
		Assert.assertEquals("[B (1), A (2)]", lS.toString());
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveNull(){
		lS.remove(null);
	}
	
	@Test
	public void testRemoveInEmpty(){
		Assert.assertNull(lS.remove("A"));
	}
	
	@Test
	public void testRemoveTimes(){
		lS.add("A", 6);
		lS.add("B", 4);
		lS.add("C", 3);
		Assert.assertEquals("A", lS.remove("A", 2));
		Assert.assertEquals("[C (3), B (4), A (4)]", lS.toString());
		Assert.assertEquals("A", lS.remove("A", 2));
		Assert.assertEquals("[C (3), B (4), A (2)]", lS.toString());
		Assert.assertEquals("A", lS.remove("A", 2));
		Assert.assertEquals("[C (3), B (4)]", lS.toString());
		Assert.assertEquals("B", lS.remove("B", 1));
		Assert.assertEquals("[C (3), B (3)]", lS.toString());
		Assert.assertEquals("B", lS.remove("B", 1));
		Assert.assertEquals("[C (3), B (2)]", lS.toString());
		Assert.assertEquals("C", lS.remove("C"));
		Assert.assertEquals("[C (2), B (2)]", lS.toString());
		Assert.assertEquals("C", lS.remove("C"));
		Assert.assertEquals("[C (1), B (2)]", lS.toString());
	}
	
	@Test
	public void testRemoveExtraFromFirst(){
		lS.add("A", 3);
		lS.addToRear("B", 2);
		lS.addToRear("C");
		Assert.assertEquals("A", lS.remove("A", 4));
		Assert.assertEquals("[B (2), C (1)]", lS.toString());
	}
	
	@Test
	public void testRemoveExtraFromLast(){
		lS.add("A", 3);
		lS.addToRear("B", 2);
		lS.addToRear("C");
		Assert.assertEquals("C", lS.remove("C", 3));
		Assert.assertEquals("[A (3), B (2)]", lS.toString());
	}
	
	@Test
	public void testRemoveExtraFromMiddle(){
		lS.add("A", 3);
		lS.addToRear("B", 2);
		lS.addToRear("C");
		Assert.assertEquals("B", lS.remove("B", 6));
		Assert.assertEquals("[A (3), C (1)]", lS.toString());
	}
	
	@Test
	public void testRemoveTimesZero(){
		lS.add("A", 4);
		Assert.assertNull(lS.remove("A", 0));
		Assert.assertEquals("[A (4)]", lS.toString());
	}
	
	@Test
	public void testRemoveTimesNoExist(){
		lS.add("A", 4);
		Assert.assertNull(lS.remove("B", 1));
		Assert.assertEquals("[A (4)]", lS.toString());
	}
	
	@Test
	public void testRemoveTimesEmpty(){
		Assert.assertNull(lS.remove("A", 9));
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveTimesNull(){
		lS.remove(null, 9);
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testRemoveTimesNegative(){
		lS.remove("A", -5);
	}
	
	@Test
	public void testRemoveElementAll(){
		lS.add("A", 2);
		lS.add("B");
		Assert.assertEquals("A", lS.removeElementAll("A"));
		Assert.assertEquals(1, lS.size());
	}
	
	@Test
	public void testRemoveElementAllAll() throws EmptyCollectionException{
		lS.add("A", 8);
		lS.addToRear("B", 3);
		lS.addToRear("C", 5);
		
		Assert.assertEquals("C", lS.removeElementAll("C"));
		Assert.assertEquals("[A (8), B (3)]", lS.toString());
		Assert.assertEquals("A", lS.removeElementAll("A"));
		Assert.assertEquals("[B (3)]", lS.toString());
		Assert.assertEquals("B", lS.removeElementAll("B"));
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test
	public void testRemoveElementAllNoExist(){
		lS.add("A", 2);
		lS.add("B");
		Assert.assertEquals(null, lS.removeElementAll("F"));
		Assert.assertEquals(3, lS.size());
	}
	
	@Test(expected = NullPointerException.class)
	public void testRemoveElementAllNull(){
		lS.removeElementAll(null);
	}
	
	@Test
	public void testRemoveAllFirst() throws EmptyCollectionException{
		lS.add("D", 2);
		lS.add("C", 3);
		lS.add("B", 2);
		lS.add("A", 5);
		Assert.assertEquals("A", lS.removeAllFirst());
		Assert.assertEquals("[B (2), C (3), D (2)]", lS.toString());
		Assert.assertEquals("B", lS.removeAllFirst());
		Assert.assertEquals("[C (3), D (2)]", lS.toString());
		Assert.assertEquals("C", lS.removeAllFirst());
		Assert.assertEquals("[D (2)]", lS.toString());
		Assert.assertEquals("D", lS.removeAllFirst());
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testRemoveAllFirstEmpty() throws EmptyCollectionException{
		lS.removeAllFirst();
	}
	
	@Test
	public void testRemoveAllLast() throws EmptyCollectionException{
		lS.add("A", 2);
		lS.addToRear("B", 5);
		lS.addToRear("C", 5);
		lS.addToRear("D", 5);
		Assert.assertEquals("D", lS.removeAllLast());
		Assert.assertEquals("[A (2), B (5), C (5)]", lS.toString());
		Assert.assertEquals("C", lS.removeAllLast());
		Assert.assertEquals("[A (2), B (5)]", lS.toString());
		Assert.assertEquals("B", lS.removeAllLast());
		Assert.assertEquals("[A (2)]", lS.toString());
		Assert.assertEquals("A", lS.removeAllLast());
		Assert.assertEquals("[]", lS.toString());
	}
	
	@Test(expected = EmptyCollectionException.class)
	public void testRemoveAllLastEmpty() throws EmptyCollectionException{
		lS.removeAllLast();
	}
	
	@Test
	public void testRemoveAllElementAt(){
		for(int i = 6; i > 0; i--){
			lS.add("" + i);
		}
		
		// 1 2 3 4 5 6
		Assert.assertEquals("2", lS.removeAllElementAt(2));
		Assert.assertEquals("[1 (1), 3 (1), 4 (1), 5 (1), 6 (1)]", lS.toString());
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveAllElementAtNegative(){
		lS.removeAllElementAt(0);
	}
	
	@Test(expected = IndexOutOfBoundsException.class)
	public void testRemoveAllElementAtOutOfRange(){
		// by default there arent elements in positions
		lS.removeAllElementAt(9);
	}
	
	@Test
	public void testIterator(){
		
		lS.add("A", 3);
		lS.addToRear("B", 2);
		lS.addToRear("C", 2);
		lS.addToRear("D", 1);
		
		Iterator<String> it = lS.iterator();
		
		Assert.assertEquals("A", it.next());
		Assert.assertEquals("A", it.next());
		Assert.assertEquals("A", it.next());
		Assert.assertEquals("B", it.next());
		Assert.assertEquals("B", it.next());
		Assert.assertEquals("C", it.next());
		Assert.assertEquals("C", it.next());
		Assert.assertEquals("D", it.next());
		
		Assert.assertFalse(it.hasNext());
		
	}
	
	@Test
	public void testIteratorHasNextTrue(){
		
		for(int i = 1; i <= 2; i++){
			lS.addToRear(""+i);
		}
		
		Iterator<String> it = lS.iterator();
		Assert.assertTrue(it.hasNext());
	}
	
	@Test
	public void testIteratorHasNextFalse(){
		Iterator<String> it = lS.iterator();
		Assert.assertFalse(it.hasNext());
	}
	
	@Test
	public void testIteratorNext(){
		lS.add("A");
		
		Iterator<String> it = lS.iterator();
		
		Assert.assertTrue(it.hasNext());
		Assert.assertEquals("A", it.next());
	}
	
	@Test(expected = NoSuchElementException.class)
	public void testIteratorNextException() throws Exception{
		Iterator<String> it = lS.iterator();
		
		Assert.assertFalse(it.hasNext());
		Assert.assertEquals("A", it.next());
	}
	
	@Test
	public void testIteratorNextMoreElements(){
		
		for(int i = 1; i <= 3; i++){
			lS.addToRear(""+i);
		}
		
		Iterator<String> it = lS.iterator();
		
		Assert.assertTrue(it.hasNext());
		Assert.assertEquals("1", it.next());
		Assert.assertEquals("2", it.next());
		Assert.assertEquals("3", it.next());
	}
	
	@Test(expected = UnsupportedOperationException.class)
	public void testIteratorRemove() throws Exception{
		lS.add("A");
		
		Iterator<String> it = lS.iterator();
		
		Assert.assertTrue(it.hasNext());
		Assert.assertEquals("A", it.next());
		it.remove();
	}
}