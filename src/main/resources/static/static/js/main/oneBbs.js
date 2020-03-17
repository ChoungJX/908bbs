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


function get_one_where(){
    var spare = GetQueryString("bid");
    
    $.ajax( {
        url:"/api_get_one_where",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"get_where","bid":spare}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                $("#area_name").text(data.area_name);
                $("#spare_name").text(data.spare_name);
                $("#spare_name").attr("href","/bbs_view?spare="+data.spare_href)
                $("#bbs_name").text(data.bbs_name);
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}


function create_receive(){
    var bid = GetQueryString("bid");
    var content = editor.txt.html();


    var data = {"api":"create_receive","bbsId":bid,"receiveContent":content};
    
    $.ajax( {
        url:"/api_create_receive",
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
            window.location.href="/login";
        }
    });
}


function get_bbs(){
    var bid = GetQueryString("bid");
    var page = GetQueryString("page");
    var page = parseInt(page);

    if (!Boolean(page)){
        page = 1;
    }

    $.ajax( {
        url:"/api_one_bbs",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"one_bbs","bbsId":bid,"page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                var oneBbs_model = $("#aCommon").clone();
                $("#aCommon").remove();

                var aBbs = oneBbs_model.clone();
                aBbs.find("#bTitle").text("标题:"+data.bbs.bbsTitle);
                aBbs.find("#bUser").text("发布者:"+data.bbs.username+"#楼主");
                aBbs.find("#bContent").html(data.bbs.bbsContent);
                aBbs.find("#bTime").text("编辑时间:"+data.bbs.lastTime);
                aBbs.find("#delete_button").attr("onclick","del_bbs(\""+data.bbs.bbsId+"\",\""+data.bbs.spId+"\")");
                if(data.bbs.purl == "000"){

                }else{
                    var aaa=$("<a></a>");
                    aaa.attr("href",data.bbs.purl);
                    aaa.text("附件:"+data.bbs.pname);
                    aBbs.find("#paperclip").append(aaa);
                }
                $("#bbs_start").append(aBbs);

                for(x in data.receive){
                    var onepage = parseInt(data.onepage);
                    var buildflag = parseInt(page-1);
                    buildflag = parseInt(buildflag*onepage);
                    buildflag = buildflag+parseInt(x)+1;
                    var oneBbs = oneBbs_model.clone();
                    oneBbs.find("#bTitle").text("回复:");
                    oneBbs.find("#bUser").text("发布者:"+data.receive[x].username+"#"+buildflag+"楼");
                    oneBbs.find("#bContent").html(data.receive[x].receiveContent);
                    oneBbs.find("#bTime").text("回复时间:"+data.receive[x].receiveTime);
                    oneBbs.find("#delete_button").attr("onclick","del_receive(\""+data.receive[x].reId+"\")");
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
                    onepage.children().attr("href","/one_bbs?bid="+bid+"&page="+pre_page);
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("href","/one_bbs?bid="+bid+"&page="+a_page);
                    $("#page_model").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("href","/one_bbs?bid="+bid+"&page="+nx_page);
                    nx_flag = nx_flag-1;
                    if((nx_page-1)*data.onepage >= data.number){
                        break;
                    }
                    nx_page = nx_page +1;
                    $("#page_model").append(onepage);
                }

                if(page*data.onepage <= data.number){
                    var onepage = onepage_model.clone();
                    onepage.children().text("下一页")
                    var b_page = parseInt(page) + 1;
                    onepage.children().attr("href","/one_bbs?bid="+bid+"&page="+b_page);
                    $("#page_model").append(onepage);
                }
                
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}


function del_bbs(bid,spId){
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
                window.location.href="/bbs_view?spare="+spId;
            }else if(data.status == "0"){
                alert("不是您的帖子，删除失败！");
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

function del_receive(reid){
    var data = {"api":"del_receive","delData":[reid]};

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

get_one_where();
get_bbs();