package top.carljung.bill.db.helper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
            Map<String, List<Bill>> map = new LinkedHashMap<>();
            for (Bill bill : bills) {
                String key = bill.getDate();
                if (!map.containsKey(key)) {
                    map.put(key, new ArrayList<>());
                }
                map.get(key).add(bill);
            }
            
            PBStore.BillDailyList.Builder dailyList = PBStore.BillDailyList.newBuilder();
            for (Map.Entry<String, List<Bill>> entry : map.entrySet()) {
                PBStore.BillDaily.Builder daily = PBStore.BillDaily.newBuilder();
                daily.setDate(entry.getKey());
                double income = 0;
                double payment = 0;
                
                for (Bill bill : entry.getValue()) {
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
                dailyList.addBillDailies(daily);
            }
            
            return dailyList.build();
        }
        
        return PBStore.BillDailyList.getDefaultInstance();
    }
}
