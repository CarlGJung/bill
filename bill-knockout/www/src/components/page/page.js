define(["knockout", "text!./page.html", "css!./page.css"], function(ko, htmlString) {
    var defaultProps = {
        data: null,
        body: "",
        footer: ""
    };
    
    function Page(props){
        props = props || defaultProps;
        
        this.data = props.data;
        this.body = props.body;
        this.footer = props.footer;
    }
    
    return { viewModel: Page, template: htmlString };
});

