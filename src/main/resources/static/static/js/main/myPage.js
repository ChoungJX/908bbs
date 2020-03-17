function get_bbs(page){
    var data = {"api":"api_person_get_bbs","page":page}

    $.ajax( {
        url:"/api_person_get_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#bbs_table").find("tbody").empty();
                for(x in data.data){
                    var one_table = $("#bbs_table").find("tbody");
                    
                    var one = $("<tr></tr>");
                    var cb = $("<input>").attr("type","checkbox");
                    cb.attr("id",data.data[x].bbsID);
                    one.append($("<td></td>").append(cb));
                    one.append($("<td></td>").text(data.data[x].bbsName));
                    one.append($("<td></td>").text(data.data[x].bbsTime));
                    one.append($("<td></td>").text(data.data[x].bbsArea));
                    one.append($("<td></td>").text(data.data[x].bbsSpare));

                    var one_tr = $("<td></td>");
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-warning btn-sm");
                    one_button.text("点击进入")
                    one_tr.append(
                        $("<a></a>").attr("href","/one_bbs?bid="+data.data[x].bbsID).append(one_button)
                    )
                    one.append(one_tr);

                    one_table.append(one);
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
        url:"/api_person_get_bbs_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_bbs(page);
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

function get_receive(page){
    var data = {"api":"api_person_get_receive","page":page}

    $.ajax( {
        url:"/api_person_get_receive",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#re_table").find("tbody").empty();
                for(x in data.data){
                    var one_table = $("#re_table").find("tbody");
                    
                    var one = $("<tr></tr>");
                    var cb = $("<input>").attr("type","checkbox");
                    cb.attr("id",data.data[x].bbsID);
                    one.append($("<td></td>").append(cb));
                    one.append($("<td></td>").text(data.data[x].bbsTitle));
                    one.append($("<td></td>").html(data.data[x].reContent));
                    one.append($("<td></td>").text(data.data[x].reTime));
                    one.append($("<td></td>").text(data.data[x].bbsArea));
                    one.append($("<td></td>").text(data.data[x].bbsSpare));

                    var one_tr = $("<td></td>");
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-warning btn-sm");
                    one_button.text("点击进入")
                    one_tr.append(
                        $("<a></a>").attr("href","/one_bbs?bid="+data.data[x].bbsId).append(one_button)
                    )
                    one.append(one_tr);

                    one_table.append(one);
                }

            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}


function page_go_re(page){
    var data = {"api":"api_person_get_receive_number"}
    $.ajax( {
        url:"/api_person_get_receive_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_receive(page);
                var onepage_model = $("#one_page_model2").clone();
                $("#page_model2").empty();
                var onepage = onepage_model.clone();
                onepage.children().text(page);
                onepage.attr("class","page-item active");
                $("#page_model2").prepend(onepage);

                var pre_flag = 3;
                var pre_page = page - 1
                while (pre_page>=1 && pre_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(pre_page);
                    onepage.children().attr("onclick","page_go_re("+pre_page+")");
                    onepage.attr("class","page-item");
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model2").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("onclick","page_go_re("+a_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model2").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("onclick","page_go_re("+nx_page+")");
                    onepage.attr("class","page-item");
                    nx_flag = nx_flag-1;
                    if((nx_page-1)*data.onepage >= data.number){
                        break;
                    }
                    nx_page = nx_page +1;
                    $("#page_model2").append(onepage);
                }

                if(page*data.onepage < data.number){
                    var onepage = onepage_model.clone();
                    onepage.children().text("下一页")
                    var b_page = parseInt(page) + 1;
                    onepage.children().attr("onclick","page_go_re("+b_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model2").append(onepage);
                }
                
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}


function del_bbs(){
    var data = {"api":"del_bbs","delData":[]};
    var one_table = $("#bbs_table").find("tbody");
    var get_data = one_table.find("input");

    for (x in get_data){
        if(get_data[x].checked){
            data.delData.push(get_data[x].id);
        }
    }

    $.ajax( {
        url:"/api_delete_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }else if(data.status == "0"){
                alert("不是您的帖子，删除失败！");
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}


function del_re(){
    var data = {"api":"del_re","delData":[]};
    var one_table = $("#re_table").find("tbody");
    var get_data = one_table.find("input");

    for (x in get_data){
        if(get_data[x].checked){
            data.delData.push(get_data[x].id);
        }
    }

    $.ajax( {
        url:"/api_delete_receive",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }else if(data.status == "0"){
                alert("不是您的回复，删除失败！");
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}


page_go(1);

$("#click_receive").click(function(){
    page_go_re(1);
})