import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.LinkedList;

public class CenterEmbeddings {

  private static final String[] transitive = {"knew", "chased", "liked", "loved", "saw"};
  private static final String[] intransitive = {"snored", "laughed", "ran"};
  private static final String[] ignored = {"the", "a", "that"};
  
  private static class Relation {
    
    String predicate; 
    String subject; 
    String object;
    
    public Relation(String predicate, String subject, String object) {
      this.predicate = predicate; 
      this.subject = subject; 
      this.object = object;
    }
    
    public String toString(){
      
      if (object != null) {
        return "(Pred: " + predicate+ ", Subj: " + subject +", Obj: " + object +")";
      } else {
        return "(Pred: " + predicate + ", Subj: " + subject+")";
      }
    }
    
  }

  public static String typeOfWord(String word) {
    for(String ignoredVerb: ignored) {
      if(word.equals(ignoredVerb)) {
        return "ignored";
      }
    }

    for(String transitiveVerb: transitive) {
      if(word.equals(transitiveVerb)) {
        return "transitive";
      }
    }

    for(String intransitiveVerb: intransitive) {
      if(word.equals(intransitiveVerb)) {
        return "intransitive";
      }
    }

    return "noun"; // when we exhausted all verb options,
                   // the only option left is a noun
  }
  
  public static List<Relation> parseSentence(String sentence) throws IllegalArgumentException {
    
    List<Relation> result = new LinkedList<>();
    LinkedList<String> wordStack = new LinkedList<>(); // push -> void addLast(E e), pop -> , isEmpty, peek

    if(sentence.length() == 0) {
      return result;
    }

    final String splitSymbol = " ";
    String[] wordsInSentence = sentence.split(splitSymbol, -1); // we're trusting the user not to use
                                                                // multiple ways of splitting here

    String predicate = ""; // helper variables
    String subject = "";
    String object = "";

    for (String currentWord : wordsInSentence) {
        switch(typeOfWord(currentWord)) {
          case "ignored":
            break; // equivalent to "continue"

          case "transitive":
            predicate = currentWord;

            if (wordStack.isEmpty()) {
              throw new IllegalArgumentException("Verb found before a noun.");
            }

            subject = wordStack.pop();
            switch (typeOfWord(subject)) {
              case "transitive", "intransitive" -> throw new IllegalArgumentException("There are too many verbs");
            }
            if (wordStack.isEmpty()) {
                wordStack.push(subject);
                wordStack.push(predicate);
            } else {
              object = wordStack.peek();
              switch (typeOfWord(object)) {
                case "transitive", "intransitive" -> throw new IllegalArgumentException("There are too many verbs");
              }
              result.add(new Relation(predicate, subject, object));
            }
            break;

          case "intransitive":
            predicate = currentWord;

            if (wordStack.isEmpty()) {
              throw new IllegalArgumentException("Verb found before a noun.");
            }

            subject = wordStack.pop();
            switch (typeOfWord(subject)) {
              case "transitive", "intransitive" -> throw new IllegalArgumentException("There are too many verbs");
              case "noun" -> result.add(new Relation(predicate, subject, null));
              default -> throw new IllegalArgumentException("A function returned an invalid response");
            }

            if (!wordStack.isEmpty()) {
              wordStack.push(subject);
            }
            break;

          case "noun":
            if(currentWord.length() > 0) {
            wordStack.push(currentWord);
            } else {
              throw new IllegalArgumentException("The input is invalid. It includes null values or several coupled spaces");
            }
            break;

          default:
            throw new IllegalArgumentException("A function returned an invalid response");
        }
      }

    // a final check in case the stack is not empty after the loop has finished
    if (!wordStack.isEmpty()) {
      object = wordStack.pop();
      switch (typeOfWord(object)) {
        case "transitive", "intransitive" -> throw new IllegalArgumentException("There are too many verbs");
      }
      if (wordStack.isEmpty()) {
        throw new IllegalArgumentException("Cannot form a Relation.");
      }

      predicate = wordStack.pop();
      switch (typeOfWord(predicate)) {
        case "transitive", "intransitive" -> {
          if (wordStack.isEmpty()) {
            throw new IllegalArgumentException("Cannot form a Relation.");
          }
          subject = wordStack.pop();
          switch (typeOfWord(subject)) {
            case "transitive", "intransitive" -> throw new IllegalArgumentException("There are too many verbs");
          }
          result.add(new Relation(predicate, subject, object));
        }
        default -> throw new IllegalArgumentException("Cannot form a relation. Missing a verb.");
      }
    }

    return result;
  }
    

  public static void main(String[] args) {

    Map<String, String> testsMap  = new TreeMap<>() {
      {
        put("test1", "the child laughed");
        put("test2", "the child that the the woman loved laughed");
        put("test3", "the child that the the woman that the man knew loved laughed");
        put("test4", "the child saw the cat"); // the main verb can be transitive
        put("test5", "the child that the man knew saw the cat"); // relative clause in the subject of a transitive sentence
        put("test6", "the child saw the cat that the man loved"); // (challenge: relative clause in the object of a transitive sentence)
      }
    };

    for (Map.Entry<String,String> entry : testsMap.entrySet()) {
      System.out.println("Test number: " + entry.getKey() + ", test = " + entry.getValue());

      List<Relation> result = parseSentence(entry.getValue());
      for (Relation r : result) {
        System.out.println(r);
      }

      System.out.println();
    }
  }
}
