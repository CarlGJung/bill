define(["knockout", "text!./bill.html", "css!./bill.css"], function(ko, htmlString) {
    var allLabels = ko.observableArray([]);
    var paymentLabels = [];
    var incomeLabels = [];
    
    function getLabels(callback){
        if (!allLabels().length) {
            ajax({url: "bills/labels/" + window.pbStore.BillType.UNKNOW, method: "GET", accept: "application/x-protobuf", success: function(data){
                var labels = window.pbStore.BillLabelList.decode(data);
                allLabels(labels.labels);
                allLabels().forEach(function(label){
                    switch(label.type){
                        case window.pbStore.BillType.PAYMENT:
                            paymentLabels.push(label);
                            break;
                        case window.pbStore.BillType.INCOME:
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
    
    getLabels();
    
    function BillPage(params){
        var self = this;
        
        self.bills = ko.observableArray([]);
        self.allLabels = allLabels;
        self.recordDialog = null;
        self.showRecord = ko.observable(false);
        
        self.getBillList = function(){
            ajax({url: "bills/bills", accept: "application/x-protobuf", success: (data, xhr)=>{
                var bills = window.pbStore.BillList.decode(data);
                self.bills(bills.bills);
            }});
        };
    
        self.showRecordDialog = function(){
            if (!self.recordDialog) {
                self.recordDialog = new RecordDialog({onHide: self.onRecordDialogHide});
            }
            self.showRecord(true);
        };
    
        self.onRecordDialogHide = function(){
            self.showRecord(false);
            self.getBillList();
        };
        
        self.getBillList();
    }
    
    var defaultProps = {
        onHide: null
    };
    
    function RecordDialog(params){
        var self = this;
        self.params = params || defaultProps;
        self.money = ko.observable("");
        self.type = ko.observable(window.pbStore.BillType.PAYMENT);
        self.selectedLabel = ko.observable({});
        self.labels = ko.observableArray([]);
        self.dialog = null;
        
        self.getDialogReg = function(dialogRef){
            self.dialog = dialogRef;
        };
        
        self.billTabs = ko.observableArray([]);
        
        Object.keys(window.pbStore.BillType).forEach(function(key){
            var value = window.pbStore.BillType[key];
            if (value > 0) {
                self.billTabs.push({"value": value, "name": window.pbStore.Bill.getTypeName(value)});
            }
        });
        
        self.getBillLabels = function(){
            var labels = [];

            switch(self.type()){
                case window.pbStore.BillType.PAYMENT:
                    labels = paymentLabels;
                    break;
                case window.pbStore.BillType.INCOME:
                    labels = incomeLabels;
                    break;
                default:;    
            }
            
            self.labels(labels);
            self.selectedLabel(labels[0]);
        };
    
        self.recordBill = function(){
            if (self.money() > 0) {
                var bill = window.pbStore.Bill.create({
                    type: self.type()
                  , money: self.money()
                  , labelId: self.selectedLabel().id
                });
                
                self.dialog.modal("hide");
                self.money(0);
                ajax({url: "bills/record", method: "POST", type: "application/x-protobuf", data: bill.toArrayBuffer(), success: function(data){

                }});
            }
        };
    
        self.onBillTypeChange = function(type){
            self.type(type.value);
            self.getBillLabels();
        };
    
        self.selectLabel = function(label){
            self.selectedLabel(label);
        };
        
        self.getBillLabels();
    };
    
    return {viewModel: BillPage, template: htmlString};
});