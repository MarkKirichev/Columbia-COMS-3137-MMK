# 3137_f22_homework0

Due: January 26, 2022, 11:59pm

Welcome Homework 0 for Honors Data Structures. The purpose of this assignment is to make sure your work environment is set up correctly and you know how to submit assignments on Github classrooms. Please follow the step-by-step instructions below. You will receive full credit for this assignment as long as you push your final version to Github. 

## Step 1: Installing Java, Scala, and Git
First, you need to make sure you have a working, recent Java (JDK >= version 11) and Scala (>= version 3) installation and a Git client. Please follow the instructions on the setup guide here: https://courseworks2.columbia.edu/courses/146212/pages/software-setup-guide

If you have trouble with the setup, please post on Ed or ask a TA or the instructor during office hours. 

## Step 2: GitHub Classrooms

Git is a popular version control system, a piece of software that keeps track of changes made to code. If the same code is modified by multiple developers, Git is able to detect, and in many cases resolve, conflicts between the different versions. Even though you can use Git locally, its particularly powerful when used with a remote repository, hosted on internet.

GitHub is a hosting service for Git repositories, that is widely used in academia, industry, and the open-source community. GitHub Classrooms is a tool that allows educators to distribute and manage programming assignments. The system will create an individual repository for each student, containing a copy of the template code. We will only discuss the very basics needed to work on homework assignments here. If you want to know more, there are many good resources for learning to use  Git and GitHub, including advanced concepts like branches and pull requests. As a starting point, take a look https://docs.github.com/en/get-started/using-git/about-git. The ultimate reference for using Git is the Git book: https://git-scm.com/book/en/v2

### 2.1 Creating a GitHub Account
You can skip this step if you already have a personal GitHub account. If not, you can create one at https://github.com/signup 
You do not have to use your Columbia email to set up this account. 

### 2.2 Linking your Account to the 3137 Classroom 
Once you have an account, make sure you are logged into GitHub. Then use this link to access the homework 0 assignment on GitHub: https://classroom.github.com/a/eyUtNaz6.
You will first be asked to link your GitGub account to the classroom ID (in this case, your Columbia UNI). IMPORTANT: If you can't find your UNI on the list, do not proceed and email me immediately at <bauer@cs.columbia.edu>.

Once you linked your personal GitHub account, GitHub Classrooms will create a new Github repository containing a copy of the homework template. You may have to reload the page to see it. Once you open the repository on Github, click on the green "Code" button, then select "https" and copy the URL. 

### 2.3 Cloning the Assignment Repository
Decide on a location in your filesystem to work on your 3137 programming assignments. On the terminal/command prompt, change into that directory using `cd`. Then run 

```$git clone [URL]``` 

where [URL] should be replaced with the URL for your homework0 github repository (that you copied in the previous step -- paste it here). Git will fetch a copy of the repository from Github and create a new directory, which will be called 3137_f22_homework0-[your_github_username]. `cd` into that directory. This will be your workspace. 

## Step 3: Compiling and running a program. 

You will notice a file called `HelloWorld.java` in your workspace. Open it using your preferred editor or IDE. 
Next, from the terminal type:

```$javac HelloWorld.java```

This will compile your code down to Java byte code that can be executed by the computer (on the Java virtual Machine).  From the terminal type:

```ls```

and you will see an additional file called `HelloWorld.class`.  This is your byte code. To execute it, type

```java HelloWorld```


## Step 4: Commiting and pushing changes. 
Modify the Java file slightly (adding a comment is sufficient) and save it. Compile and run again to make sure it still works (never commit code that doesn't compile). Then run 

```
git add HelloWorld.java
git commit -m "changelog message"
```

where the changelog message should describe what you changed, for example "added a comment". 
`git add` marks the file for inclusion in the next commit. 

Git commit saves the current version of the code in your workspace to your *local* repository. You will now have to push the content of your local repository to the remote Github repository. 

```git push```

Git will now ask you for your username and password. The username is your github username, but the password is ***not*** your regular GitHub password. To get the password, open GitHub in a browser, click on your avatar picture on the top-right, select "settings", then "Developer Settings". Click on "Personal access tokens". Click on "Generate New Token". You may have to re-enter your github password. Then enter a name for the token, for example "https_push_token". Set the expiration date to 90 days, and select the "repo" scope. Then scroll down and click "generate token". **Make sure you copy the token in the green box!**, you will not be able to view it again. This is your access token/password. 

Enter the token instead of the password when you called git push. You only have to do this the first time. Your code should now be pushed to Github. 

Navigate to https://github.com/ColumbiaCSDataStructures and then your homework0 repository. Make sure that the changes you made locally show up on Github. 

### Step 5: Creating a new program.

In your preferred editor or IDE, create a new file `GenericBinarySearch.java` and save it in your workspace for homework 0.  Copy (or retype) the Binary Search code discussed in class. Compile and run the program with a list of integers to make sure it works correctly.

Then create a `ComparablePerson.java` file and copy (or retype) the code discussed in class (this represents a person with a first name and last name, that implements the Comparable interface).

Finally, modify the main program in `GenericBinarySearch.java` so that it operates on a list of at least three ComparablePerson instances. 

Compile and run your program. You can compile multiple Java files in the same directory using `javac *.java`. Java will also automatically (re-)compile all imported classes when necessary. 

Next, you need to add the new files to your git repository. Then commit the changes to your repository. 

```
$ git add GenericBinarySearch.java ComparablePerson.java
$ git commit -m "Added GenericBinarySearch and ComparablePerson classes."
```
Finally push the changes to Github. 
```
$ git push
```

Double check that everything has been pushed correctly. Congratulations, you have finished homework 0!
