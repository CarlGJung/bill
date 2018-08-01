/*eslint-disable block-scoped-var, no-redeclare, no-control-regex, no-prototype-builtins*/
import * as $protobuf from "protobufjs/light";

const $root = ($protobuf.roots.structure || ($protobuf.roots.structure = new $protobuf.Root()))
.setOptions({
  java_package: "top.carljung.bill.proto",
  java_outer_classname: "StructureStore"
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
      type: {
        type: "BillType",
        id: 1
      },
      label: {
        type: "int32",
        id: 2
      }
    }
  },
  Label: {
    fields: {
      id: {
        type: "int32",
        id: 1
      },
      name: {
        type: "string",
        id: 2
      }
    }
  }
});

export { $root as default };
