function DialogAgent(){
    var defaultProps = {
        dialogId: "",
        header: null,
        body: null,
        footer: null,
        onHide: null,
        dialogRef: null
    };
    
    function DialogRoot(container, options){
        this.header = options.header || defaultProps.header;
        this.body = options.body || defaultProps.body;
        this.footer = options.footer || defaultProps.footer;
        this.data = options.data || options;
        this.dialogRef = (function(dialogRef){
            this.dialogRef = dialogRef;
            if (options.dialogRef) {
                options.dialogRef(dialogRef);
            }
        }).bind(this);
        this.onHide = function(){
            document.body.removeChild(container);
            if (options.onHide) {
               options.onHide();
            } 
        };
    }
    
    this.showDialog = function(options){
        var dialogEle = '<modal-dialog params="header: $data.header, body: $data.body'
                + ', footer: $data.footer, data: $data.data' 
                + ', dialogRef: $data.dialogRef, onHide: $data.onHide"/>';
        var container = document.createElement("div");
        container.innerHTML = dialogEle;
        document.body.appendChild(container);
        
        var dialogRoot = new DialogRoot(container, options);
        ko.applyBindings(dialogRoot, container);
    };
}

window.DialogAgent = DialogAgent;