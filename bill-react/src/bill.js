import React from 'react';
import ajax from './ajax';
import {Link, Redirect} from 'react-router-dom';
import {pbStore} from './pbStore';

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

        ajax({url: "bill/record", method: "POST", type: "application/x-protobuf", data: bill.toArrayBuffer()});
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

export default BillRecord;
