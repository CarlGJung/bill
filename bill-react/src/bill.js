import React from 'react';
import ajax from './ajax';
import {Link, Redirect} from 'react-router-dom';
import {pbStore} from './pbStore';

class BillHome extends React.Component{
    constructor(props){
        super(props);
        this.state = {bills: []};
    }
    
    componentDidMount(){
        ajax({url: "bills/bills", accept: "application/x-protobuf", success: (data, xhr)=>{
            var bills = pbStore.BillList.decode(data);
            this.setState({bills: bills.bills});
        }});
    }
    
    render(){
        return(
            <div>
                <BillRecord></BillRecord>
                <BillList bills={this.state.bills}></BillList>
            </div>    
        );
    }
};

class BillRecord extends React.Component{
    constructor(props){
        super(props);
        this.state = {money: ""};
        this.handleInput = this.handleInput.bind(this);
        this.recordBill = this.recordBill.bind(this);
    }
    
    handleInput(event){
        this.setState({money: event.target.value});
    }

    recordBill(event){
        var bill = pbStore.Bill.create({
            type: pbStore.BillType.INCOME
          , money: this.state.money
          , labelId: 0
        });

        ajax({url: "bills/record", method: "POST", type: "application/x-protobuf", data: bill.toArrayBuffer()});
    }
    
    render(){
        return (
            <div>
                <input type="number" autoFocus value={this.state.money} onChange={this.handleInput}></input>
                <button onClick={this.recordBill}>确定</button>
            </div>
        );
    }
};

function BillList(props){
    const bills = props.bills;
    const billItems = bills.map((bill)=>
        <li key={bill.id}>{bill.money}</li>
    );
   
    return (
        <ul>
            {billItems}
        </ul>        
    );
}

export default BillHome;
