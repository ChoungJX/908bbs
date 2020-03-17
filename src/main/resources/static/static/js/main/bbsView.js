var E = window.wangEditor;
var editor = new E('#Weditor');
editor.customConfig.uploadImgServer = '/upload_pic';
editor.customConfig.uploadFileName = 'files';
editor.create();


function GetQueryString(name)
{
     var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
     var r = window.location.search.substr(1).match(reg);
     if(r!=null)return  unescape(r[2]); return null;
}


function get_where(){
    var spare = GetQueryString("spare");
    
    $.ajax( {
        url:"/api_get_where",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"get_where","spare":spare}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                $("#area_name").text(data.name.area_name);
                $("#spare_name").text(data.name.spare_name);
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

function get_bbs(){
    var spare = GetQueryString("spare");
    var page = GetQueryString("page");
    var page = parseInt(page);

    if (!Boolean(page)){
        page = 1;
    }

    $.ajax( {
        url:"/api_get_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"get_bbs","spare":spare,"page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                var oneBbs_model = $("#one_bbs").clone();
                $("#one_bbs").remove();

                for(x in data.data){
                    var oneBbs = oneBbs_model.clone();
                    if (data.data[x].flag == "1"){
                        oneBbs.find("#one_title").text(data.data[x].bbsTitle+"@置顶");
                    }else{
                        oneBbs.find("#one_title").text(data.data[x].bbsTitle);
                    }
                    //oneBbs.find("#one_content").text(data.data[x].bbsContent);
                    oneBbs.find("#one_url").attr("href","/one_bbs?bid="+data.data[x].bbsId);
                    oneBbs.find("#username").text("发布者:"+data.data[x].username+"------>");
                    oneBbs.find("#receiveTime").text("最后更新时间:"+data.data[x].lastTime+"------>"+"阅读数量:"+data.data[x].readNumber);
                    oneBbs.find("#top_button").attr("onclick","change_bbs(\""+data.data[x].bbsId+"\",\"1\")");
                    oneBbs.find("#great_button").attr("onclick","change_bbs(\""+data.data[x].bbsId+"\",\"2\")");
                    oneBbs.find("#delete_button").attr("onclick","del_bbs(\""+data.data[x].bbsId+"\")");
                    $("#bbs_start").append(oneBbs);
                }


                
                var onepage_model = $("#one_page_model").clone();
                $("#one_page_model").children().text(page);
                $("#one_page_model").attr("class","page-item active");
                var pre_flag = 3;
                var pre_page = page - 1
                while (pre_page>=1 && pre_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(pre_page);
                    onepage.children().attr("href","/bbs_view?spare="+spare+"&page="+pre_page);
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("href","/bbs_view?spare="+spare+"&page="+a_page);
                    $("#page_model").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("href","/bbs_view?spare="+spare+"&page="+nx_page);
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
                    onepage.children().attr("href","/bbs_view?spare="+spare+"&page="+b_page);
                    $("#page_model").append(onepage);
                }
                
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

function create_bbs(){
    $("#upinfo").text("上传中，请稍后!");
    $("#modal-652263").click();

    var spare = GetQueryString("spare");
    var title = $("#bbsTitle").val();
    var content = editor.txt.html();


    var data = {"api":"create_bbs","spare":spare,"bbsTitle":title,"bbsContent":content};
    
    $.ajax( {
        url:"/api_create_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                submit2(data.bid);
            }else{
                alert(data.message);
                $("#info_close").click();
            }
        },
        error: function(){
            window.location.href="/login";
        }
    });
}

function del_bbs(bid){
    var data = {"api":"del_bbs","delData":[bid]};

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


function get_hot_spare_bbs(){
    var spare = GetQueryString("spare");


    var data = {"api":"get_hot_spare_bbs","spare":spare};
    
    $.ajax( {
        url:"/api_get_hot_spare_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                var atable = $("#hot_table").find("tbody");
                atable.empty();
                for(x in data.data){
                    var atr = $("<tr></tr>");

                    var atd = $("<td></td>");
                    var aaa = $("<a></a>");
                    aaa.attr("href","/one_bbs?bid="+data.data[x].bid);
                    aaa.text(data.data[x].title);
                    atd.append(aaa)
                    atr.append(atd);

                    var atd = $("<td></td>");
                    atd.append(data.data[x].username);
                    atr.append(atd);

                    var atd = $("<td></td>");
                    atd.append(data.data[x].rnumber);
                    atr.append(atd);

                    atable.append(atr);
                }
            }else{
                alert(data.message);
            }
        },
        error: function(){
            window.location.href="/login";
        }
    });
}

function change_bbs(bid,info){
    var data = {"api":"del_bbs","bid":bid,"info":info};

    $.ajax( {
        url:"/api_change_bbs_info",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }else{
                alert(data.message);
            }
        },
        error: function(){
            alert("你没有权限提交！");
        }
    });
}

function get_great_spare_bbs(page){
    var spare = GetQueryString("spare");


    var data = {"api":"get_great_spare_bbs","spare":spare,"page":page};
    
    $.ajax( {
        url:"/api_get_great_spare_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                var atable = $("#great_table").find("tbody");
                atable.empty();
                for(x in data.data){
                    var atr = $("<tr></tr>");

                    var atd = $("<td></td>");
                    var aaa = $("<a></a>");
                    aaa.attr("href","/one_bbs?bid="+data.data[x].bid);
                    aaa.text(data.data[x].title);
                    atd.append(aaa)
                    atr.append(atd);

                    var atd = $("<td></td>");
                    atd.append(data.data[x].username);
                    atr.append(atd);

                    var atd = $("<td></td>");
                    atd.append(data.data[x].rnumber);
                    atr.append(atd);

                    atable.append(atr);
                }
            }else{
                alert(data.message);
            }
        },
        error: function(){
            window.location.href="/login";
        }
    });
}


function page_go_re(page){
    var spare = GetQueryString("spare");
    var data = {"api":"/api_get_great_spare_bbs_number","spare":spare}
    $.ajax( {
        url:"/api_get_great_spare_bbs_number",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify(data),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                get_great_spare_bbs(page);
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

function submit2(bid){
    var type = "file";          //后台接收时需要的参数名称，自定义即可           //即input的id，用来寻找值
    var formData = new FormData();
    formData.append(type, $("#bbsFile")[0].files[0]);    //生成一对表单属性
    if(formData.get(type)=="undefined"){
        window.location.reload();
        return;
    }
    formData.append("bid",bid);
    $.ajax({
        type: "POST",           //因为是传输文件，所以必须是post
        url: '/upload',         //对应的后台处理类的地址
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            window.location.reload();
        }
    });
}


get_where();
get_bbs();
get_hot_spare_bbs()
page_go_re(1);