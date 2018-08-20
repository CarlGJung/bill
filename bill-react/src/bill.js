import React from 'react';
import ajax from './ajax';
import {Link, Redirect} from 'react-router-dom';
import Page from "./page";
import {TabSelector} from "./component";
import {Dialog} from "./dialog";
import "./bill.css";

const billTabs = [];
Object.keys(window.pbStore.BillType).forEach(function(key){
    var value = window.pbStore.BillType[key];
    if (value > 0) {
        billTabs.push({"value": value, "name": window.pbStore.Bill.getTypeName(value)});
    }
});

let allLabels = [];
let paymentLabels = [];
let incomeLabels = [];

function getLabels(callback){
    if (!allLabels.length) {
        ajax({url: "bills/labels/" + window.pbStore.BillType.UNKNOW, method: "GET", accept: "application/x-protobuf", success: (data)=>{
            let labels = window.pbStore.BillLabelList.decode(data);
            allLabels = labels.labels;
            allLabels.forEach(function(label){
                switch(label.type){
                    case window.pbStore.BillType.PAYMENT:
                        paymentLabels.push(label);
                        break;
                    case window.pbStore.BillType.INCOME:
                        incomeLabels.push(label);
                        break;
                    default:;    
                }
            });
            if (callback) {
                callback();
            }
        }});
    } else {
        if (callback) {
            callback();
        }
    }
}

class BillHome extends React.Component{
    constructor(props){
        super(props);
        this.state = {bills: [], showRecord: false};
    }
    
    componentDidMount(){
        getLabels(this.getBillList);
    }
    
    getBillList = ()=>{
        ajax({url: "bills/bills", accept: "application/x-protobuf", success: (data, xhr)=>{
            var bills = window.pbStore.BillList.decode(data);
            this.setState({bills: bills.bills});
        }});
    }
    
    showRecordDialog = ()=>{
        this.setState({showRecord: true});
    }
    
    onRecordDialogHide = ()=>{
        this.setState({showRecord: false});
        this.getBillList();
    }
    render(){
        return(
            <div>
                <BillList bills={this.state.bills}></BillList>
                <button onClick={this.showRecordDialog}>记账</button>
                {this.state.showRecord 
                    ? <RecordDialog onHide={this.onRecordDialogHide}></RecordDialog>
                    : null
                }
            </div>    
        );
    }
};

class RecordDialog extends React.Component{
    constructor(props){
        super(props);
        this.state = {money: "", type: window.pbStore.BillType.PAYMENT, selectedLabel: null, labels: []};
    }
    
    componentDidMount(){
        this.getBillLabels();
    }
    
    getBillLabels = (type=this.state.type)=>{
        let labels = [];
        
        switch(type){
            case window.pbStore.BillType.PAYMENT:
                labels = paymentLabels;
                break;
            case window.pbStore.BillType.INCOME:
                labels = incomeLabels;
                break;
            default:;    
        }
        this.setState({labels: labels, selectedLabel: labels[0]});
    }
    
    handleInput = (event)=>{
        this.setState({money: event.target.value});
    }

    recordBill = (event)=>{
        if (this.state.money > 0) {
            var bill = window.pbStore.Bill.create({
                type: this.state.type
              , money: this.state.money
              , labelId: this.state.selectedLabel.id
            });
            this.setState({money: 0});
            ajax({url: "bills/record", method: "POST", type: "application/x-protobuf", data: bill.toArrayBuffer(), success: (data)=>{

            }});
        }
    }
    
    onBillTypeChange = (value)=>{
        this.setState({type: value});
        this.getBillLabels(value);
    }
    
    selectLabel = (label)=>{
        this.setState({selectedLabel: label});
    }
    
    render(){
        return (
            <Dialog id="bill-record"
                    header={
                    <div className="container-fluid">
                        <div className="row">
                            <div className="col-3" data-dismiss="modal">取消</div>
                            <div className="col-6">
                                <TabSelector tabs={billTabs} value={this.state.type} onSelect={this.onBillTypeChange}></TabSelector>
                            </div>
                            <div className="col-3" onClick={this.recordBill}>确定</div>
                        </div>
                    </div>
                    }
                    body={
                    <div>
                        <BillLabel label={this.state.selectedLabel}></BillLabel> 
                        <input type="number" autoFocus value={this.state.money} onChange={this.handleInput}></input>
                        <BillLabelList labels={this.state.labels} select={this.selectLabel}/>
                    </div>
                    }
                    onHide={this.props.onHide}
            ></Dialog>
        );
    }
};

function BillList(props){
    const bills = props.bills;
    const billItems = bills.map((bill)=>
        <li key={bill.id}>
            <BillLabel label={bill.getLabel(allLabels)}></BillLabel>
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

function BillLabel(props){
    const label = props.label;
    if (label) {
        return (
            <div style={{color: `${label.color}`}}>
                <span>{label.name}</span>
            </div>
        );
    } else {
        return null;
    }    
}

function BillLabelList(props){
    const labels = props.labels;
    const select = function(label){
        if (props.select) {
            props.select(label);
        }
    };
    
    const labelItems = labels.map((label)=>
        <li key={label.id} onClick={()=>{select(label)}}>
            <BillLabel label={label}></BillLabel>
        </li>
    );
   
    return (
        <ul>
            {labelItems}
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
