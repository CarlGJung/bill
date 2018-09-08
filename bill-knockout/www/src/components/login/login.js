define(["knockout", "text!./login.html", "css!./login.css"], function(ko, htmlString) {

    function Login(params) {
        var self = this;
        self.username = ko.observable("");
        self.password = ko.observable("");
        
        self.login = function(){
            var user = pbStore.User.create({});
            user.username = self.username();
            user.password = self.password();
            console.log(user);
            ajax({url: "/users/login", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer(), complete: function(data, xhr){
                console.log(data);
                console.log(xhr);
            }});
        };
    }

    return { viewModel: Login, template: htmlString };
});
