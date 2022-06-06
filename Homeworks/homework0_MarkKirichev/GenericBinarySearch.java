public class BinarySearch {
    
    public static int linearSearch(T[] a, T x) {
        for (int i = 0; i < a.length; ++i) {
	    if (a[i].equals(x)) {
	        return i;
            }
	}
	
	return -1;
    }
    
    public static <T extends Comparable<T>> int binarySearch(T[] a, T x) {
    	int low = 0;
	int high = a.length - 1;
	
	while(low <= high) {

	    int mid = (high + low) / 2;

	    if (a[mid] < x) {
	        low = mid + 1;
	    } else if (a[mid] > x) {
	    	high = mid - 1;
	    } else {
	    	return mid;
	    }
	}
	
	return -1;
    }
}

public static void main(String[] args) {
    
     Integer[] a = {
     	0, 5, 10, 13, 15, 23, 42, 217, 1024, 4929
     };

     Integer x = 1024;

     System.out.println(BinarySearch(a, x));
}
