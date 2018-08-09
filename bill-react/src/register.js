import React from 'react';
import {Link} from 'react-router-dom';
import ajax from './ajax';
import {pbStore} from './pbStore';
import Page from "./page";

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
        var user = pbStore.User.create({});
        user.username = this.state.username;
        user.password = this.state.password;

        ajax({url: "/users/register", method: "POST", type:"application/x-protobuf", data: user.toArrayBuffer()});
        
    }
    
    render(){
        return( 
            <div>
                <form onSubmit={this.onSubmit}>
                    <div className="form-group username">
                        <input type="text" value={this.state.username} onChange={this.onInputUsername} placeholder="请输入用户名"/>
                    </div>
                    <div className="form-group password">
                        <input type="password" value={this.state.password} onChange={this.onInputPassword} placeholder="请输入密码"/>
                    </div>
                    <button className="btn btn-primary login-btn" type="submit">注册</button>
                </form>
            </div>
        );
    }
};

class RegisterPage extends React.Component{
    render(){
        return (
            <Page view={<Register/>} footer={(<Link to="/login">登录</Link>)}></Page>            
        );
    } 
};

export default RegisterPage;