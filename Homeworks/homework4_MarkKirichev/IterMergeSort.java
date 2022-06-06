public class IterMergeSort {

        public static void merge(Integer[] arr, int aCtr, int bCtr, int rightEnd) {

            Integer[] tmp = new Integer[arr.length];
        
            int leftEnd = bCtr - 1;
            int cCtr = aCtr;
            int original_a = aCtr;
            int original_right_end = rightEnd;
          
            while (aCtr <= leftEnd && bCtr <= rightEnd) {
                if (arr[aCtr] <= arr[bCtr]) {
                    tmp[cCtr++] = arr[aCtr++];
                } else {
                tmp[cCtr++] = arr[bCtr++];
                }
            }
            while (aCtr <= leftEnd)
                tmp[cCtr++] = arr[aCtr++];
            while (bCtr <= rightEnd)
                tmp[cCtr++] = arr[bCtr++];
         
            // copy sorted subpartition back into arr
            for (int i = original_a; i <= original_right_end; i++) {
                arr[i] = tmp[i];
            }
        }

        public static void mergeSort(Integer[] inputArray) {
            if (inputArray == null) { // if we have an empty array there's nothing to sort
                return;
            }

            if (inputArray.length == 1) { // if we have an array with 1 element there's nothing to sort
                return;
            }

            if (inputArray.length == 2) { // here we only have to check if we need a single swap
                if (inputArray[0] > inputArray[1]) {
                    inputArray[0] = inputArray[0] + inputArray[1];
                    inputArray[1] = inputArray[0] - inputArray[1];
                    inputArray[1] = inputArray[1] - inputArray[0];
                }
                return;
            }

            if (inputArray.length > 2) {
                for (var width = 1; width < inputArray.length; width = 2 * width) {
                    for (int i = 0; i < inputArray.length - width; i = i + 2 * width) {
                        int left = i;   // start index of array 1
                        int middle = i + width; // start index of second array or the end index of first array
                        int end = Math.min(i + 2 * width - 1, inputArray.length);    // end of the second array

                        merge(inputArray, left, middle, end);
                    }
                }
            }
        }



        public static void main(String[] args) {
            
            Integer[] test = {1, 9, 2, 4, 7, 8, 5, 0, -1};
                
            mergeSort(test);
            
            for (Integer i : test) {
                System.out.print(i);
                System.out.print(" ");
            }
            System.out.println();
        }
}
