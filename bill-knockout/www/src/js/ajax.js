const server =  window.location.protocol + "//" + window.location.host;
function getServerUrl(url){
    if (url.indexOf("/") !== 0) {
        url = "/" + url;
    }
    
    url = server + url;
    return url;
}

function ajax(options){
    var method = options.method || "GET";
    var type = options.type || "text/plain";
    var accept = options.accept || "text/plain";
    var url = getServerUrl(options.url || "");
    var success = options.success;
    var complete = options.complete;
    var data = options.data;
    var xhr = new XMLHttpRequest();
    xhr.open(method, url, true);
    xhr.setRequestHeader("Content-Type", type);
    xhr.setRequestHeader("Accept", accept);
    xhr.responseType = accept.indexOf("text/") > 0 ? "text" : "arraybuffer";
    xhr.withCredentials = true;
    xhr.send(data);

    var XHR_DONE = 4;
    var RESPONSE_OK = 200;
    var RESPONSE_FORBIDDEN = 403;
    
    xhr.onreadystatechange = function(){
        if (xhr.readyState === XHR_DONE) {
            var data = xhr.response;
            if (xhr.responseType === "arraybuffer") {
                data = new Uint8Array(data);
            }
            
            switch(xhr.status){
                case RESPONSE_OK:
                    if (success) {
                        success(data, xhr);
                    }
                    break;
                case RESPONSE_FORBIDDEN:
                    window.location.hash = "#";
                    break;
                default:;    
            }

            if (complete) {
                complete(data, xhr);
            }
            
        }
    };
}
