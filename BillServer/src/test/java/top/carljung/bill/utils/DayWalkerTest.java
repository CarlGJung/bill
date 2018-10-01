package top.carljung.bill.utils;

import java.text.SimpleDateFormat;
import org.junit.Test;

/**
 *
 * @author wangchao
 */
public class DayWalkerTest {
    @Test
    public void test(){
        DayWalker walker = new DayWalker();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        System.out.println(format.format(walker.getDayBegin()));
        System.out.println(format.format(walker.getDayEnd()));
        
        walker.previous();
        
        System.out.println(format.format(walker.getDayBegin()));
        System.out.println(format.format(walker.getDayEnd()));
        
        walker.next();
        
        System.out.println(format.format(walker.getDayBegin()));
        System.out.println(format.format(walker.getDayEnd()));
    }
}
