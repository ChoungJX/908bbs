function get_info(){
    var data = {"api":"api_person_info"}

    $.ajax( {
        url:"/api_person_info",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#welcome").text("欢迎来到908bbs!"+data.data.username);
                $("#bbs_table").find("tbody").empty();
                var one_table = $("#bbs_table").find("tbody");
                
                var one = $("<tr></tr>");
                one.append($("<td></td>").text("上次登录IP"));
                one.append($("<td></td>").text(data.data.ip));
                one_table.append(one);

                var one = $("<tr></tr>");
                one.append($("<td></td>").text("上次登录时间"));
                one.append($("<td></td>").text(data.data.lastTime));
                one_table.append(one);

                var one = $("<tr></tr>");
                one.append($("<td></td>").text("注册时间"));
                one.append($("<td></td>").text(data.data.enrollTime));
                one_table.append(one);

                var one = $("<tr></tr>");
                one.append($("<td></td>").text("908bbs唯一指定id"));
                one.append($("<td></td>").text(data.data.uId));
                one_table.append(one);

                var one = $("<tr></tr>");
                one.append($("<td></td>").text("邮箱"));
                one.append($("<td></td>").text(data.data.email));
                one_table.append(one);


            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function save(){
    var password = document.getElementById("password").value;
    var password2 = document.getElementById("password2").value;

    var data = {"api":"change_info"};
    
    if(password != password2){
            alert("输入的两次密码不一致！");
            aaa = document.getElementById("password");
            aaa.value = "";
            bbb = document.getElementById("password2");
            bbb.value = "";
            return;
    }
    
    var email = document.getElementById("email").value;
    
    if(!email && !password){
        alert("你没有修改任何数据！");
        return;
    }

    if (email){
        data.email = email;
    }
    if (password){
        data.password = password;
    }
    $.ajax( {
            url:"/api_change_info",
            type: "POST",
            contentType: "application/json;charset=UTF-8;",
            data: JSON.stringify(data),
            dataType: "json",
            //processData:false,
            //contentType: false,
    
            success: function(data){
                    if (data.status == "1"){
                            window.location.href="/mySetting";
                    }else if(data.status == "-1"){
                            alert("输入数据格式错误！");
                    }else if(data.status == "0"){
                            alert("用户名已注册！");
                    }
            },
            error: function(){
            alert("服务器连接失败！");
            }
    });
    }

get_info();