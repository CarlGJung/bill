function PbStoreAgent(pbStore, protobuf){
    const debug = false;
        
    function Enum(value){
        this.value = value;
    }
    
    Enum.prototype.valueOf = function(){
        return this.value;
    };
    
    Enum.prototype.toString = function(){
        return String(this.value);
    };
    
    function enumGenerator(typeName, scope){
        scope = scope || pbStore;
        var originEnum = pbStore[typeName];
        var source =  "return "
                    + "function " + typeName + "(){ "
                    + "    Enum.apply(this, arguments); "
                    + "} ";
        var enumType = scope[typeName] = Function("Enum", source)(Enum);
        enumType.prototype = new Enum();
        enumType.values = [];
        enumType.valuesById = {};
        enumType.fromNumber = function(value){
            return enumType.valuesById[value];
        };
        
        Object.keys(originEnum).forEach(function(name){
            var id = originEnum[name];
            var value = enumType[name] = new enumType(id);
            enumType.values.push(value);
            enumType.valuesById[id] = value;
        });
    }
    
    pbStore.nestedArray.forEach(function(item){
        if (item instanceof protobuf.Enum) {
            enumGenerator(item.name);
        }
    });
    
    pbStore.BillType.prototype.getName = function(){
        switch(this){
            case pbStore.BillType.INCOME: return "收入";
            case pbStore.BillType.PAYMENT: return "支出";
            default: return "未知";    
        } 
    };
    
    var Message = protobuf.Message;
    
    Message.prototype.toArrayBuffer = function(){
        var unwrapedMsg = this.unwrap();
        if (debug) {
            var errors = this.$type.verify(unwrapedMsg);
            if (errors) {
                throw new Error(errors);
            }
        }
        return this.$type.encode(unwrapedMsg).finish();
    };
    
    Message.prototype.prepare = function(){
    };
    
    Message.prototype.unwrap = function(){
        this.prepare();
        var unwraped = {};
        this.getDeclaredFileds().forEach((field) => {
            unwraped[field] = typeof this[field] === "function" ? this[field]() : this[field];
        });
        return unwraped;
    };
    
    Message.prototype.getDeclaredFileds = function(){
        return Object.keys(this.$type.fields);
    };

    Message.prototype.assignUndeclaredFileds = function(options={}){
        const declaredFileds = this.getDeclaredFileds();
        declaredFileds.forEach((field)=>{
            if (!this.hasOwnProperty(field)) {
                this[field] = options[field];
            }
        });
    };
    
    pbStore.User = pbStore.lookupType("User").ctor;

    function Bill(options={money: "", type: window.pbStore.BillType.PAYMENT, labelId: 0, time: 0}){
        this.label = null;
        this.typeEnum = null;
        this.inEditModal = false;
        this.assignUndeclaredFileds(options);
    }

    pbStore.lookupType("Bill").ctor = Bill;
    pbStore.Bill = Bill;
    
    Object.defineProperties(Bill.prototype, {
        "typeEnum": {
            get: function(){
                return this._typeEnum || (this._typeEnum = pbStore.BillType.fromNumber(this.type));
            }
        }
    });
    
    Bill.prototype.editModal = function(clone){
        var o = clone ? new Bill(this) : this;
        o.money = ko.observable(this.money);
        o.type = ko.observable(this.typeEnum);
        o.time = ko.observable(this.time || Date.now());
        o.label = ko.observable(this.label);
        o.inEditModal = true;
        return o;
    };
    
    Bill.prototype.prepare = function(){
        this.labelId = this.label().id;
    };
    
    Bill.prototype.getTypeName = function(){
        return this.typeEnum.getName();
    };

    Bill.prototype.getLabel = function(labels){
        if (this.inEditModal) {
            return this.label();
        }
        
        if (!this.label && (labels = ko.unwrap(labels)) && labels.length) {
            for (let i = 0; i < labels.length && !this.label; i++) {
                let label = labels[i];

                if (label.id === this.labelId) {
                    this.label = label;
                }
            }

        }
        return this.label;
    };
    
    function BillDaily(options={date: "", income: 0, payment: 0, bills: null}){
        this.assignUndeclaredFileds(options);
    }
    
    const YEAR = new Date().getFullYear();
    BillDaily.prototype.dateLabel = function(){
        if (this.year === YEAR) {
            return this.month + "-" + this.dayOfMonth;
        }
        return this.year + "-" + this.month + "-" + this.dayOfMonth;
    };
    
    BillDaily.prototype.incomeLabel = function(){
        if (this.income) {
            return "收入: " + this.income;
        }
        return "";
    };
    
    BillDaily.prototype.paymentLabel = function(){
        if (this.payment) {
            return "支出: " + this.payment;
        }
        return "";
    };
    
    pbStore.lookupType("BillDaily").ctor = BillDaily;
    pbStore.BillDaily = BillDaily;
    
};
