object MergeSort {

    // a normal merge
    def mergeNormal(list1 : List[Int], list2 : List[Int]) : List[Int] = (list1, list2) match {
        case(Nil, _) => list2
        case(_, Nil) => list1
        case(head1::tail1, head2::tail2) =>
            if (head1 < head2) head1::mergeNormal(tail1, list2) else head2::mergeNormal(list1, tail2)
    }

    // a merge that is also tail-recursive BUT uses the slow ::: operation
    @scala.annotation.tailrec
    def mergeTailRec(list1: List[Int], list2: List[Int], acc: List[Int] = List()): List[Int] = (list1, list2) match {
        case (Nil, _) => acc ::: list2
        case (_, Nil) => acc ::: list1
        case (head1 :: tail1, head2 :: tail2) =>
            if (head1 < head2) mergeTailRec(tail1, list2, acc :+ head1) else mergeTailRec(list1, tail2, acc :+ head2)
    }

    // a micro-optimiztion here would be a creation of function
    // sort(list, listSize) that will contain the size of the list
    // since Scala will iterate through the list to get its size
    // which will not make the complexity larger than the merge operation
    // but will avoid traversing through the list every time its size is needed
    def sort(list : List[Int]) : List[Int] = list match {
        case Nil => list
        case head::Nil => list
        case _ => {
            val(leftList, rightList) = list.splitAt(list.length / 2)
            mergeTailRec(
                sort(leftList),
                sort(rightList)
            )
            /*
            mergeNormal(
                sort(leftList),
                sort(rightList)
            )
            */
        }
    }

    def main(args : Array[String]) : Unit = {
        println(
            sort(
                List(9, 5, 7, 3, 1, 4, 6, 2, 8, 0)
            )
        )
    }
}
