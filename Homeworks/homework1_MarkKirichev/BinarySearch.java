public class BinarySearch {

  public static <T extends Comparable<T>> int binarySearch(T[] arr, T x) {
      final int left = 0;
      final int right = arr.length - 1;
      return binarySearch(arr, x, left, right);
  }

  private static <T extends Comparable<T>> int binarySearch(T[] arr, T x, int left, int right) {
      if (right >= left) {
          final int mid = left + ((right - left) >> 1);
          final int comparison = arr[mid].compareTo(x);

          if (comparison == 0) {
              return mid;
          } else if (comparison > 0) {
              return binarySearch(arr, x, left, mid - 1);
          }

          return binarySearch(arr, x, mid + 1, right);
      }
      return -1;
  }
}
