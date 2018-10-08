package top.carljung.bill.db.helper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import top.carljung.bill.db.Bill;
import top.carljung.bill.proto.PBStore;

/**
 *
 * @author wangchao
 */
public class BillHelper {
    public static PBStore.BillDailyList getBillDailyList(int userId){
        List<Bill> bills = Bill.getBillList(userId);
        
        if (!bills.isEmpty()) {
            Map<String, List<Bill>> map = bills.stream().collect(
                Collectors.groupingBy(Bill::getDateStr, LinkedHashMap::new, Collectors.toList())
            );
            
            PBStore.BillDailyList.Builder dailyList = PBStore.BillDailyList.newBuilder();
            for (Map.Entry<String, List<Bill>> entry : map.entrySet()) {
                dailyList.addBillDailies(computeBillDaily(entry.getValue()));
            }
            
            return dailyList.build();
        }
        
        return PBStore.BillDailyList.getDefaultInstance();
    }
    
    private static PBStore.BillDaily computeBillDaily(List<Bill> oneDayBills){
        assert oneDayBills != null;
        assert oneDayBills.size() > 0;
        
        PBStore.BillDaily.Builder daily = PBStore.BillDaily.newBuilder();
        double income = 0;
        double payment = 0;
        for (Bill bill : oneDayBills) {
            daily.addBills(bill.toPBBill());
            double money = bill.getMoney();
            short type = bill.getType();

            if (type == PBStore.BillType.INCOME_VALUE) {
                income += money;
            } else if (type == PBStore.BillType.PAYMENT_VALUE) {
                payment += money;
            }
        }

        daily.setIncome(income);
        daily.setPayment(payment);

        Bill oneBill = oneDayBills.get(0);
        daily.setDayOfMonth(oneBill.getDayOfMonth());
        daily.setMonth(oneBill.getMonth());
        daily.setYear(oneBill.getYear());
        daily.setWeekOfYear(oneBill.getWeekOfYear());
        return daily.build();
    }
    
}
