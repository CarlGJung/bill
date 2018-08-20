import {Message} from "protobufjs/light";
import pbStore from "./structure";

window.pbStore = pbStore;
const debug = false;

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
    this._label = null;
}

pbStore.lookupType("Bill").ctor = Bill;
pbStore.Bill = Bill;

Bill.prototype.getTypeName = function(){
    return Bill.getTypeName(this.type);
};

Bill.prototype.getLabel = function(labels){
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

Bill.getTypeName = function(value){
    switch(value){
        case pbStore.BillType.INCOME: return "收入";
        case pbStore.BillType.PAYMENT: return "支出";
        default: return "未知";    
    }
};
export {pbStore};

