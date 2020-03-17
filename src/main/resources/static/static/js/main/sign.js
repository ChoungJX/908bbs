var encrypt = new JSEncrypt();
var aaa = $.cookie("public")
encrypt.setPublicKey(aaa);

function login(){
        var username = document.getElementById("login_username").value;
        var password = document.getElementById("login_password").value;
    
        var data = {"api":"login","username":username,"password":password};
        data = JSON.stringify(data);
        data = "{\"requestData\":"+encrypt.encrypt(data)+"}";
        $.ajax( {
            url:"/api_login",
            type: "POST",
            contentType: "application/json;charset=UTF-8;",
            data: data,
            dataType: "json",
            //processData:false,
            //contentType: false,
    
            success: function(data){
                    console.log(data);
                    if (data.status == "1"){
                            window.location.href="/";
                    }else{
                        alert(data.message);
                    }
            },
            error: function(){
                alert("服务器连接失败！");
            }
        });
    }
    
    function enroll(){
        var username = document.getElementById("enroll_username").value;
        var password = document.getElementById("enroll_password").value;
        var password2 = document.getElementById("enroll_password2").value;
    
        if(password != password2){
            alert("输入的两次密码不一致！");
            aaa = document.getElementById("enroll_password");
            aaa.value = "";
            bbb = document.getElementById("enroll_password2");
            bbb.value = "";
            return;
        }
        
        var email = document.getElementById("enroll_email").value;
        var question = document.getElementById("enroll_question").value;
        var answer = document.getElementById("enroll_answer").value;
    
        var data = {"api":"enroll","username":username,"password":password,"email":email,"question":question,"answer":answer}
        data = JSON.stringify(data);
        data = "{\"requestData\":"+encrypt.encrypt(data)+"}";
        $.ajax( {
            url:"/api_enroll",
            type: "POST",
            contentType: "application/json;charset=UTF-8;",
            data: data,
            dataType: "json",
            //processData:false,
            //contentType: false,
    
            success: function(data){
                    if (data.status == "1"){
                            window.location.href="/";
                    }else{
                        alert(data.message);
                    }
            },
            error: function(){
                alert("服务器连接失败！");
            }
        });
    }