function get_bbs_info(){
    $.ajax( {
        url:"/api_get_bbsinfo",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"get_bbsinfo"}),
        dataType: "json",
        //processData:false,
        //contentType: false,
        
        success: function(data){
            if (data.status == "1"){
                var get_model = $("#card-968229").clone();
                $("#card-968229").empty();
                for(x in data.area){
                    var one_card = get_model.clone().children();
                    var areaName = data.area[x].areaName
                    one_card.find("#area_name").text(areaName);
                    var one_spare = one_card.clone().find("#spare_info");
                    one_card.find("#spare_info").remove();

                    for(y in data.spare[areaName]){
                        one_spare
                        data.spare[areaName][y]
                        var get_spare = one_spare.clone();
                        get_spare.find("#spare_name").text(data.spare[areaName][y].spareName);
                        get_spare.find("#spare_paper").text(data.spare[areaName][y].paperclip);
                        get_spare.find("#spare_url").attr("href","/bbs_view?spare="+data.spare[areaName][y].spareId);
                        //get_spare.find("#spare_url").text("点击进入");

                        one_card.find("#spare_add").append(get_spare);
                    }

                    $("#card-968229").append(one_card);
                }
            }
        },
        error: function(){
    
        }
    });
}

function show_info(){
    var data = {"api":"del_bbs"};

    $.ajax( {
        url:"/api_admin_get_info",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                $("#info_title").text(data.info.title);
                $("#info_content").html(data.info.content);
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}


function get_access(){
    var data = {"api":"admin_get_recommon"}

    $.ajax( {
        url:"/api_admin_get_recommon",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            var ainner = $("#ainner").clone();
            var flag = "0";
            $("#inner_start").empty();
            $("#inner_index").empty();
            if (data.status == "1"){
                for(x in data.data){
                    var ali = $("<li></li>");
                    ali.attr("data-slide-to",""+x);
                    ali.attr("data-target","#carousel-488418");
                    $("#inner_index").append(
                        ali
                    );
                    var inner_model = ainner.clone();
                    inner_model.find("#inner_img").attr("src",data.data[x].pic);
                    //inner_model.find("#inner_img").attr("width","140");
                    //inner_model.find("#inner_img").attr("height","140");
                    inner_model.find("#inner_title").text(data.data[x].title);
                    inner_model.find("#inner_content").text(data.data[x].content);
                    if(flag=="0"){
                        inner_model.attr("class","carousel-item active");
                        flag = "1";
                    }
                    $("#inner_start").append(inner_model);
                }
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}


function get_hot_bbs(){
    var data = {"api":"admin_get_recommon"}

    $.ajax( {
        url:"/api_index_get_hot_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                $("#hot_table").find("tbody").empty();
                for(x in data.data){
                    var one_table = $("#hot_table").find("tbody");
                    var one_tr = $("<tr></tr>");
                    var aaa = $("<a></a>")
                    aaa.attr("href","/one_bbs?bid="+data.data[x].bid);
                    aaa.text(data.data[x].title);
                    one_tr.append(
                        $("<td></td>").append(aaa)
                    );
                    one_tr.append(
                        $("<td></td>").append(data.data[x].username)
                    );

                    one_tr.append(
                        $("<td></td>").append(data.data[x].sname)
                    );

                    one_tr.append(
                        $("<td></td>").append(data.data[x].number)
                    );
                    $("#hot_table").find("tbody").append(one_tr);
                }
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

get_access();
show_info();
get_hot_bbs();
get_bbs_info();