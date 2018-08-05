import {Message} from "protobufjs/light";
import pbStore from "./structure";

const debug = false;
window.pbStore = pbStore;

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

export {pbStore, User};

