import React from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import registerServiceWorker from './registerServiceWorker';
import ajax from "./ajax";
import Register from "./register";

var structure = require("./structure.js");
ReactDOM.render(<Register />, document.getElementById('root'));
registerServiceWorker();
