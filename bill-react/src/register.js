import React from 'react';
import ajax from './ajax';
import User from './pbStore';

class Register extends React.Component{
    constructor(props){
        super(props);
        this.state = {username: "", password: "", verifyUsernameMsg: "", verifyPasswordMsg: ""};
        this.onInputUsername = this.onInputUsername.bind(this);
        this.onInputPassword = this.onInputPassword.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }
    
    onInputUsername = function(event){
        this.setState({username: event.target.value});
    }
    
    onInputPassword = function(event){
        this.setState({password: event.target.value});
    }
    
    onSubmit = function(event){
        event.preventDefault();
        var user = new User();
        user.username = this.state.username;
        user.password = this.state.password;
        console.log(user.toArrayBuffer());
        ajax({url: "/user/login", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer()});
        
    }
    
    render(){
        return( 
            <div>
                <form onSubmit={this.onSubmit}>
                    <input type="text" value={this.state.username} onChange={this.onInputUsername} placeholder="请输入用户名"/>
                    <span>{this.state.verifyUsernameMsg}</span>
                    <input type="password" value={this.state.password} onChange={this.onInputPassword} placeholder="请输入密码"/>
                    <input type="submit" value="注册"></input>
                </form>
            </div>
        );
    }
};

export default Register;