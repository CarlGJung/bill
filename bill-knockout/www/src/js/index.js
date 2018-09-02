ajax({url: "bills/bills", accept: "application/x-protobuf", success: (data, xhr)=>{
    console.log(data);
}});

