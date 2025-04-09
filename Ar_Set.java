package set;

public class Ar_Set {
	public static final int MAXSIZE = 100;
	protected int[] SetItems;
	protected int length;
	
	public Ar_Set() {
		SetItems = new int[MAXSIZE];
		length = 0;
	}
	
	public void store(int newItem) {
		int curind = 0;
		while(curind<length && newItem > SetItems[curind]) {
			curind++;
		}
		if (length == 0 || SetItems[curind] != newItem) {
			for (int pushind = length; pushind > curind; pushind--) {
				SetItems[pushind] = SetItems[pushind-1];
			}
			SetItems[curind] = newItem;
			length++;
		}
	}
	
	public void printSet() {
		System.out.print("{");
		for (int loc = 0; loc < length; loc++) {
			System.out.print(SetItems[loc]);
			if (loc < length - 1) {
				System.out.print(",");
			}
		}
		System.out.print("}");
	}
	
	public Ar_Set intersect(Ar_Set altSet) {
		Ar_Set resultSet = new Ar_Set();
		int s1 = 0, s2 = 0;
		while(s1 < length && s2 < altSet.length) {
			if (SetItems[s1] < altSet.SetItems[s2]) {
				s1++;
			}
			else if (SetItems[s1] > altSet.SetItems[s2]) {
				s2++;
			}
			else {
				resultSet.store(SetItems[s1]);
			}
		}
		return resultSet;
	}

}
