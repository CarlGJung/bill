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
        enumType.prototype = Enum.prototype;
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
        if (debug) {
            var errors = this.$type.verify(this);
            if (errors) {
                throw new Error(errors);
            }
        }
        return this.$type.encode(this).finish();
    };

    Message.prototype.getDeclaredFileds = function(){
        return Object.keys(this.$type.fields);
    };

    Message.prototype.assignDeclaredFileds = function(options={}){
        const declaredFileds = this.getDeclaredFileds();
        declaredFileds.forEach((field)=>{
            this[field] = options[field];
        });
    };

    pbStore.User = pbStore.lookupType("User").ctor;

    function Bill(options={}){
        this.assignDeclaredFileds(options);
        this.typeEnum = null;
        this._label = null;
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
    
    Bill.prototype.getTypeName = function(){
        return this.typeEnum.getName();
    };

    Bill.prototype.getLabel = function(labels){
        labels = ko.unwrap(labels);
        if (!this._label && labels && labels.length) {
            for (let i = 0; i < labels.length && !this._label; i++) {
                let label = labels[i];

                if (label.id === this.labelId) {
                    this._label = label;
                }
            }

        }
        return this._label;
    };
};
