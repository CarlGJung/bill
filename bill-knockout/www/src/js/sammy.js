function SammyPage(){
    var sammy = Sammy(function() {
        this.get("#login", function() {
            window.rootView.page("LoginPage");
        });
        
        this.get("#register", function(){
            window.rootView.page("RegisterPage");
        });
        
        this.get("/", function() {
            this.redirect("#login");
        });
    }).run();
    
    return sammy;
}
window.SammyPage = SammyPage;