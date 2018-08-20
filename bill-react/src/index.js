import React from 'react';
import ReactDOM from 'react-dom';
import {HashRouter, Route, Switch, Redirect, Link} from 'react-router-dom';
import registerServiceWorker from './registerServiceWorker';
import "bootstrap";
import 'bootstrap/dist/css/bootstrap.css';
import './index.css';
import ajax from "./ajax";
import LoginPage from "./login";
import RegisterPage from "./register";
import BillPage from "./bill";
import './pbStore';


class App extends React.Component{
    render(){
        return (
            <HashRouter>
                <Switch>
                    <Redirect exact from="/" to="/login"></Redirect>
                    <Route exact path="/login" component={LoginPage}></Route>
                    <Route exact path="/register" component={RegisterPage}></Route>
                    <Route exact path="/bill" component={BillPage}></Route>
                </Switch>
            </HashRouter>   
        );
    }
};

ReactDOM.render(
    <App></App>        
, document.getElementById('root'));
registerServiceWorker();
