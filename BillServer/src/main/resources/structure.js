/*eslint-disable block-scoped-var, no-redeclare, no-control-regex, no-prototype-builtins*/
(function(global, factory) { /* global define, require, module */

    /* AMD */ if (typeof define === 'function' && define.amd)
        define(["protobufjs/minimal"], factory);

    /* CommonJS */ else if (typeof require === 'function' && typeof module === 'object' && module && module.exports)
        module.exports = factory(require("protobufjs/minimal"));

})(this, function($protobuf) {
    "use strict";

    var $Reader = $protobuf.Reader, $Writer = $protobuf.Writer, $util = $protobuf.util;
    
    var $root = $protobuf.roots.structure || ($protobuf.roots.structure = {});
    
    $root.BillType = (function() {
        var valuesById = {}, values = Object.create(valuesById);
        values[valuesById[0] = "DEFAULT"] = 0;
        values[valuesById[1] = "INCOME"] = 1;
        values[valuesById[2] = "PAYMENT"] = 2;
        return values;
    })();
    
    $root.Bill = (function() {
    
        function Bill(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }
    
        Bill.prototype.type = 0;
        Bill.prototype.label = 0;
    
        Bill.create = function create(properties) {
            return new Bill(properties);
        };
    
        Bill.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.type != null && message.hasOwnProperty("type"))
                writer.uint32(8).int32(message.type);
            if (message.label != null && message.hasOwnProperty("label"))
                writer.uint32(16).int32(message.label);
            return writer;
        };
    
        Bill.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };
    
        Bill.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.Bill();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.type = reader.int32();
                    break;
                case 2:
                    message.label = reader.int32();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };
    
        Bill.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };
    
        Bill.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.type != null && message.hasOwnProperty("type"))
                switch (message.type) {
                default:
                    return "type: enum value expected";
                case 0:
                case 1:
                case 2:
                    break;
                }
            if (message.label != null && message.hasOwnProperty("label"))
                if (!$util.isInteger(message.label))
                    return "label: integer expected";
            return null;
        };
    
        Bill.fromObject = function fromObject(object) {
            if (object instanceof $root.Bill)
                return object;
            var message = new $root.Bill();
            switch (object.type) {
            case "DEFAULT":
            case 0:
                message.type = 0;
                break;
            case "INCOME":
            case 1:
                message.type = 1;
                break;
            case "PAYMENT":
            case 2:
                message.type = 2;
                break;
            }
            if (object.label != null)
                message.label = object.label | 0;
            return message;
        };
    
        Bill.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.type = options.enums === String ? "DEFAULT" : 0;
                object.label = 0;
            }
            if (message.type != null && message.hasOwnProperty("type"))
                object.type = options.enums === String ? $root.BillType[message.type] : message.type;
            if (message.label != null && message.hasOwnProperty("label"))
                object.label = message.label;
            return object;
        };
    
        Bill.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };
    
        return Bill;
    })();
    
    $root.Label = (function() {
    
        function Label(properties) {
            if (properties)
                for (var keys = Object.keys(properties), i = 0; i < keys.length; ++i)
                    if (properties[keys[i]] != null)
                        this[keys[i]] = properties[keys[i]];
        }
    
        Label.prototype.id = 0;
        Label.prototype.name = "";
    
        Label.create = function create(properties) {
            return new Label(properties);
        };
    
        Label.encode = function encode(message, writer) {
            if (!writer)
                writer = $Writer.create();
            if (message.id != null && message.hasOwnProperty("id"))
                writer.uint32(8).int32(message.id);
            if (message.name != null && message.hasOwnProperty("name"))
                writer.uint32(18).string(message.name);
            return writer;
        };
    
        Label.encodeDelimited = function encodeDelimited(message, writer) {
            return this.encode(message, writer).ldelim();
        };
    
        Label.decode = function decode(reader, length) {
            if (!(reader instanceof $Reader))
                reader = $Reader.create(reader);
            var end = length === undefined ? reader.len : reader.pos + length, message = new $root.Label();
            while (reader.pos < end) {
                var tag = reader.uint32();
                switch (tag >>> 3) {
                case 1:
                    message.id = reader.int32();
                    break;
                case 2:
                    message.name = reader.string();
                    break;
                default:
                    reader.skipType(tag & 7);
                    break;
                }
            }
            return message;
        };
    
        Label.decodeDelimited = function decodeDelimited(reader) {
            if (!(reader instanceof $Reader))
                reader = new $Reader(reader);
            return this.decode(reader, reader.uint32());
        };
    
        Label.verify = function verify(message) {
            if (typeof message !== "object" || message === null)
                return "object expected";
            if (message.id != null && message.hasOwnProperty("id"))
                if (!$util.isInteger(message.id))
                    return "id: integer expected";
            if (message.name != null && message.hasOwnProperty("name"))
                if (!$util.isString(message.name))
                    return "name: string expected";
            return null;
        };
    
        Label.fromObject = function fromObject(object) {
            if (object instanceof $root.Label)
                return object;
            var message = new $root.Label();
            if (object.id != null)
                message.id = object.id | 0;
            if (object.name != null)
                message.name = String(object.name);
            return message;
        };
    
        Label.toObject = function toObject(message, options) {
            if (!options)
                options = {};
            var object = {};
            if (options.defaults) {
                object.id = 0;
                object.name = "";
            }
            if (message.id != null && message.hasOwnProperty("id"))
                object.id = message.id;
            if (message.name != null && message.hasOwnProperty("name"))
                object.name = message.name;
            return object;
        };
    
        Label.prototype.toJSON = function toJSON() {
            return this.constructor.toObject(this, $protobuf.util.toJSONOptions);
        };
    
        return Label;
    })();

    return $root;
});
