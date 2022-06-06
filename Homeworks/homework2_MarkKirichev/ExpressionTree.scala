import scala.collection.immutable.::

/**
 * A simple binary tree that stores strings. 
 * This is an abstract base class for the concrete case classes Node and Leaf.
 */
abstract class ExpressionTree {
 
    val content : String

    // methods for parts (b), (c), and (d)
    def postfix() : String = {
      def proc_leaf(s : String) : String = { s }
      def proc_node(left : String,
                    right: String,
                    content: String) : String = { left + right + content }
      this.traverse(proc_node, proc_leaf)
    }

    def infix() : String = {
      def proc_leaf(s: String) : String = { s }
      def proc_node(left: String,
                    right: String,
                    content: String) : String = { "(" + left + content + right + ")" }
      this.traverse(proc_node, proc_leaf)
    }

    def eval() : Double = {
      def proc_leaf(s: String) : Double = { s.toDouble }
      def proc_node(left: Double,
                    right: Double,
                    content: String) : Double = {
        content match {
          case "+" => return left + right
          case "-" => return left - right
          case "*" => return left * right
          case "/" => return left / right
          case _ => return 0 // in case of somebody trying to evaluate something illegal
        }
      }

      this.traverse(proc_node, proc_leaf)
    }

    /** 
     * A higher-order generalization for tree operations.
     *                   
     * This method implements tree traversal as an 
     * abstraction over different tree operations.
     * Tree operations can be implemented by creating function objects
     * proc_node and proc_leaf and passing them to traverse.
     * The abstract method is implemented in Node and Leaf. 
     */
    def traverse[A](proc_node: (A, A, String) => A, proc_leaf: String => A) : A
}

object ExpressionTree {

    def helperApply(currentList: List[String],
                    expressionTreeStack: List[ExpressionTree]) : ExpressionTree = {
        if(currentList.isEmpty) { // we've reached the bottom of the recursion
          return expressionTreeStack.head
        }

        currentList.head match {
          case "+" | "-" | "*" | "/" => return helperApply( // recursive call to handle the signs case
            currentList.tail,
            Node(currentList.head,
                 expressionTreeStack.tail.head,
                 expressionTreeStack.head) :: expressionTreeStack.tail.tail
            )
          case _ => return helperApply( // recursive call to handle the numbers case
            currentList.tail,
            Leaf(currentList.head) :: expressionTreeStack
          )
      }
    }

    // method for part (a)
    def apply(expression : String) : ExpressionTree = {
      val splitStringSequence = expression.split(" ")
      val myListOfExpressions = splitStringSequence.toList // our list of strings that will help us build the expr. tree
      val myExpressionTreeStack = List[ExpressionTree]() // initializing a helper stack

      return helperApply(myListOfExpressions, myExpressionTreeStack)
    }


    // You can run ExpressionTree to test your code.
    def main(args : Array[String]) {

        // Uncomment to test part (a)
        val tree : ExpressionTree = ExpressionTree("3 5 6 * + 7 -")
            
        // Uncomment to test part (b)
        println(tree.postfix())

        // Uncomment to test part (c)
        println(tree.infix())
    
        // Uncomment to test part(d) 
        println(tree.eval())
    }

}


/** 
 * A node with exactly two subtrees. 
 */ 
case class Node(val content: String,
                val left: ExpressionTree,
                val right: ExpressionTree) extends ExpressionTree {

     /** 
      * The traverse implementation for Node calls proc_node on the 
      * results returned by calling traverse recusively on each 
      * subtree and the content of this node. 
      */
    def traverse[A](proc_node : (A, A, String) => A, proc_leaf: String => A) =
            proc_node(left.traverse(proc_node, proc_leaf),
                      right.traverse(proc_node, proc_leaf),
                      content)
}

/** 
 * A node that does not have any further subtrees (i.e. a single leaf node).
 */
case class Leaf(val content : String) extends ExpressionTree {
    /** 
     * The traverse implementation for Leaf calls proc_laf on the content of
     * the node. proc_leaf usually just converts the content into the correct
     * result type.
     */
    def traverse[A](proc_node : (A, A, String) => A, proc_leaf: String => A) =
        proc_leaf(content)
}

