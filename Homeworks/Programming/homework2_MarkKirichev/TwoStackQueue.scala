class TwoStackQueue[A](s1 : List[A], s2 : List[A]) {

  def this() = this(Nil, Nil) // auxiliary constructor

  def enqueue(x : A) : TwoStackQueue[A] = {
    new TwoStackQueue(x :: s1, s2);
  }

  // tail recursion
  def reverseList[A](list : List[A], result: List[A] = Nil) : List[A] = list match {
    case Nil => result; // returning the final reversed list if we exhausted all nodes
    case (x :: xs) => reverseList(xs, x :: result); // else we append the next node to result and continue
  }

  def dequeue() : (A, TwoStackQueue[A]) = {
    if(s2.isEmpty) {
        // checking edge case where s1 has only 1 element
        val reversedFirstStack = if(s1.tail == Nil) s1 else reverseList(s1); // reversing the list if more elements
        (reversedFirstStack.head, new TwoStackQueue(Nil, reversedFirstStack.tail)); // returning the wanted tuple
    } else {
      (s2.head, new TwoStackQueue(s1, s2.tail));
    }
  }

  // A better version, that works with Exceptions
  /**
   *  class EmptyQueueException(s: String) extends Exception(s) {}
   *
   *  class EmptyQueueExceptionInstance[A] {
   *    @throws(classOf[EmptyQueueException])
   *    def validate(list: List[A]): Unit = {
   *      if (list.isEmpty) {
   *        throw new EmptyQueueException("You cannot dequeue() an empty queue!")
   *      }
   *    }
   *  }
   *
   *
   * def dequeue() : Option[(A, TwoStackQueue[A])] = {
   *   if(s2.isEmpty) {
   *     var queueException = new EmptyQueueExceptionInstance[A]();
   *     try {
   *       queueException.validate(s1); // checking whether the Queue is empty
   *
   *       // checking edge case where s1 has only 1 element
   *       val reversedFirstStack = if(s1.tail == Nil) s1 else reverseList(s1); // reversing the list if more elements
   *       (reversedFirstStack.head, new TwoStackQueue(Nil, reversedFirstStack.tail)); // returning the wanted tuple
   *     } catch {
   *       case e: Exception => {
   *        println("Exception occurred : " + e);
   *        return Nil;
   *       }
   *     }
   *   } else {
   *     (s2.head, new TwoStackQueue(s1, s2.tail));
   *   }
   * }
   */
}


object TwoStackQueue {

  def main(args : Array[String]): Unit = {
      val q1  = new TwoStackQueue[Int]();
    
      val q2 = q1.enqueue(1);
      val q3 = q2.enqueue(2);

      
      val (x, q4) = q3.dequeue()
      println(x);  // Should print 1

      val q5 = q4.enqueue(3);

      val (y, q6) = q5.dequeue()
      println(y); // Should print 2

      val (z, q7) = q6.dequeue()
      println(z); // Should print 3
  }

}
