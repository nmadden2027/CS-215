package ht;

public class HashTable {
	public static final int CAPACITY = 100;
	private LL_UList[] hashChains;
	
	public HashTable() {
		hashChains = new LL_UList[CAPACITY];
	}
	
	private int hash(int key) {
		return key % CAPACITY;
	}
	
	public void putItem(int key) {
		int hashValue = hash(key);
		if(hashChains[hashValue] == null) {
			hashChains[hashValue] = new LL_UList();
		}
		hashChains[hashValue].putItem(key);
	}
	
	public void deleteItem(int key) throws Exception {
		int hashValue = hash(key);
		if(hashChains[hashValue] == null) {
			throw new Exception("Unable to delete");
		}
		hashChains[hashValue].deleteItem(key);
	}
	
	public int getItem(int key) {
		int hashValue = hash(key);
		if(hashChains[hashValue] == null) {
			return -1;
		}
		return hashChains[hashValue].getItem(key);
	}
	
	public int countkeys() {
		int totalkeys = 0;
		for (int i = 0; i < CAPACITY; i++) {
			if (hashChains[i] != null) {
				totalkeys = totalkeys + hashChains[i].getLength();
			}
		}
		return totalkeys;
	}
	
	public void printTable() {
		for(int i =0; i < CAPACITY; i++) {
			if(hashChains[i] == null) 
				System.out.println("()");
			else
				hashChains[i].printList();
		}
	}
}
