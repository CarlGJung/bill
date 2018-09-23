function SammyPage(){
    var sammy = Sammy(function() {
        this.get("#login", function() {
            window.rootView.page("login-page");
        });
        
        this.get("#register", function(){
            window.rootView.page("register-page");
        });
        
        this.get("#bill", function(){
            window.rootView.page("bill-page");
        });
        
        this.get("/", function() {
            this.redirect("#login");
        });
    }).run();
    
    return sammy;
}
window.SammyPage = SammyPage;