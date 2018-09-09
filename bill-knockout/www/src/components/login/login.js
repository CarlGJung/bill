define(["knockout", "text!./login.html", "css!./login.css"], function(ko, htmlString) {

    function Login(params) {
        var self = this;
        self.username = ko.observable("");
        self.password = ko.observable("");
        
        self.login = function(){
            var user = pbStore.User.create({});
            user.username = self.username();
            user.password = self.password();

            ajax({url: "/users/login", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer(), complete: function(data, xhr){
                console.log(data);
                console.log(xhr);
            }});
        };
        
        this.gotoRegister = function(){
            location.hash = "#register";
        };
    }

    return { viewModel: Login, template: htmlString };
});
