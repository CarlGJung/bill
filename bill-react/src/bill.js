import React from 'react';
import ajax from './ajax';
import {Link, Redirect} from 'react-router-dom';
import Page from "./page";
import TabSelector from "./component"

class BillHome extends React.Component{
    constructor(props){
        super(props);
        this.state = {bills: []};
        this.getBillList = this.getBillList.bind(this);
    }
    
    componentDidMount(){
        this.getBillList();
    }
    
    getBillList(){
        ajax({url: "bills/bills", accept: "application/x-protobuf", success: (data, xhr)=>{
            var bills = window.pbStore.BillList.decode(data);
            this.setState({bills: bills.bills});
        }});
    }
    
    render(){
        return(
            <div>
                <BillRecord success={this.getBillList}></BillRecord>
                <BillList bills={this.state.bills}></BillList>
            </div>    
        );
    }
};

const billTabs = [];
Object.keys(window.pbStore.BillType).forEach(function(key){
    var value = window.pbStore.BillType[key];
    if (value > 0) {
        billTabs.push({"value": value, "name": window.pbStore.Bill.getTypeName(value)});
    }
});

class BillRecord extends React.Component{
    constructor(props){
        super(props);
        this.state = {money: "", type: window.pbStore.BillType.PAYMENT};
        this.handleInput = this.handleInput.bind(this);
        this.recordBill = this.recordBill.bind(this);
        this.onBillTypeChange = this.onBillTypeChange.bind(this);
    }
    
    handleInput(event){
        this.setState({money: event.target.value});
    }

    recordBill(event){
        var bill = window.pbStore.Bill.create({
            type: this.state.type
          , money: this.state.money
          , labelId: 0
        });
        
        ajax({url: "bills/record", method: "POST", type: "application/x-protobuf", data: bill.toArrayBuffer(), success: (data)=>{
            if (this.props.success) {
                this.props.success();
            }
        }});
    }
    
    onBillTypeChange(value){
        this.setState({type: value});
    }
    
    render(){
        return (
            <div>
                <TabSelector tabs={billTabs} value={this.state.type} onSelect={this.onBillTypeChange}></TabSelector>    
                <input type="number" autoFocus value={this.state.money} onChange={this.handleInput}></input>
                <button onClick={this.recordBill}>确定</button>
            </div>
        );
    }
};

function BillList(props){
    const bills = props.bills;
    const billItems = bills.map((bill)=>
        <li key={bill.id}>
            <span>{bill.getTypeName()}</span>
            <span>{bill.money}</span>
        </li>
    );
   
    return (
        <ul>
            {billItems}
        </ul>        
    );
}

class BillPage extends React.Component{
    render(){
        return (
            <Page view={<BillHome/>} footer={(<Link to="/login">退出</Link>)}></Page>            
        );
    } 
};

export default BillPage;
