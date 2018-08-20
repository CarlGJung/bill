import React from "react";
import "./component.css";

class TabSelector extends React.PureComponent{
    static defaultProps = {
        value: null,
        tabs: [],
        onSelect: () => {}
    };
    
    render(){
        return (
            <div className="tab-selector">
                <ul>
                {this.props.tabs.map((tab)=>(
                        <li key={tab.value} 
                            className={`tab-item ${tab.value === this.props.value ? "selected" : ""}`}
                            onClick={()=>this.props.onSelect(tab.value, tab)}
                        >
                            {tab.name}    
                        </li>
                ))}    
                </ul>
            </div>    
        );
    }
};

export {TabSelector};
