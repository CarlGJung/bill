import {Message} from "protobufjs/light";
import pbStore from "./structure";

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
function User(){
    
}

pbStore.lookupType("User").ctor = User;//ES5 setter function

function Bill(){
    
}
pbStore.lookupType("Bill").ctor = Bill;//ES5 setter function

Bill.prototype.getTypeText = function(){
    switch(this.type){
        case pbStore.BillType.INCOME: return "收入";
        case pbStore.BillType.PAYMENT: return "收入";
        default: return "未知";    
    }
};

export {pbStore};

