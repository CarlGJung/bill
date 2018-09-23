define(["knockout", "text!./dialog.html", "css!./dialog.css"], function(ko, htmlString) {
    var defaultProps = {
        dialogId: "",
        header: null,
        body: null,
        footer: null,
        onHide: null,
        dialogRef: null
    };
    
    function Dialog(params, componentInfo){
        params = params || defaultProps;
        
        var self = this;
        self.data = params.data;
        self.dialogId = params.dialogId;
        self.header = params.header;
        self.body = params.body;
        self.footer = params.footer;
        self.onHide = params.onHide;
        self.dialogRef = params.dialogRef;
        
        self.$modal;
        if (componentInfo.element.nodeType === 8) {//comment
            self.$modal = $(componentInfo.element.nextElementSibling);
        } else if (componentInfo.element.tagName === "MODAL-DIALOG") {//custom elements
            self.$modal = $(componentInfo.element.firstElementChild);
        } else {
            self.$modal = $(componentInfo.element);
        }
        
        if (self.dialogRef) {
            self.dialogRef(self.$modal);
        }
        
        self.$modal.modal("show");
        self.$modal.on('hidden.bs.modal', function() {
            if (self.onHide) {
                self.onHide();
            }
        });
    }
    
    return { 
        viewModel: {createViewModel: function(params, componentInfo){
            return new Dialog(params, componentInfo);
        }}, 
        template: htmlString 
    };
});


