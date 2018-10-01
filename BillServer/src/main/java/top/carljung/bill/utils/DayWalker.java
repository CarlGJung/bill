package top.carljung.bill.utils;

import java.util.Calendar;

/**
 *
 * @author wangchao
 */
public class DayWalker {
    public static final long DAY_MILLIONS = 24 * 60 * 60 * 1000L;

    private long dayBegin = -1;
    private long dayEnd = -1;
    
    public long getDayEnd(){
        if (dayEnd == -1) {
            dayEnd = getTodayEndTime();
        }
        return this.dayEnd;
    }
    
    public long getDayBegin(){
        if (dayBegin == -1) {
            dayEnd = getDayEnd();
            dayBegin = dayEnd - DAY_MILLIONS + 1000;
        }
        return this.dayBegin;
    }
    
    public void next(){
        dayEnd = getDayEnd();
        dayBegin = getDayBegin();
        dayEnd = dayEnd + DAY_MILLIONS;
        dayBegin = dayBegin + DAY_MILLIONS;
    }
    
    public void previous(){
        dayEnd = getDayEnd();
        dayBegin = getDayBegin();
        dayEnd = dayEnd - DAY_MILLIONS;
        dayBegin = dayBegin - DAY_MILLIONS;
    }
    
    public static long getTodayEndTime(){
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(System.currentTimeMillis());
        Calendar dayEnd = Calendar.getInstance();
        dayEnd.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
        
        return dayEnd.getTimeInMillis();
    }
}
