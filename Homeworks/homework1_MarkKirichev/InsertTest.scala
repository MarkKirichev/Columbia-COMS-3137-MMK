import scala.annotation.tailrec

object InsertTest {
    // Write a recursive Scala function that returns the element at index k in a list.

    @tailrec
    def get[A](l : List[A], k : Int) : A = {
        // write this function. Do not use l.get(k) !
        if(k == 0) { l.head } else { get(l.tail, k - 1) } // uses k as a counter until we get to our wanted number
    }                                                     // and then retrieves it

    /**
     * Write a recursive Scala function that inserts an element x at index k into an
     * existing immutable Scala list and returns a new list. 
     */
    def insert[A](l : List[A], x : A, k : Int) : List[A] = {
        if(k == 0) { x :: l } else { l.head :: insert(l.tail, x, k - 1) } // uses k as a counter until we get to our
    }                                                                     // target while concurrently adding the elems
                                                                          // at the beginning and when we reach k = 0
                                                                          // we just add the remainder of the list
    def main(args: Array[String]): Unit = {

        val li : List[Integer] = List(1, 2, 3, 4, 5);

        println(get(li, 3)); // Should print 4

        val insertTestList: List[List[Any]] = List(
            insert(li, 7, 0), // Should print 7, 1, 2, 3, 4, 5
            insert(li, 7, 2), // Should print 1, 2, 7, 3, 4, 5
            insert(li, 7, 5), // Should print 1, 2, 3, 4, 5, 7
        )

        insertTestList.foreach(value => println(value))
    }
}