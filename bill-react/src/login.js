import React from 'react';
import ajax from './ajax';
import {pbStore} from './pbStore';
import {Link, Redirect} from 'react-router-dom';

class Login extends React.Component{
     constructor(props){
        super(props);
        this.state = {username: "", password: "", verifyUsernameMsg: "", verifyPasswordMsg: "", login: false};
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
        var user = pbStore.User.create({});
        user.username = this.state.username;
        user.password = this.state.password;
        
        ajax({url: "/users/login", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer(), success: (data)=>{
            this.setState({login: true});
        }});
        
    }
    
    render(){
        if (this.state.login) {
            return  <Redirect to="/bill"></Redirect>;
        }
        return( 
            <div>
                <form onSubmit={this.onSubmit}>
                    <input type="text" value={this.state.username} onChange={this.onInputUsername} placeholder="请输入用户名"/>
                    <span>{this.state.verifyUsernameMsg}</span>
                    <input type="password" value={this.state.password} onChange={this.onInputPassword} placeholder="请输入密码"/>
                    <input type="submit" value="登录"></input>
                </form>
                <Link to="/register">注册</Link>
            </div>
        );
    }
};

export default Login;