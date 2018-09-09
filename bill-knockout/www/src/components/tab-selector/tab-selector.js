define(["knockout", "text!./tab-selector.html", "css!./tab-selector.css"], function(ko, htmlString) {
    var defaultProps = {
        value: null,
        tabs: [],
        onSelect: null
    };

    function TabSelector(params){
        var self = this;
        self.params = params || defaultProps;
    }
    
    return {viewModel: TabSelector, template: htmlString};
});
