define(["knockout", "text!./bill.html", "css!./bill.css"], function(ko, htmlString) {
    var allLabels = ko.observableArray([]);
    var paymentLabels = [];
    var incomeLabels = [];
    
    function getLabels(callback){
        if (!allLabels().length) {
            ajax({url: "bills/labels/" + window.pbStore.BillType.UNKNOW_BillType, method: "GET", accept: "application/x-protobuf", success: function(data){
                var labels = window.pbStore.BillLabelList.decode(data);
                allLabels(labels.labels);
                allLabels().forEach(function(label){
                    switch(label.type){
                        case window.pbStore.BillType.PAYMENT.value:
                            paymentLabels.push(label);
                            break;
                        case window.pbStore.BillType.INCOME.value:
                            incomeLabels.push(label);
                            break;
                        default:;    
                    }
                });
                if (callback) {
                    callback();
                }
            }});
        } else {
            if (callback) {
                callback();
            }
        }
    }
        
    function BillPage(params){
        var self = this;
        
        self.bills = ko.observableArray([]);
        self.allLabels = allLabels;
        
        self.getBillList = function(){
            ajax({url: "bills/bills", accept: "application/x-protobuf", success: function(data, xhr){
                var bills = window.pbStore.BillList.decode(data);
                self.bills(bills.bills);
            }});
        };
    
        self.recordDialog = null;
        self.showRecordDialog = function(){
            if (!self.recordDialog) {
                self.recordDialog = new RecordDialog({onHide: self.onRecordDialogHide});
            }
            rootView.showDialog(self.recordDialog);
        };
    
        self.onRecordDialogHide = function(){
            self.getBillList();
        };
        
        self.deleteBill = function(bill){
            ajax({url: "bills/" + bill.id, method: "DELETE", success: function(){
                self.getBillList();
            }});
        };
        
        self.prepareUpdateBill = function(bill){
            
        };
        
        getLabels(self.getBillList);
    }
    
    function RecordDialog(params){
        var self = this;
        self.params = params || {};
        self.bill = new pbStore.Bill();
        self.labels = ko.observableArray([]);
        self.header = "recordDialogHeader";
        self.body = "recordDialogBody";
        self.onHide = params.onHide;
        self.dialog = null;
        self.dialogRef = function(dialogRef){
            self.dialog = dialogRef;
        };;
        self.dialogId = "bill-record";
            
        self.billTabs = ko.observableArray([]);
        
        Object.keys(window.pbStore.BillType).forEach(function(key){
            var value = window.pbStore.BillType[key];
            if (value > 0) {
                self.billTabs.push({"value": value, "name": value.getName()});
            }
        });
        
        self.getBillLabels = function(){
            var labels = [];

            switch(self.bill.type()){
                case window.pbStore.BillType.PAYMENT:
                    labels = paymentLabels;
                    break;
                case window.pbStore.BillType.INCOME:
                    labels = incomeLabels;
                    break;
                default:;    
            }
            
            self.labels(labels);
            self.bill.label(labels[0]);
        };
    
        self.recordBill = function(){
            if (self.bill.money() > 0) {
                self.dialog.modal("hide");
                ajax({url: "bills/record", method: "PUT", type: "application/x-protobuf", data: self.bill.toArrayBuffer(), success: function(data){

                }});
            }
        };
    
        self.onBillTypeChange = function(type){
            self.bill.type(type.value);
            self.getBillLabels();
        };
    
        self.selectLabel = function(label){
            self.bill.label(label);
        };
        
        getLabels(self.getBillLabels);
    };
    
    return {viewModel: BillPage, template: htmlString};
});