function get_person(page){
    var data = {"api":"get_access","page":page};

    $.ajax( {
        url:"/api_admin_get_person",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#table_start").empty();
                for(x in data.data){
                    var one = $("<tr></tr>");
                    one.append(
                        $("<td></td>").text(data.data[x].username)
                    );

                    one.append(
                        $("<td></td>").text(data.data[x].firsttime)
                    );

                    one.append(
                        $("<td></td>").text(data.data[x].lasttime)
                    );

                    one.append(
                        $("<td></td>").text(data.data[x].type)
                    );

                    var one_tr = $("<td></td>");
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-warning btn-sm");
                    one_button.attr("onclick","full_type_form(\""+data.data[x].type+"\",\""+data.data[x].uid+"\")");
                    one_button.text("更改权限")
                    one_tr.append(
                       one_button
                    )
                    one.append(one_tr);
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-danger btn-sm");
                    one_button.attr("onclick","change_status(\""+data.data[x].uid+"\")");
                    one_button.text("禁封/解除")
                    one_tr.append(
                        one_button
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

function page_go(page){
    var data = {"api":"api_person_get_bbs_number"}
    $.ajax( {
        url:"/api_admin_get_person_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_person(page);
                var onepage_model = $("#one_page_model").clone();
                $("#page_model").empty();
                var onepage = onepage_model.clone();
                onepage.children().text(page);
                onepage.attr("class","page-item active");
                $("#page_model").prepend(onepage);

                var pre_flag = 3;
                var pre_page = page - 1
                while (pre_page>=1 && pre_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(pre_page);
                    onepage.children().attr("onclick","page_go("+pre_page+")");
                    onepage.attr("class","page-item");
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("onclick","page_go("+a_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("onclick","page_go("+nx_page+")");
                    onepage.attr("class","page-item");
                    nx_flag = nx_flag-1;
                    if((nx_page-1)*data.onepage >= data.number){
                        break;
                    }
                    nx_page = nx_page +1;
                    $("#page_model").append(onepage);
                }

                if(page*data.onepage < data.number){
                    var onepage = onepage_model.clone();
                    onepage.children().text("下一页")
                    var b_page = parseInt(page) + 1;
                    onepage.children().attr("onclick","page_go("+b_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model").append(onepage);
                }
                
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function full_type_form(tpye,uid){
    $("#uuu_type").val(parseInt(tpye));
    $("#uid").val(uid);

    $("#modal-268478").click();
}

function change_type(){
    var uid = $("#uid").val();
    var type = $("#uuu_type").val();
    var data = {"uid":uid,"type":""+type};
    $.ajax( {
        url:"/api_admin_change_person_type",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }else if (data.status == "-1"){
                alert("未知错误");
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function change_status(uid){
    var data = {"uid":uid};
    $.ajax( {
        url:"/api_admin_inhibit_person",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }else if (data.status == "-1"){
                alert("未知错误");
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}


page_go(1);