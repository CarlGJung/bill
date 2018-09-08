requirejs.config({
    //By default load any module IDs from js/lib
    baseUrl: "/",
    //except, if the module ID starts with "app",
    //load it from the js/app directory. paths
    //config is relative to the baseUrl, and
    //never includes a ".js" extension since
    //the paths config could be for a directory.
    paths: {
        "text": "require/text",
        "css": "require/css",
        "structure": "src/js/structure"
    },
    shim:{
        "structure":{
            deps: ["protobufjs/light"]
        }
    }
});

//保证所有的knockout实例都是同一个引用
define("knockout",[],function(){
    return ko;
});

define("protobufjs/light",[],function(){
    return protobuf;
});


