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
    var accept = options.accept || "*/*";
    var url = getServerUrl(options.url || "");
    var success = options.success;
    var complete = options.complete;
    var data = options.data;
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", type);
    xhr.setRequestHeader("Accept", accept);
    xhr.withCredentials = true;
    xhr.send(data);

    var XHR_DONE = 4;
    var RESPONSE_OK = 200;
    
    xhr.onreadystatechange = function(){
        if (xhr.readyState === XHR_DONE) {
            var data = null;
            
            if (xhr.status === RESPONSE_OK) {
                data = xhr.response;
                
                if (accept === "application/x-protobuf") {
                    data = str2bytes(data);
                }
                
                if (isFunction(success)) {
                    success(data, xhr);
                }
                if (isFunction(complete)) {
                    complete(data, xhr);
                }
            }

        }
    };
    
    function str2bytes(str){
        var bytes = [];
        for (var i = 0, len = str.length; i < len; ++i) {
            var c = str.charCodeAt(i);
            var byte = c & 0xff;
            bytes.push(byte);
        }
        return bytes;
    }
    
    function isFunction(o){
        return typeof o === "function";
    }
}

export default ajax;

