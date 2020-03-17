function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


function getMessage(){
    var getId = GetQueryString("mId");
    $.ajax( {
        url:"/api_oneMessage",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"oneMessage","mId":getId}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            aaa = document.getElementById("iflogin");
            if (data.status == "1"){
                $("#Mtitle").text(data.title);
                $("#Mcontent").text(data.content);
                $("#Suser").text("发送人:"+data.sendUser);
                $("#Ruser").text("收件人:"+data.receiveUser);
            }else{

            }
        },
        error: function(){

        }
    });
}

getMessage()