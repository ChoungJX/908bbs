function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}

function get_bbs(page){
    var sid = GetQueryString("sid");
    var data = {"api":"admin_get_bbs","page":page,"sid":sid};
    $.ajax( {
        url:"/api_admin_get_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#atable").find("tbody").empty();
                var tby = $("#atable").find("tbody");
                for (x in data.data){
                    var atr = $("<tr></tr>");
                    var cb = $("<input>").attr("type","checkbox");
                    cb.attr("id",data.data[x].bid);
                    atr.append($("<td></td>").append(cb));
                    atr.append(
                        $("<td></td>").text(data.data[x].title)
                    );
                    atr.append(
                        $("<td></td>").text(data.data[x].username)
                    );
                    atr.append(
                        $("<td></td>").text(data.data[x].time)
                    );
                    atr.append(
                        $("<td></td>").text(data.data[x].sname)
                    );
                    tby.append(atr);
                }
            }else if (data.status == "-1"){
                alert("找不到用户");
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function page_go(page){
    var sid = GetQueryString("sid");
    var data = {"api":"api_person_get_bbs_number","sid":sid};
    $.ajax( {
        url:"/api_admin_get_bbs_number",
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


function del_bbs(){
    var data = {"api":"del_bbs","delData":[]};
    var one_table = $("#atable").find("tbody");
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

page_go(1);