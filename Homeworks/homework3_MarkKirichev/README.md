# COMS W3137 Spring 22 - Programming Assignment 3 (100 pts)

Please make sure to use git add to add all modified files, followed by git commit. Then use git push to push your files to your remote respository on Github. Double check through the Github web interface that your changes were submitted correctly.

## Problem 1) Fun with BSTs as Sets (in Scala) (34 pts)

Recall that a BST represents a *Set* of keys. In this problem you will implement set operations on a BST implemented in Scala, including union and intersection. To implement these operations, you will use two primitive operations: *split* and *attach*.

The split method takes a BST as parameters, along with some value *k* (*k* may or may not be a key in the tree). It returns a pair of BSTs *t1* and *t2*, and a boolean *b*. All keys in *t1* must be less than *k*, and all keys in *t2* must be greater than *k*. It is possible that *t1* or *t2* are empty. The boolean *b* should be true if *k* appeared in the original tree and false otherwise. 

![The split operation](http://www.cs.columbia.edu/~bauer/split_operation.png)   
    
The *attach* method takes two BSTs *t1* and *t2* and parameters. It assumes that all keys in *t1* are less than any key in *t2* (note that this is true for the trees returned by the split method). The attach method returns a single BST in which *t1* has been attached to *t2* in the appropriate location.
  
![The attach operation](http://www.cs.columbia.edu/~bauer/attach_operation.png)   

Taken together, these two primitive methods are sufficient to implement *contains*, *delete*, *insert*, *union*, and *intersection*. 

Take a look at the file Bst.scala. The tree representation is slightly different from what we have seen so far. The class TreeNode represents a tree (storing Ints) with a single root and two (possibly empty) subtrees. The subtrees are of type *Option[TreeNode]*. Option has two subclasses that are case classes: *None*, and *Some(root)*, where root is of type TreeNode. In Scala, Options are often used to avoid the need for null return values. In our case, *None* represents an empty Tree. The TreeNode class contains a toString method that simply prints the in-order traversal of the tree (i.e. the elements in the set represented by the BST).  For example, the tree resulting from the attach operation in the example above would be displayed as  "{1 3 5 6 7 8 }". The printTree() method prints the tree as a nested tree structure. Calling it on the same tree would return "(6 (5 (3 1)) (8 7))". Empty left children (if any) are indicated using a *.

The companion object *Bst* contain methods that operate on the tree (I found that making these methods of the Bst singleton, rather than methods of TreeNode simplifies the problems conceptually). The three *tree* methods are factory methods to construct tree objects. For example, the initial tree *t* in the examples above could be constructed as tree(6, tree(3, tree(1), tree(5)), tree(8, tree(7))). To insert an empty left child, you would use *None* as a placeholder. The method *split* has already been provided.

* a) (6 pts) Complete the method *attach*, according to the explanation above. Hint: Recursively traverse t2 to find the attachment point for t1.
* b) (4 pts) Using only calls to split and/or attach (and no additional recursive calls), complete the method *contains(t, k)*, which should return true if *k* is in the tree *t* and false otherwise.
* c) (4 pts)  Using only calls to split and/or attach (and no additional recursive calls), complete the method *delete(t, k)*, which should return a new BST with *k* deleted from *t*.
* d) (4 pts) Using only calls to split and/or attach (and no additional recursive calls), complete the method *insert(t, k)*, which should return a new tree with *k* inserted into *t*.
* e) (8 pts) Using only calls to split and/or attach, complete the method *union(t1, t2)*, which should return a BST representing the union of the values in *t1* and *t2*. You will have to call union recursively. For example, if you have a BST representing {1 2 3 5} and another one representing {1 4 5 7} the union should be {1 2 3 4 5 7}.
* f)  (8 pts) Using only calls to split and/or attach, complete the method *intersection(t1, t2)*, which should return a BST representing the intersection of the values in *t1* and *t2*. You will have to call intersection recursively. For example, if you have a BST representing {1 2 3 5} and another one representing {1 4 5 7} the intersection should be {1 5}.

Using the printTree and toString methods, design appropriate test examples in the main method to illustrate that each method works correctly. 

## Problem 2 - Searching a Twitter Dataset (66 pts)

The file *coachella_tweets.csv* contains 3882 tweets discussing the Coachella 2015 music festival. This dataset was originally created
for the purpose of training and testing sentiment analysis models, identifying if a users view of the festival is positive or negative.
In this assignment, you will not work on sentiment, but instead use various Map and Set implementations in the Java standard library to index and query this database.

In the .csv file, each row contains one tweet. Each row has three fields (columns), separated by commas. The file CsvReader.java
already provides a class that reads in this format (described below), so you do not have to re-implement this. The fields are

* The username of the author.
* The content of the tweet.
* The date and time of the tweet, formatted as DD/MM/YY hh:mm, where the hour format is in a 24 hour format. For example “1/7/15 15:02” (which is 3:02pm).

The goal of this problem is to read the complete data into memory and index it using different maps. Specifically, you will index
the tweets using

* A [*java.util.HashMap*](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/HashMap.html) in which the keys are usernames (of type String). This allows you to efficiently find all tweets sent by a user.
* A java.util.HashMap in which the keys are keywords (of type String) appearing in the tweets, which allows you to efficiently find all tweets mentioning a specific term.
* A [*java.util.TreeMap*](https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/util/HashMap.html) in which the keys are dates and times (represented as instances of the class *DateTime*. The TreeMap allows you to find tweets by a specific date and time and also efficiently retrieve tweets **in a certain time range**. A TreeMap is a balanced Binary Search Tree. 

You may find it useful to look at the Java API reference for both data structures.

In all three maps, the values should be *Lists* containing instances of the class *Tweet*. The maps will be instance variables of the *TweetDB* class.

### Part 1 - Reading the CSVfile and Indexing by Username (18 pts)

The class *CsvReade*r contains code to read a CSV file. The constructor takes a *BufferedReader* as a parameter and opens the file.
The *CsvReader* instance then provides a *String[] nextLine()* method, which returns a String array of the individual fields in each row. The method returns null once the reader has reached the end of the file.

The class *Tweet* represents an individual tweet. The constructor takes the following parameters: username, content, and time stamp as a *DateTime* instance.
The *DateTime* class already comes with a constructor that parses the date and time in a string format, as described above. The dates/time string can be directly passed to the constructor of the DateTime class to create a corresponding DateTime instance. 

**TODO:** First, in the *TweetDB* class, complete the constructor. The parameter to the constructor is the pathname of a data file (such as "coachella_tweets.csv").
Using a *CsvReader*, read through the input file and convert each row into a Tweet instance.
Then, insert each Tweet into the *tweetsPerUser* hash map, indexed by the username. Recall that the values in the hash map should be Lists of Tweets (a user may have tweeted multiple times).
Finally, write the method. *public List<Tweet> getTweetsByUser(String user)* that returns all tweets written by a user. You can test the functionality in the main method, but the graders will call your methods from their own tester program. Note that the result may contain duplicates (because there are duplicates in the data set). 
    
### Part 2 - Indexing by Keyword (15 pts)

**TODO:** Update the constructor *TweetDB* so that each Tweet is additionally indexed by the the words that appear in the tweet content. You should add the tweets into the *tweetsPerKeyword* hashmap.
    
You may want to write an additional method for this, which removes punctuation and obtains keywords from the tweet. Do not spend a lot of time preprocessing the tweet text for this assignment. Simply stripping out common punctuation symbols and splitting by whitespace is sufficient.
    
Then, complete the method *public List<Tweet> getTweetsByKeyword(String keyword)*, which returns all tweets with a given keyword.
Again, you can test this functionality in the main method.
    
    
### Part 3 - Indexing by Date/Time (18 pts)

**TODO:** 
    
* Step 1) Update the constructor *TweetDB* so that each Tweet is additionally indexed by its *DateTime* object. You should add the tweets into the *tweetsByTime* tree map. To use the *DateTime* instances as keys, you need to modify that class to implement *Comparable*.

* Step 2) Write the method *public List<Tweet> getTweetsInRange(DateTime start, DateTime end)* that returns a list of all tweets between a start time (inclusive) and end time (exclusive). Use the [TreeMap.subMap](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/util/TreeMap.html#subMap(K,boolean,K,boolean)) method. This method allows you to get all the keys in a certain range on a BST. Again, you can test this functionality in the main method.
    
### Part 4 - Removing Duplicates (15 pts)

You may have noticed that the output of your various search method contains duplicates. To remove these duplicates, update your *getTweetsByUser*, *getTweetsByKeyword* and *getTweetsInRange* method in the following way. Before returning the results, insert all retrieved tweets into a *java.util.HashSet*. Then turn the set back into a list.
To make this work properly, you need to modify the *Tweet* and *DateTime* classes and override both *int hashCode()* and *boolean equals(Object other)*. Implement appropriate hash functions.
