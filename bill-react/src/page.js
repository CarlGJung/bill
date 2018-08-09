import "./page.css";
import React from 'react';
import Register from "./register";
import Login from "./login";
import BillHome from "./bill";


class Page extends React.Component{
    constructor(props){
        super(props);
    }
    
    render(){
        return (
            <div className="page">
                <div className="view">
                    {this.props.view}
                </div>
                <div className="footer">
                    {this.props.footer}
                </div>
            </div>    
        );
    }
};

export default Page;

