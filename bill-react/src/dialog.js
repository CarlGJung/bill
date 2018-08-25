import React from "react";
import $ from "jquery";

class Dialog extends React.PureComponent{    
    static defaultProps = {
        id: "",
        header: null,
        body: null,
        footer: null,
        onHide: null,
        dialogRef: null
    };
    
    constructor(props, context){
        super(props, context);
        this.$modal = null;
    }
    
    componentDidMount(){
        this.$modal.modal("show");
        this.$modal.on('hidden.bs.modal', (e) => {
            if (this.props.onHide) {
                this.props.onHide();
            }
        });
    }
    
    setModalRef = (ref)=>{
        this.$modal = $(ref);
        if (this.props.dialogRef) {
            this.props.dialogRef(this.$modal);
        }
    }
    
    render(){
        return (
          <div ref={this.setModalRef} id={this.props.id} className="modal fade" tabIndex="-1" role="dialog">
            <div className="modal-dialog" role="document">
              <div className="modal-content">
                <div className="modal-header">
                    {this.props.header}
                </div>
                <div className="modal-body">
                    {this.props.body}
                </div>
              </div>
            </div>
          </div>
        );
    }
};

export {Dialog};