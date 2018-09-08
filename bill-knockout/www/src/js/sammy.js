function SammyPage(){
    var sammy = Sammy(function() {
        this.get("#login", function() {
//            window.rootView.page(new Page({body: "LoginPage"}));
        });
    }).run();
    
    return sammy;
}
window.SammyPage = SammyPage;