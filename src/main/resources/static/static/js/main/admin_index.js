function get_access(){
    var data = {"api":"get_access"}

    $.ajax( {
        url:"/api_get_admin_access",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                for(x in data.data){
                    var one = $("<tr></tr>");
                    one.attr("class","table-success");
                    one.append($("<td></td>").text(data.data[x].accessName));

                    one.append($("<td></td>").text(">="+data.data[x].accessWeight));

                    var one_tr = $("<td></td>");
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-warning btn-sm");
                    one_button.text("点击进入")
                    one_tr.append(
                        $("<a></a>").attr("href",data.data[x].accessUrl).append(one_button)
                    )
                    one.append(one_tr);

                    $("#table_start").append(one);

                }
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

get_access();
    