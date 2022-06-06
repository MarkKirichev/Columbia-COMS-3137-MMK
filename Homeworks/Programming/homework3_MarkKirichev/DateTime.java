/**
 * Represent a timestamp consisting of a date (day/month/year) and time 
 * in hours and minutes (24h format.
 */
public class DateTime implements Comparable<DateTime> {

    public int year;
    public int month;
    public int day;
    public int hours;
    public int minutes;
    public int seconds;
    public boolean pm;

    public DateTime(int year, int day, int month, int h, int m) {
        this.year = year;
        this.month = month;
        this.day = day;
        this.hours = h;
        this.minutes = m;
    }

    public DateTime(String datetime) {
        String[] fields = datetime.split(" ");
        String[] dateFields = fields[0].split("/");
        month = Integer.parseInt(dateFields[0]);
        day = Integer.parseInt(dateFields[1]);
        year = Integer.parseInt(dateFields[2]);

        String[] timeFields = fields[1].split(":");
        hours = Integer.parseInt(timeFields[0]);
        minutes = Integer.parseInt(timeFields[1]);;
    }

    public String toString() {
        return Integer.toString(month) + "/" +
                Integer.toString(day) + "/" +
                Integer.toString(year) + "  " +
                String.format("%02d", hours)+":"+String.format("%02d", minutes);
    }

    public int compareTo(DateTime o) {
        if (this.computeSeconds() > o.computeSeconds()) {
            return 1;
        } else if (this.computeSeconds() < o.computeSeconds()) {
            return -1;
        } else {
            return 0;
        }
    }

    public double computeSeconds() {
        final int daysInYear = 365;
        final int hoursInDay = 24;
        final int secondsInHour = 3600;
        final int secondsInMinute = 60;
        final int daysInMonth = 30;

        return this.year * daysInYear * hoursInDay * secondsInHour +
                this.month * daysInMonth * hoursInDay * secondsInHour +
                this.day * hoursInDay * secondsInHour +
                this.hours * secondsInHour +
                this.minutes * secondsInMinute +
                this.seconds;
    }

    @Override
    public int hashCode() {
        return (int)this.computeSeconds();
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
        DateTime other = (DateTime) obj;
        return this.computeSeconds() == other.computeSeconds();
    }

}