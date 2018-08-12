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

pbStore.User = pbStore.lookupType("User").ctor;


pbStore.Bill = pbStore.lookupType("Bill").ctor;//ES5 getter function

pbStore.Bill.prototype.getTypeName = function(){
    return pbStore.Bill.getTypeName(this.type);
};

pbStore.Bill.getTypeName = function(value){
    switch(value){
        case pbStore.BillType.INCOME: return "收入";
        case pbStore.BillType.PAYMENT: return "支出";
        default: return "未知";    
    }
}
export {pbStore};

