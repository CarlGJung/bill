define(["knockout", "text!./page.html", "css!./page.css"], function(ko, htmlString) {
    var defaultProps = {
        data: null,
        body: "",
        footer: "",
        css: ""
    };
    
    function Page(params){
        params = params || defaultProps;
        
        this.css = params.css;
        this.data = params.data;
        this.body = params.body;
        this.footer = params.footer;
    }
    
    return { viewModel: Page, template: htmlString };
});

