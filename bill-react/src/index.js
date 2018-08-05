import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter, Route, Switch, Redirect} from 'react-router-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import ajax from "./ajax";
import Register from "./register";
import Login from "./login";
import BillHome from "./bill";

var structure = require("./structure.js");
ReactDOM.render(
    <BrowserRouter>
        <Switch>
            <Redirect exact from="/" to="/login"></Redirect>
            <Route exact path="/login" component={Login}></Route>
            <Route exact path="/register" component={Register}></Route>
            <Route exact path="/bill" component={BillHome}></Route>
        </Switch>
    </BrowserRouter>
, document.getElementById('root'));
registerServiceWorker();
