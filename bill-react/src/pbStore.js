import pbStore from "./structure";

function User(){
    
}

pbStore.lookupType("User").ctor = User;

User.prototype.toArrayBuffer = function(){
    return User.encode(this).finish();
};

export default User;

