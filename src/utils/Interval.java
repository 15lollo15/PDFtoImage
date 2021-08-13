package utils;

public class Interval {

    private int start;
    private int finish;


    public Interval(int start, int finish) {
        if(start > finish || start < 0 || finish < 0)
            throw new IllegalArgumentException();
        this.start = start;
        this.finish = finish;
    }

    public int getStart() {
        return start;
    }

    public int getFinish() {
        return finish;
    }

    public boolean isInTheInterval(int i) {
        return i >= start && i <= finish;
    }

    @Override
    public String toString() {
        String str = String.valueOf(start);
        if(finish != start)
            str += "-" + String.valueOf(finish);
        return str;
    }

    public boolean isIntersected(Interval i) {
        if(this.start >= i.start && this.start <= i.finish)
            return true;
        if(this.start >= i.finish && this.finish <= i.finish)
            return true;

        if(i.start >= this.start && i.start <= this.finish)
            return true;
        if(i.start >= this.finish && i.finish <= this.finish)
            return true;
        return false;
    }

    public int getIntervalSize(){
        return finish - start + 1;
    }

    public static Interval getIntervalFromString(String str) {
        String[] splitted = str.split("-");
        int start = Integer.valueOf(splitted[0]);
        int finish = start;
        if(splitted.length > 1)
            finish = Integer.valueOf(splitted[1]);
        return new Interval(start, finish);
    }


}
