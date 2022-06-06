/**
 * Represent a tweet, including the content, author's username
 * and time when it was posted. 
 */
public class Tweet {

    public String user;
    public DateTime datetime;
    public String content;

    public Tweet(String user, DateTime datetime, String content) {
        this.user = user;
        this.datetime = datetime;
        this.content = content;
    }

    public String toString() {
        return "@"+this.user+" ["+datetime.toString()+"]: "+content;
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }

        Tweet other = (Tweet) obj;
        return user.equals(other.user)
                && datetime.equals(other.datetime)
                && content.equals(other.content);
    }

}