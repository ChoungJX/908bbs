function login(){
    var username = document.getElementById("login_username").value;
    var password = document.getElementById("login_password").value;

    var data = {"api":"login","username":username,"password":password}

    $.ajax( {
        url:"/api_admin_login",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
                if (data.status == "1"){
                        window.location.href="/admin_index";
                }else if(data.status == "-1"){
                        alert("密码错误！");
                }else if(data.status == "0"){
                        alert("您没有权限访问该网站！");
                }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}
    