import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import ajax from "./ajax";
import Register from "./register";
import Login from "./login";

var structure = require("./structure.js");
ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Redirect exact from="/" to="/login"></Redirect>
            <Route path="/login" component={Login}></Route>
            <Route path="/register" component={Register}></Route>
        </Switch>
    </BrowserRouter>
, document.getElementById('root'));
registerServiceWorker();
