const server = "http://localhost:18080/";
function getServerUrl(url){
    if (url.indexOf("/") === 0) {
        url = url.replace(/^[/]+/, "");
    }
    
    url = server + url;
    return url;
}
function ajax(options){
    var method = options.method || "GET";
    var type = options.type || "text/plain";
    var url = getServerUrl(options.url || "");
    var success = options.success;
    var complete = options.complete;
    var data = options.data;
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", type);
    xhr.withCredentials = true;
    xhr.send(data);

    var XHR_DONE = 4;
    var RESPONSE_OK = 200;
    
    xhr.onreadystatechange = function(){
        if (xhr.readyState === XHR_DONE) {
            if (xhr.status === RESPONSE_OK) {
                if (isFunction(success)) {
                    success(xhr);
                }
            }

            if (isFunction(complete)) {
                complete(xhr);
            }
        }
    };

    function isFunction(o){
        return typeof o === "function";
    }
}

export default ajax;

