package spell;

import java.util.SortedSet;
import java.util.TreeSet;
import spell.ITrie.INode;

public class Trie implements ITrie {

	private Node root;
	private int nodeCount;
	private int wordCount;
	private SortedSet<String> prevWords;

	public Trie() {
		root = new Node();
		nodeCount = 1;
		wordCount = 0;
		prevWords = new TreeSet<String>();
	}

	public void add(String word) {
		prevWords.add(word);
		Node n = root;
		for (int i = 0; i < word.length(); i++) {
			if (n.nodeArray[word.charAt(i) - 'a'] == null) {
				n.nodeArray[word.charAt(i) - 'a'] = new Node();
				n.nodeArray[word.charAt(i) - 'a'].substr = word.substring(0, i + 1);
				nodeCount++;
				if (i == word.length() - 1) {
					n.nodeArray[word.charAt(i) - 'a'].substr = word.substring(0, i + 1);
					wordCount++;
					n.nodeArray[word.charAt(i) - 'a'].frequency++;
				}
				n = n.nodeArray[word.charAt(i) - 'a'];
			} else {
				if (i == word.length() - 1) {
					n.nodeArray[word.charAt(i) - 'a'].frequency++;
				} else {
					n = n.nodeArray[word.charAt(i) - 'a'];
				}
			}
		}
	}

	public Node find(String word) {
		Node n = root;
		for (int i = 0; i < word.length(); i++) {
			if (n.nodeArray[word.charAt(i) - 'a'] == null)
				return null;
			else {
				if (i == word.length() - 1) {
					if (n.nodeArray[word.charAt(i) - 'a'].frequency > 0) {
						return n.nodeArray[word.charAt(i) - 'a'];
					} else
						return null;
				}
				n = n.nodeArray[word.charAt(i) - 'a'];
			}
		}
		return null;
	}

	public int getWordCount() {
		return wordCount;
	}

	public int getNodeCount() {
		return nodeCount;
	}

	@Override
	public String toString() {
		String result = "";
		for (String word : prevWords) {
			//Node n = find(word);
			result += word + "\n";
		}
		return result;
	}

	@Override
	public int hashCode() {
		return wordCount * 12 + nodeCount * 17;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (this.getClass() != o.getClass()) {
			return false;
		} else {
			Trie current = (Trie) o;
			if (this.toString2().equals(current.toString2())) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	public String toString2() {
		String result = "";
		for (String word : prevWords) {
			Node n = find(word);
			result += word + " " + n.frequency + "\n";
		}
		return result;
	}

	public class Node implements ITrie.INode {
		int frequency;
		String substr;
		Node[] nodeArray = new Node[26];

		public int getValue() {
			return frequency;
		}
	}
}
