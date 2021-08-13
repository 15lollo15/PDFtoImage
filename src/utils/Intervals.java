package utils;

import java.util.ArrayList;
import java.util.List;

public class Intervals {

    private List<Interval> intervals;

    public Intervals() {
        intervals = new ArrayList<>();
    }

    public void addInterval(Interval newInterval) {
        for(Interval interval : intervals)
            if(interval.isIntersected(newInterval))
                return;
        intervals.add(newInterval);
    }

    public void removeInterval(Interval interval){
        intervals.remove(interval);
    }

    public boolean isInTheIntervals(int i) {
        for(Interval interval: intervals)
            if(interval.isInTheInterval(i))
                return true;
        return false;
    }

    public static Intervals getIntervalsFromString(String str) {
        Intervals intervals = new Intervals();
        String[] strIntervals = str.split(",");
        for(String strInterval : strIntervals)
            intervals.addInterval(Interval.getIntervalFromString(strInterval));
        return intervals;
    }

    @Override
    public String toString() {
        String str = "";
        for(int i = 0; i<intervals.size(); i++){
            str += intervals.get(i).toString();
            if(i != (intervals.size()-1))
                str += ",";
        }
        return str;
    }

    public int getIntervalsTotalSize(){
        int sum = 0;
        for(Interval i : intervals)
            sum += i.getIntervalSize();
        return sum;
    }
}
