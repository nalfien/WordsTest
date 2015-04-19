package wordstest;

import static org.junit.Assert.*;

import org.junit.Test;

public class WordsTestCase {

	@Test
	public void testProcessWordEmpty() {
		WordsTest.clearAll();
		WordsTest.processWord("");
		assertEquals(WordsTest.getSequences().size(), 0);
		assertEquals(WordsTest.getDupes().size(), 0);
	}
	
	@Test
	public void testProcessWordSingleSequence() {
		WordsTest.clearAll();
		WordsTest.processWord("four");
		assertEquals(WordsTest.getSequences().size(), 1);
		assertEquals(WordsTest.getDupes().size(), 0);
	}
	
	@Test
	public void testProcessWordTwoSeqOneWord() {
		WordsTest.clearAll();
		WordsTest.processWord("sixty");
		assertEquals(WordsTest.getSequences().size(), 2);
		assertEquals(WordsTest.getDupes().size(), 0);
	}
	
	@Test
	public void testProcessWordDupe() {
		WordsTest.clearAll();
		WordsTest.processWord("four");
		WordsTest.processWord("four");
		
		assertEquals(WordsTest.getSequences().size(), 0);
		assertEquals(WordsTest.getDupes().size(), 1);
	}
}
