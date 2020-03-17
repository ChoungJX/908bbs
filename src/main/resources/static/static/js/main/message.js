



function get_m_no(page){
    $.ajax( {
        url:"/api_message_receive_no",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"message_receive_no","page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
        
        success: function(data){
            if (data.status == "1"){
                $("#no_read").empty();
                for(i in data.message){
                    var tr=$("<tr class='table-active'></tr>");
                    tr.append($("<td></td>").text(data.message[i].mTitle));
                    tr.append($("<td></td>").text(data.message[i].createTime));
                    tr.append($("<td></td>").text("未读"));
                    gropuB = $('<div class="btn-group" role="group"></div>');
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("查看");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/one_message?mId="+data.message[i].mId);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("删除");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/delete_message?mId="+data.message[i].mId);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tr.append(gropuB);
    
                    $("#no_read").append(tr);
                }
            }
        },
        error: function(){
    
        }
    });
}


function page_go(page){
    var data = {"api":"api_person_get_bbs_number"}
    $.ajax( {
        url:"/api_message_receive_no_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_m_no(page);
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



function get_m_yes(page){
    $.ajax( {
        url:"/api_message_receive_yes",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"message_receive_yes","page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
        
        success: function(data){
            if (data.status == "1"){
                $("#read").empty();
                for(i in data.message){
                    var tr=$("<tr></tr>");
                    tr.append($("<td></td>").text(data.message[i].mTitle));
                    tr.append($("<td></td>").text(data.message[i].createTime));
                    tr.append($("<td></td>").text("已读"));
                    gropuB = $('<div class="btn-group" role="group"></div>');
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("查看");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/one_message?mId="+data.message[i].mId);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("删除");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/delete_message?mId="+data.message[i].mId);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tr.append(gropuB);
                    $("#read").append(tr);
                }
            }
        },
        error: function(){
    
        }
    });
}

function page_go2(page){
    var data = {"api":"/api_message_receive_yes_number"}
    $.ajax( {
        url:"/api_message_receive_yes_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_m_yes("1");
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
                    onepage.children().attr("onclick","page_go2("+pre_page+")");
                    onepage.attr("class","page-item");
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model2").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("onclick","page_go2("+a_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model2").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("onclick","page_go2("+nx_page+")");
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
                    onepage.children().attr("onclick","page_go2("+b_page+")");
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


function get_send_yes(page){
    $.ajax( {
        url:"/api_message_send_yes",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"message_send_yes","page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
        
        success: function(data){
            $("#send_yes").empty();
            if (data.status == "1"){
                for(i in data.message){
                    var tr=$("<tr></tr>");
                    tr.append($("<td></td>").text(data.message[i][0]));
                    tr.append($("<td></td>").text(data.message[i][1]));
                    tr.append($("<td></td>").text(data.message[i][2]));
                    tr.append($("<td></td>").text("已发送"));
                    console.log(data.message[i][0]);
                    gropuB = $('<div class="btn-group" role="group"></div>');
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("查看");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/one_message?mId="+data.message[i][3]);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tempB = $("<button></button>");
                    tempB.attr("class","btn btn-secondary");
                    tempB.attr("type","button");
                    tempB.text("删除");
                    tempAA = $("<a></a>");
                    tempAA.attr("href","/delete_message?mId="+data.message[i][3]);
                    tempAA.append(tempB);
                    gropuB.append(tempAA);
    
                    tr.append(gropuB);
    
                    $("#send_yes").append(tr);
                }
            }
        },
        error: function(){
    
        }
    });
}

function page_go3(page){
    var data = {"api":"api_message_send_yes_number"}
    $.ajax( {
        url:"/api_message_send_yes_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_send_yes(page);
                var onepage_model = $("#one_page_model3").clone();
                $("#page_model3").empty();
                var onepage = onepage_model.clone();
                onepage.children().text(page);
                onepage.attr("class","page-item active");
                $("#page_model3").prepend(onepage);

                var pre_flag = 3;
                var pre_page = page - 1
                while (pre_page>=1 && pre_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(pre_page);
                    onepage.children().attr("onclick","page_go3("+pre_page+")");
                    onepage.attr("class","page-item");
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model3").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("onclick","page_go3("+a_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model3").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("onclick","page_go3("+nx_page+")");
                    onepage.attr("class","page-item");
                    nx_flag = nx_flag-1;
                    if((nx_page-1)*data.onepage >= data.number){
                        break;
                    }
                    nx_page = nx_page +1;
                    $("#page_model3").append(onepage);
                }

                if(page*data.onepage < data.number){
                    var onepage = onepage_model.clone();
                    onepage.children().text("下一页")
                    var b_page = parseInt(page) + 1;
                    onepage.children().attr("onclick","page_go3("+b_page+")");
                    onepage.attr("class","page-item");
                    $("#page_model3").append(onepage);
                }
                
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function sendMessage(){
    var receiveUser = document.getElementById("receiveUser").value;
    var title = document.getElementById("sendTitle").value;
    var content = document.getElementById("sendContent").value;
    var status = "0";

    data = {"api":"message_send","username":receiveUser,"mTitle":title,"mContent":content,"mStatus":status}
    $.ajax( {
        url:"/api_message_send",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            aaa = document.getElementById("iflogin");
            if (data.status == "1"){
                $("#receiveUser").val("");
                $("#sendTitle").val("");
                $("#sendContent").val("");

                
            }else if(data.status == "0"){
                alert("用户不存在！");
            }else if(data.status == "-2"){
                alert("登录失效！");
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

page_go(1);

page_go2(1);

$("#click_send_yes").click(function(){
    page_go3(1);
})
