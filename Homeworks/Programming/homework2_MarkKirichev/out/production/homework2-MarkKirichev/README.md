# COMS W3137 Spring 22 - Programming Assignment 2  (100 pts)

Please make sure to use git add to add all modified files, followed by git commit. Then use git push to push your files to your remote respository on Github.
Double check through the Github web interface that your changes were submitted correctly.

## Problem 1 - Two Stack Queue (Scala) - 30 pts
In this problem you will implement a Banker's queue, as discussed in class, which uses two completely separate stacks, `s1` and `s2`. Enqueue operations happen by pushing the data on to `s1`. Dequeue operations are completed with a pop from `s2`. Recall that when `s2` is empty, the dequeue operation should first move all elements from `s1` to `s2` using a sequence of pop and push operations.  

Complete the class `TwoStackQueue` using two stacks. Implement the `enqueue` and `dequeue` method as described below: 

* `enqueue(x : A)` should return a new `TwoStackQueue` in which the new entry x has been pushed to `s1`. This is straightforward.
* `dequeue()` should return a *pair*, consisting of the dequeued element and a new `TwoStackQueue` with updated `s1` and `s2`. You will have to distinguish between the case when `s2` is empty, and when it is not. To implement moving the entries from `s1` to `s2`, you can either write an additional recursive method that returns the new `s2`, or you can use `foldLeft`.

Note that for the stacks we use immutable lists. So pushing to the stack is accomplished by prepending the new element (`stack.push(x)` becomes `x :: stack`). Pop is implemented by taking `stack.head`. The remaining stack is then `stack.tail`.

**Important** Do not use any *var*s, mutable data structures, or loops in your code. 

You may use the main method in `TwoStackQueue` to test your code. You do not have to worry about error handling (for example, when trying to dequeue from an empty queue). 


## Problem 2 - Extracting Relations from English Sentences (Java) - 40 points

Nested structures are a hallmark of natural languages (as well as programming languages).
To correctly analyze the grammatical structure of natural language expressions, and ultimate figure out semantic relations (‘who does what to whom?’)
we must be able to model such nested structures. Most computational linguists believe that this can be done using a stack.

We will consider a fragment of English illustrating a phenomenon called center embeddings. Consider the sentence “The child that the woman that the man knew loved laughed”.

This sentence contains two nested relative clauses. Such sentences are difficult to understand, especially for non-native speakers, but they are perfectly grammatical.
To get a clearer understanding, we can start with the subject and predicate and then add the relative clauses one by one.

    the child laughed
    the child [that the the woman loved] laughed
    the child [that the woman [that the man knew] loved] laughed
    the child [that the woman [that the man […] knew] loved] laughed

The relative clauses are embedded in between the subject (child) and the predicate (laughed). In principle, an arbitrary number of nested clauses can be embedded this way, but each new phrase one pushes the subject further to the left and the predicate further to the right.

Our goal is to extract a set of relations from sentences of this type. Relations are either of the form (subject, predicate) or (subject, predicate, object). For the example sentence above, the following relations should be produced.

(man, knew, woman)
(woman, loved, child)
(child, laughed)

Note that laugh is an intransitive verb, so there is no object, while know and laugh are transitive verbs.

When processing a sentence word by word (left to right), we need to keep track of word that still need to be “attached” to the predicate. This can be done using a stack.

**TODO:**
Take a look at the class `CenterEmbeddings`. The class contains a nested class `Relation` to represent the relations described above. Each Relation has a predicate, a subject, and an object (passed to the constructor in this particular order). The object may be `null` if the predicate is an intransitive verb (see above).
The `toString` method prints the relation in the format illustrated above.

There are two static String arrays, listing transitive and intransitive verbs in the language.

Your task is to write the method `public static List<Relation> parseSentence(String sentence)`. This method should take a sentence as input (as a string) and return a list of `Relation` instances.

To separate the input string into tokens (words), you may use the [split](https://docs.oracle.com/javase/7/docs/api/java/lang/String.html#split(java.lang.String)) method of the String class.
    
Process the sentence left to right. Use a stack to keep track of subjects and objects. Create a new `Relation` when you encounter a verb. Make sure your program handles both transitive and intransitive verbs correctly. Think about what, if anything, needs to be pushed back onto the stack after processing a verb.
    
As a Stack implementation, please use `java.util.LinkedList`, limiting yourself to the methods `push`, `pop`, `isEmpty`, and `peek`.
    
The input contains articles (*the*, *a*) and the relative pronoun *that*. You can ignore these as you process the sentence. All other tokens are either verbs (in one of the two string arrays, as discussed earlier), or nouns. Assume that any token that is not an article, relative pronoun, or verb, is a noun. You can assume the input is lower-case and there is no punctuation.
    
The input sentences will follow the center embedding pattern illustrated above and will not have any other structure not shown. 
    
If the input cannot be processed, your method should throw an java.util.IllegalArgumentExceptionIllegalArgumentException.
An empty input string should result an an empty relation list.

Use the main method of the class `CenterEmbedding` to test your code. Several test sentences have been provided.
One added complication is that the main verb can be transitive, as illustrated in test 4, 5, and 6. In that case, a relative clause may appear in the subject, in the object, or both. Make sure you get test 4 and 5 to work. Test 6 is optional. 

For simplicity, you can assume that relative clauses only contain transitive verbs. So you will not encounter phrases like "the child that laughted". 

## Problem 3 - Expression Tree (Scala) - 30 pts

The file `ExpressionTree.scala` contains a class `ExpressionTree` that is used to represent simple binary trees storing strings. `ExpressionTree` is an abstract class that has two concrete subclasses `Node` and `Leaf`. Both are case classes. All `Node` instances contain the instance variables `left` and `right`, corresponding to the subtrees of this node. Leafs do not have any subtrees. All `ExpressionTree` instance (Nodes or Leafs) also have a `content` instance variable of type String. A generic version of this tree representation will be discussed in class.
The companion objects to `Leaf` and `Node` define `apply` methods that make it easy to construct binary trees like this: 

```
   val tree : ExpressionTree = 
              Node ("-", Node ("+", Leaf ("3"), 
                                    Node ("*", Leaf ("5"), 
                                               Leaf ("6"))),
                         Leaf ("7"))
```
which corresponds to the following tree: 

```
          - 
        /   \
       +     7
      / \
     3   *
        / \
       5   6
```


* (a - 10 pts) write the `apply(expression : String) : ExpressionTree` method of the `ExpressionTree` companion object. The method should take an arithmetic expression in postfix representation as a parameter and should return a new `ExpressionTree` instance for the corresponding expression tree. Implement the stack-based algorithm for constructing expression trees that was discussed in class. Use immutable lists as a stack. You may also use https://www.scala-lang.org/api/2.12.1/scala/collection/immutable/Stack.html. For convenience, you may use a *var* to refer to the stack and a for loop to iterate through the input tokens (for an added challenge, implement this algorithm recursively without use of a *var*). If implemented correctly, you should be able to create a new expression tree like this:

```
val tree : ExpressionTree = ExpressionTree("3 5 6 * + 7 -")
```

Assume the individual tokens (operators and operands) are separate by a single whitespace. Hint: you can use `.split(" ")` to split the expression string into tokens.


* (b - 6 pts) Many tree operations can be implemented as tree traversals. In functional programming languages, the steps of the traversal can be be separated from the computation of the result. The steps of the traversal are already implemented in the method `traverse[A](proc_node, proc_leaf)`. This method is required by the abstract class `ExpressionTree` and then implemented differently in `Node` and `Leaf`. `proc_leaf` and `proc_node` are parameters that expect first class function objects as arguments. 

On `Leaf` instances, the `traverse` method calls `proc_leaf`, which must be a function of type `String => A`, mapping the content of the leaf to some result of type `A`. `A` is the type of the result of the tree operation. For example, for retrieving a postfix representation of the tree, the type should be `String` and for evaluating the tree, the type should be `Double`. 

On `Node` instances, the `traverse` methjod calls the function objects passed to the parameter `proc_node`, which is of type `(A, A, String) => A`. The three parameters are 1) the result of recursively calling traverse on the left subtree 2) the result of calling traverse on the right subtree and 3) the content of the current node. 
*TODO:* Implement the method `postfix() : String` in the class `ExpressionTree` that returns the postfix representation of the tree as a string. Create appropriate first class functions and pass them to `traverse`. Do not implement the steps of the traversal yourself.


* (c - 8 pts) Using the same technique as in part (b), implement the method `infix() : String` that returns an infix representation of the tree as a string. Make sure the string contains parentheses to make the expression unambiguous (it's okay if there are redundant parentheses).


* (d - 6 pts) Using the same technique as in part (b) and (c) Implement the method `eval() : Double` that returns the result of evaluating the expression stored in the expression tree.
