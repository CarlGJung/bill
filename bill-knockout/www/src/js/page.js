    var defaultProps = {
        body: "",
        footer: ""
    };
    
    function Page(props){
        props = props || defaultProps;
        
        this.body = props.body;
        this.footer = props.footer;
    }
    
    window.Page = Page;

