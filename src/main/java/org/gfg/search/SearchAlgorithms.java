package org.gfg.search;

/**
 * Contains implementation of a number of searching algorithms on collections and arrays.
 * @author Andrey Yemelyanov
 *
 */
public class SearchAlgorithms{
	
	/**
	 * <p>Searches a sorted array <tt>arr</tt> of {@link Comparable} instances for a specified <tt>key</tt>.</p>
	 * <p>The behavior is not specified if the supplied array is not sorted. If there are 
* several instances of the key present in the array, an index of any of the instances is returned.</p>
	 * 
	 * @param <T> type of the elements in the array and the key, must be of type {@link Comparable}
	 * @param arr Sorted array to be searched
	 * @param key Key to be searched for in the specified array
	 * @return an index at which the key is present, or -1 if the key is not found in the array
	 */
    public static <T extends Comparable<T>> int binarySearch(T[] arr, T key){
        int from = 0; int to = arr.length - 1;
        while(from <= to){
            int mid = from + (to - from) / 2;
            if(arr[mid].compareTo(key) == 0) return mid;
            if(arr[mid].compareTo(key) < 0) from = mid + 1;
            else to = mid - 1;
        }
        return -1;
    }

    /**
     * <p>Finds a peak in an array of integers. An array element is a peak if it
     * is not smaller than its immediate neighbors.
     * If an input array is sorted in a strictly increasing order, the last element is always a peak.
     * If an input array is sorted in a strictly decreasing order, the first element is always a peak.
     * If all input array elements are the same, any element is a peak.</p>
     * @param arr Input array
     * @return index of a peak element
     */
    public static int findPeak(int[] arr){
        int from = 0; int to = arr.length - 1;
        while(from <= to){
            int mid = from + (to - from) / 2;
            if((mid == 0 || arr[mid] >= arr[mid - 1]) && (mid == arr.length - 1 || arr[mid] >= arr[mid + 1])) return mid;
            else if(mid > 0 && arr[mid] < arr[mid - 1]) to = mid - 1;
            else from = mid + 1;
        }
        return 0;
    }
}