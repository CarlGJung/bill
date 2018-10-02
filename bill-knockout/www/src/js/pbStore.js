/*eslint-disable block-scoped-var, no-redeclare, no-control-regex, no-prototype-builtins*/
(function(global, factory) { /* global define, require, module */

    /* AMD */ if (typeof define === 'function' && define.amd)
        define(["protobufjs/light"], factory);

    /* CommonJS */ else if (typeof require === 'function' && typeof module === 'object' && module && module.exports)
        module.exports = factory(require("protobufjs/light"));

})(this, function($protobuf) {
    "use strict";

    var $root = ($protobuf.roots.pbStore || ($protobuf.roots.pbStore = new $protobuf.Root()))
    .setOptions({
      java_package: "top.carljung.bill.proto",
      java_outer_classname: "PBStore"
    })
    .addJSON({
      User: {
        fields: {
          userId: {
            type: "int32",
            id: 1
          },
          username: {
            type: "string",
            id: 2
          },
          password: {
            type: "string",
            id: 3
          }
        }
      },
      BillType: {
        values: {
          UNKNOW_BillType: 0,
          INCOME: 1,
          PAYMENT: 2
        }
      },
      DataState: {
        values: {
          UNKNOW_DataState: 0,
          ACTIVED: 1,
          DELETED: 2
        }
      },
      Bill: {
        fields: {
          id: {
            type: "int32",
            id: 1
          },
          type: {
            type: "BillType",
            id: 2
          },
          labelId: {
            type: "int32",
            id: 3
          },
          money: {
            type: "double",
            id: 4
          },
          time: {
            type: "int64",
            id: 5
          }
        }
      },
      BillDaily: {
        fields: {
          date: {
            type: "string",
            id: 1
          },
          income: {
            type: "double",
            id: 2
          },
          payment: {
            type: "double",
            id: 3
          },
          bills: {
            rule: "repeated",
            type: "Bill",
            id: 4
          }
        }
      },
      BillDailyList: {
        fields: {
          billDailies: {
            rule: "repeated",
            type: "BillDaily",
            id: 1
          }
        }
      },
      BillLabel: {
        fields: {
          id: {
            type: "int32",
            id: 1
          },
          type: {
            type: "BillType",
            id: 2
          },
          name: {
            type: "string",
            id: 3
          },
          color: {
            type: "string",
            id: 4
          },
          icon: {
            type: "string",
            id: 5
          },
          remark: {
            type: "string",
            id: 6
          }
        }
      },
      BillLabelList: {
        fields: {
          labels: {
            rule: "repeated",
            type: "BillLabel",
            id: 1
          }
        }
      }
    });

    return $root;
});
