requirejs(["pbStore", "protobufjs/light"],function(pbStore, protobuf){
    window.pbStore = pbStore;
    PbStoreAgent.call(this, pbStore, protobuf);
    
    function RootView(){
        window.rootView = this;
        this.page = ko.observable("LoginPage");
        this.app = new SammyPage();
    }

    ko.applyBindings(new RootView());
});
