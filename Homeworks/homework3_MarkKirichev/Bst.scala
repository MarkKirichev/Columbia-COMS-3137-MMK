class TreeNode (val data : Int,
                val left : Option[TreeNode],
                val right: Option[TreeNode]) {

    def printTree() : String = 
        (left, right) match {
            case (None, None) => data.toString;
            case (Some(l), None) => "(" + data.toString + " " + l.printTree +")";
            case (None, Some(r)) => "(" + data.toString + " * " + r.printTree +")";
            case (Some(l), Some(r)) => "(" + data.toString + " "+l.printTree + " " + r.printTree+")";
        }

    /** 
     * Print the set represented by this BST. 
     */ 
    override def toString() : String = "{" + inorder + "}"

    def inorder() : String = 
        (left, right) match {
            case (None, None) => data.toString 
            case (Some(l), None) => l.inorder + " " + data.toString 
            case (None, Some(r)) => data.toString + " " + r.inorder 
            case (Some(l), Some(r)) => l.inorder + " " + data.toString + " " + r.inorder 
        }

}

object Bst {
    /** 
     * Helper factory methods
     */  
    def tree(value : Int, left : Option[TreeNode], right : Option[TreeNode]) : Option[TreeNode]= {
        Some(new TreeNode(value, left, right));
    }
    
    def tree(value : Int, left : Option[TreeNode]) : Option[TreeNode] = {
        tree(value, left, None)
    }
    
    def tree(value : Int ) : Option[TreeNode] = {
        tree(value, None, None) 
    }

    /**
     * Returns two BSTs (A,B) such that A contains all keys less than x and B 
     * contains all keys greater than x. The additional boolean indicates if the value 
     * x appeared in the original tree or not. 
     */ 
    def split(tree: Option[TreeNode], x : Int) : (Option[TreeNode], Option[TreeNode], Boolean) = 
        tree match  {

            case None => (None, None, false)

            case Some(root) => {
                if (root.data == x)
                    (root.left, root.right, true)
                
                else if (x > root.data) {
                    val (rLeft, rRight, b) = split(root.right,x) 
                    (Some(new TreeNode(root.data, root.left, rLeft)), rRight, b | false)
                }
                else {
                    val (lLeft, lRight, b) = split(root.left, x)
                    (lLeft, Some(new TreeNode(root.data, lRight, root.right)), b | false)
                }
            }
        }

    /** 
     * Assume that all keys in t2 are greater than keys in t1. 
     * Attach t1 to t2 in the appropriate position. 
     */ 
    def attach(t1: Option[TreeNode], t2: Option[TreeNode]) : Option[TreeNode]= {
        t1 match {
            case None => t2
            case Some(root) => Some(
                new TreeNode(root.data, attach(root.left, t2), root.left)
            )
        }
    }

    /**
     * Return true if x is a key in the BST. 
     */ 
    def contains(tree : Option[TreeNode], x : Int) : Boolean = {
        val(_, _, isContained) = split(tree, x)
        isContained
    }

    /**
     * Return a new BST in which x has been deleted. 
     */
    def delete(tree : Option[TreeNode], x : Int)  : Option[TreeNode] = {
        val(t1, t2, _) = split(tree, x)
        attach(t1, t2)
    }

    /**
     * Return a new BST with x inserted. 
     */
    def insert(tree : Option[TreeNode], x : Int) : Option[TreeNode] = {
        if(contains(tree, x)) { // in the case when the element is already inside the set
            return tree
        }

        val(t1, t2, _) = split(tree, x)
        attach(
            attach(
                t1,
                Some(new TreeNode(x, None, None))
            ), t2
        )
    }

    /**
     * Return a new BST that represents the union of t1 and t2. 
     */ 
    def union(t1 : Option[TreeNode], t2 : Option[TreeNode]) : Option[TreeNode] = {
        t1 match {
            case None => t2
            case Some(root) => {
                val (firstSubtree_t2, secondSubtree_t2, _) = split(t2, root.data)
                Some(
                    new TreeNode(
                        root.data,
                        union(root.left, firstSubtree_t2),
                        union(root.right, secondSubtree_t2)
                    )
                )
            }
        }
    }
    
    /**
     * Return a new BST that represents the intersection of t1 and t2. 
     */ 
    def intersection(t1 : Option[TreeNode], t2 : Option[TreeNode]) : Option[TreeNode] = {
        t1 match {
            case None => t1
            case Some(root) => {
                val (firstSubtree_t2, secondSubtree_t2, isContained) = split(t2, root.data)
                if(isContained) {
                    Some(
                        new TreeNode(
                            root.data,
                            intersection(root.left, firstSubtree_t2),
                            intersection(root.right, secondSubtree_t2)
                        )
                    )
                } else {
                    attach(
                        intersection(root.left, firstSubtree_t2),
                        intersection(root.right, secondSubtree_t2)
                    )
                }
            }
        }
    }
      
    def main(args : Array[String]): Unit = {
        val t : Option[TreeNode] = tree(6, tree(3, tree(1), tree(5)), tree(8,tree(7))); 
        val (t1, t2, b) = split(t,4)
        println(t1.get);
        println(t1.get.printTree())
        println(t2.get);
        println(t2.get.printTree())

        val t3 = attach(t1, t2);
        println(t3.get.printTree());
        println(t3.get);
    }

}

