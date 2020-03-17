function get_info(){
    var data = {"api":"get_access"}

    $.ajax( {
        url:"/api_get_admin_area",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                var acard=$("#a_card").clone();
                $("#card-757487").empty();
                for(x in data.areaData){
                    var each_card = acard.clone();
                    each_card.find("#name").text("板块名:"+data.areaData[x].aname+"@管辖人:"+data.areaData[x].username);
                    each_card.find("#change_area_info").attr("onclick","full_area_form(\""+data.areaData[x].aname+"\",\""+data.areaData[x].username+"\",\""+data.areaData[x].aid+"\",\"change\")");
                    each_card.find("#delete_area_info").attr("onclick","del_area(\""+data.areaData[x].aid+"\")");
                    each_card.find("#create_spare_info").attr("onclick","clean_spare_form(\""+data.areaData[x].aid+"\")");
                    var tby = each_card.find("tbody");
                    for(y in data.spareData[x]){
                        var onetr = $("<tr></tr>");
                        onetr.append(
                            $("<td></td>").text(data.spareData[x][y].sname)
                        );
                        onetr.append(
                            $("<td></td>").text(data.spareData[x][y].username)
                        );
                        onetr.append(
                            $("<td></td>").text(data.spareData[x][y].info)
                        );

                        var newtd = $("<td></td>")
                        var abutton = $("<button></button>");
                        abutton.attr("class","btn btn-primary btn-sm")
                        abutton.attr("onclick","full_spare_form(\""+data.spareData[x][y].sname+"\",\""+data.spareData[x][y].username+"\",\""+data.spareData[x][y].info+"\",\""+data.spareData[x][y].sid+"\",\"change\")")
                        abutton.text("修改分区信息");
                        newtd.append(abutton);
                        var abutton = $("<button></button>");
                        abutton.attr("class","btn btn-danger btn-sm")
                        abutton.attr("onclick","del_spare(\""+data.spareData[x][y].sid+"\")")
                        abutton.text("删除分区");
                        newtd.append(abutton);
                        onetr.append(newtd);

                        tby.append(onetr);
                    }
                    
                    $("#card-757487").append(each_card);
                }
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function upload_area(){
    var aname = $("#a_aname").val();
    var username = $("#a_username").val();
    var aid = $("#a_aid").val();

    var action = $("#a_action").val();
    if (action == "change"){
        var data = {"aid":aid,"username":username,"aname":aname};

        $.ajax( {
            url:"/api_admin_change_area",
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
                    alert("找不到用户");
                }
            },
            error: function(){
            alert("服务器连接失败！");
            }
        });
        } else{
            var data = {"username":username,"areaName":aname};
            $.ajax( {
                url:"/api_admin_create_area",
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
                        alert("找不到用户");
                    }
                },
                error: function(){
                alert("服务器连接失败！");
                }
            });
    }
}

function upload_spare(){
    var aname = $("#s_aname").val();
    var username = $("#s_username").val();
    var info = $("#s_info").val();
    var aid = $("#s_aid").val();
    var said = $("#s_aaid").val();

    var action = $("#s_action").val();
    if (action == "change"){
        var data = {"sid":aid,"username":username,"sname":aname,"info":info};

        $.ajax( {
            url:"/api_admin_change_spare",
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
                    alert("找不到用户");
                }
            },
            error: function(){
            alert("服务器连接失败！");
            }
        });
        } else{
            var data = {"username":username,"spareName":aname,"subordinateAreaId":said,"info":info};
            $.ajax( {
                url:"/api_admin_create_spare",
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
                        alert("找不到用户");
                    }
                },
                error: function(){
                alert("服务器连接失败！");
                }
            });
    }
}

function full_area_form(aname,username,aid,action){
    $("#a_aname").val(aname);
    $("#a_username").val(username);
    $("#a_aid").val(aid);
    $("#a_action").val(action);

    $("#change_area_model").click();
}

function full_spare_form(aname,username,info,aid,action){
    $("#s_aname").val(aname);
    $("#s_username").val(username);
    $("#s_info").val(info);
    $("#s_aid").val(aid);
    $("#s_action").val(action);

    $("#change_spare_model").click();
}

function clean_area_form(){
    $("#a_aname").val("");
    $("#a_username").val("");
    $("#a_aid").val("");
    $("#a_action").val("create");

    $("#change_area_model").click();
}

function clean_spare_form(aid){
    $("#s_aname").val("");
    $("#s_username").val("");
    $("#s_info").val("");
    $("#s_aid").val("");
    $("#s_action").val("create");
    $("#s_aaid").val(aid);

    $("#change_spare_model").click();
}

function del_area(aid){
    var data = {"aid":aid};
    $.ajax( {
        url:"/api_admin_delete_area",
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
                alert("找不到用户");
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function del_spare(sid){
    var data = {"sid":sid};
    $.ajax( {
        url:"/api_admin_delete_spare",
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
                alert("找不到用户");
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

get_info();