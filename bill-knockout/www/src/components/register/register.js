define(["knockout", "text!./register.html", "css!./register.css"], function(ko, htmlString) {

    function Register(params) {
        var self = this;
        self.username = ko.observable("");
        self.password = ko.observable("");
        
        self.register = function(){
            var user = pbStore.User.create({});
            user.username = self.username();
            user.password = self.password();

            ajax({url: "/users/register", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer(), complete: function(data, xhr){
                console.log(data);
                console.log(xhr);
            }});
        };
        
        self.gotoLogin = function(){
            location.hash = "#login";
        };
    }

    return { viewModel: Register, template: htmlString };
});


