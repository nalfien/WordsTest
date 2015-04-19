package wordstest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class WordsTest {
	// Tree Map (for ordering) to contain sequences and source words
	private static TreeMap<String, String> sequences = new TreeMap<String, String>();
	
	// List for duplicates 
	private static List<String> dupes = new ArrayList<String>();
	
	public static void main(String[] args) {
		try {
			// Open the user-provided file.
			File dictFile = new File(args[0]);
			BufferedReader br = new BufferedReader(new FileReader(dictFile));
			String line;
			int wordCount = 0;
			
			// Process each line.
			while((line = br.readLine()) != null) {
				processWord(line);
				wordCount++;
			}
			
			br.close();
			
			// Get the path of the dictionary file.
			String filePath = dictFile.getAbsolutePath();
			filePath = filePath.substring(0, filePath.lastIndexOf(File.separator) + 1);
			
			// Create two new files: one for each four-letter sequence, one for each word.
			File seqFile = new File(filePath + "sequences.txt");
			File wordFile = new File(filePath + "words.txt");
			
			// Create the two files if they don't exist.
			if(!seqFile.exists()) {
				seqFile.createNewFile();
			}
			
			if(!wordFile.exists()) {
				wordFile.createNewFile();
			}
			
			// Create a writer for each file.
			BufferedWriter seqBw = new BufferedWriter(new FileWriter(seqFile.getAbsoluteFile()));
			BufferedWriter wordBw = new BufferedWriter(new FileWriter(wordFile.getAbsoluteFile()));
			
			// Write each sequence to a file and its corresponding word to a file.
			for(String key : sequences.keySet()) {
				seqBw.write(key + "\r\n");
				wordBw.write(sequences.get(key) + "\r\n");
			}
			
			// Output a final status message.
			System.out.println("Run complete.");
			System.out.println("Found " + sequences.size() + " sequences in " + wordCount + " words,\n" + 
					"with " + dupes.size() + " duplicate sequences found.");
			
			seqBw.close();
			wordBw.close();
		} catch (FileNotFoundException e) {
			System.out.println("Input file not found.");
		} catch (IOException e) {
			System.out.println("An error occurred reading from or writing to a file.");
		}
	}
	
	public static void processWord(String word) {
		// For each four-letter sequence,
		for(int i = 0; i + 4 <= word.length(); i++) {
			String testWord = (String) word.subSequence(i, i + 4);
			
			// Check if the sequence is in the duplicates list already, if not,
			if(!dupes.contains(testWord)) {
				// Check if the sequence is already in the sequences list,
				if(!sequences.containsKey(testWord)) {
					// If not, add it
					sequences.put(testWord, word);
				} else {
					// If the sequence is already in the list, add to the duplicates list,
					// remove from the sequence list, and add to the duplicates list.
					dupes.add(testWord);
					sequences.remove(testWord);
				}
			}
		}
	}
}
