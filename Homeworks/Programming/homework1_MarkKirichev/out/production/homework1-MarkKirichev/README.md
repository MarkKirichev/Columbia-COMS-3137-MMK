# COMS W3137 Spring 22 - Programming Assignment 1  (100 pts)
Due: Wed Feb 9, 11:59pm

Please make sure to use `git add` to add all modified files, followed by `git commit`. Then use `git push` to push your files to your remote respository on Github. 
Double check through the Github web interface that your changes were submitted correctly.  

## Problem 1 - Recursive Binary Search - 14 points

In the file `BinarySearch.java` implement the method `public static <T extends Comparable<T>> int binarySearch(T[] arr, T x)`. This method should run a **recursive** binary search to find a value equal to `x`. Hint: The public `binarySearch` method, itself, should not be recursive. Instead, write an additional private helper method with additional parameters. This private helper method should be called from the public `binarySearch` method. This must run in log n time (where n is the length of the input array). If the value is not found in the array, return -1. Else, return the index in the array where the value was found.

To test your code, you may compile and run `BinarySearchTester.java`. This tester class uses the types defined in the package `shapes`, which includes an interface `Shape` and concrete classes `Rectangle`, `Square`, and `Circle`. The `Shape` interface extends the `Comparable` interface, which means that the concrete classes all need to have a compareTo method. In this case, the different shapes are compared according to their **area**. Take a look at the code for these classes to make sure you understand how everything works. There is nothing you need to change in this package -- it's only here to test the BinarySearch class. 

## Problem 2 - Linked List Operations flipPairs- 23 points 
The file `LinkedList.java` contains a doubly linked list, essentially identical to the implementation discussed in class.

Your task is to complete the method `void flipPairs()`, which should flip each adjacent pair of elements in the linked list.
For example, assume you have a `LinkedList<String>`

```
"A" "B" "C" "D" "E"
```

after calling `flipPairs()` on the list, the list should be changed to 

```
"B" "A" "D" "C" "E"
```

If the list contains an odd number of elements, the extra element in the end should stay in place unchanged (element "E" in the example).

The method should modify the list data structure by changing the `next` and `prev` references in the linked list nodes. 

You can test your method by running the main method of LinkedList, which should print out a list in the original, and then with the elements swapped as ilustrated in the example. 

You may also want to verify that both the previous and next pointers of your result list are set correctly.     

## Problem 3 - Linked List Operations reverse - 23 pts
Reconsider the list implementation in `LinkedList.java`. 

Complete the method `void reverse()`, which should modify the linked list in place, such that the elements are reversed. 

The method should modify the list data structure by changing the next and prev references in the linked list nodes.

You can test your method by running the main method of LinkedList, which should print out a list in the original, and then in reversed order.

You may also want to verify that both the previous and next pointers of your result list are set correctly.     


## Problem 4 - Scala: Get and Insert on Immutable Lists - 20 pts

Take a look at the file `InsertTest.scala`. Complete the two function templates:  

* The first function, `get` has the following signature:
```
    def get[A](l : List[A], k : Int) : A = {
    }
```
This function should return the element at index k. 
* The second function has the signature
```
def insert[A](l : List[A], x : A, k : Int) : List[A]= {
}
```
This function should return a new list, with the element x inserted at index k of the list l.

Do not use vars in your code, only vals. Both functions should use recursion. You do not have to worry about error handling at this point. 


## Problem 5 - Range Iterable - 20 pts 

In Java, we typically iterate over a sequence of integers as follows:

```
for (int i=0; i<10; i = i + 2) {
  ...
}
```

i.e you define the initializer `int i=0`, termination condition `i<10`, and update `i = i+2`. Suppose we use a version of Java that *only* supports enhanced for loops (aka for-each loops), i.e. it only allows you to iterate through the elements of an Iterable (N.B.: in Python, all for-loops are for-each loops). In order to still be able to iterate over a sequence of numbers, you need to create such an Iterable, called `Range`.

In the file `Range.java` modify the class so that it can be used as illustrated in the following example: 
```
            for(Integer j : new Range(1,8,1)) {
             System.out.print(j);
            }
            // Prints the sequence 1 2 3 4 5 6 7


            for(Integer j : new Range(1,8,2)) {
             System.out.print(j);
            }
            // Prints the sequence 1 3 5 7

            for(Integer j : new Range(1,8)) { 
             System.out.print(j);
            }
            // Prints the sequence 1 2 3 4 5 6 7

            for(Integer j : new Range(8,0,-1)) {
             System.out.print(j);
            }
            // Prints the sequence 8 7 6 5 4 3 2 1

```
  The arguments passed to the constructor of Range are `new Range(int start, int stop, int incr)`

* start - the first integer produced
* stop - iteration stops before stop is reached
* incr - the increment each time around the loop. Note that it should be possible to not specify the incr parameter, in which case this value should default to 1 (third example). Hint: The best way to implement this is to use two constructors (overloading constructors). Also note that incr can be negative as well (last example).

General hint: use an inner class for the Iterator that is returned by the iterator() method of Range. 
