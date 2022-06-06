import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.TreeMap;

public class TweetDB {

    HashMap<String, List<Tweet>> tweetsPerUser;
    HashMap<String, List<Tweet>> tweetsPerKeyword;
    TreeMap<DateTime, List<Tweet>> tweetsByTime;

    public TweetDB(String pathToFile) throws FileNotFoundException, IOException {
        tweetsPerUser = new HashMap<>();
        tweetsPerKeyword = new HashMap<>();
        tweetsByTime = new TreeMap<>();

        BufferedReader br = new BufferedReader(new FileReader(pathToFile));
        CsvReader csv = new CsvReader(br);

        String[] fields = csv.nextLine();
        List<Tweet> listOfTweets = new ArrayList<>();

        while (fields != null) {
            Tweet tweetObject = new Tweet(
                    fields[0],
                    new DateTime(fields[2]),
                    fields[1]
            );
            listOfTweets.add(tweetObject);
            fields = csv.nextLine();
        }

        for (Tweet t : listOfTweets) {
            if (tweetsPerUser.containsKey(t.user)) {
                tweetsPerUser.get(t.user).add(t);
            } else {
                List<Tweet> actualTweetList = new LinkedList<>();

                actualTweetList.add(t);
                tweetsPerUser.put(t.user, actualTweetList);
            }

            String[] tokens = clean(t.content);
            for (String currentToken : tokens) {
                if (tweetsPerKeyword.containsKey(currentToken)) {
                    tweetsPerKeyword.get(currentToken).add(t);
                } else {
                    List<Tweet> actualTweetList = new LinkedList<>();
                    actualTweetList.add(t);
                    tweetsPerKeyword.put(currentToken, actualTweetList);
                }
            }

            DateTime dateTimeObject = t.datetime;
            if (tweetsByTime.containsKey(dateTimeObject)) {
                tweetsByTime.get(dateTimeObject).add(t);
            } else {
                List<Tweet> actualTweetList = new LinkedList<>();
                actualTweetList.add(t);
                tweetsByTime.put(dateTimeObject, actualTweetList);
            }
        }
    }

    private static String[] clean(String sentence) {
        String[] noSpaceArray = sentence.split(" ");
        String[] cleanedArray = new String[noSpaceArray.length];

        int i = 0;
        for (String x : noSpaceArray) {
            /**
             * check if each element of noSpaceArray is a noun or verb; if so, then add the
             * element to the cleanedArray
             **/
            if (!(x.equals("#") || x.equals("@") || x.equals("!") || x.equals("$") || x.equals("%") || x.equals("^")
                    || x.equals("&") || x.equals("*") || x.equals("(") || x.equals(")") || x.equals("-")
                    || x.equals(",") || x.equals(".") || x.equals("?") || x.equals(";"))) {
                cleanedArray[i] = x;
                i++;
            }
        }
        return cleanedArray;
    }

    public List<Tweet> getTweetsByUser(String user) {

        HashSet<Tweet> tweets = new HashSet<Tweet>();

        if (tweetsPerUser.containsKey(user)) {
            tweets.addAll(tweetsPerUser.get(user));
            return new ArrayList<Tweet>(tweets);
        }
        return null;
    }


    public List<Tweet> getTweetsByKeyword(String kw) {
        HashSet<Tweet> tweets = new HashSet<Tweet>();

        try {
            tweets.addAll(tweetsPerKeyword.get(kw));

            return new ArrayList<Tweet>(tweets);
        } catch (NullPointerException e) {
            throw new NoSuchElementException("Keyword Not Found.");
        }
    }

    public List<Tweet> getTweetsInRange(DateTime start, DateTime end) {
        Collection<List<Tweet>> collections = tweetsByTime.subMap(start, end).values();
        HashSet<Tweet> set = new HashSet<>();
        for (List<Tweet> w : collections) {
            for (Tweet t : w) {
                set.add(t);
            }
        }
        return new ArrayList<Tweet>(set);
    }

    public static void main(String[] args) {

        try {
            TweetDB tdb = new TweetDB("coachella_tweets.csv");

            // Part 1: Search by username
             for(Tweet t : tdb.getTweetsByUser("hannah_frog"))
                System.out.println(t);


            // Part 2: Search by keyword
            for(Tweet t : tdb.getTweetsByKeyword("music"))
                System.out.println(t);


            // Part 3: Search by date/time interval
            for(Tweet t : tdb.getTweetsInRange(new DateTime("1/6/15 00:00"), new DateTime("1/6/15 5:00")))
                System.out.println(t);


        } catch (FileNotFoundException e) {
            System.out.println(".csv File not found.");
        } catch (IOException e) {
            System.out.println("Error reading from .csv file.");
        }
    }
}