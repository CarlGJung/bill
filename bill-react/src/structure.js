/*eslint-disable block-scoped-var, no-redeclare, no-control-regex, no-prototype-builtins*/
import * as $protobuf from "protobufjs/light";

const $root = ($protobuf.roots.pbStore || ($protobuf.roots.pbStore = new $protobuf.Root()))
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
      DEFAULT: 0,
      INCOME: 1,
      PAYMENT: 2
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
      }
    }
  },
  BillList: {
    fields: {
      bills: {
        rule: "repeated",
        type: "Bill",
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
      billType: {
        type: "int32",
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
      remark: {
        type: "string",
        id: 5
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

export { $root as default };
