var E = window.wangEditor;
var editor = new E('#Weditor');
editor.customConfig.uploadImgShowBase64 = true;
editor.create();



function GetQueryString(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
    var r = decodeURI(window.location.search).substr(1).match(reg); //匹配目标参数
    if (r != null) return unescape(r[2]); return null; //返回参数值
    }

function get_bbs(){
    var spare = GetQueryString("word");
    var page = GetQueryString("page");
    var page = parseInt(page);

    if (!Boolean(page)){
        page = 1;
    }

    $.ajax( {
        url:"/api_get_bbs_search",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"get_bbs","spare":"%"+spare+"%","page":page}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            if (data.status == "1"){
                var oneBbs_model = $("#one_bbs").clone();
                $("#one_bbs").remove();

                for(x in data.data){
                    var oneBbs = oneBbs_model.clone();
                    oneBbs.find("#one_title").text(data.data[x].bbsTitle);
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
                    onepage.children().attr("href","/bbs_view_search?word="+spare+"&page="+pre_page);
                    pre_flag = pre_flag -1;
                    pre_page = pre_page -1;
                    $("#page_model").prepend(onepage);
                }
                if(page>1){
                    var onepage = onepage_model.clone();
                    onepage.children().text("上一页");
                    var a_page = parseInt(page) -1;
                    onepage.children().attr("href","/bbs_view_search?word="+spare+"&page="+a_page);
                    $("#page_model").prepend(onepage);
                }

                var nx_flag = 3;
                var nx_page = page + 1;
                while (nx_flag >0){
                    var onepage = onepage_model.clone();
                    onepage.children().text(nx_page)
                    onepage.children().attr("href","/bbs_view_search?word="+spare+"&page="+nx_page);
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
                    onepage.children().attr("href","/bbs_view_search?word="+spare+"&page="+b_page);
                    $("#page_model").append(onepage);
                }
                
            }
        },
        error: function(){
            alert("连接服务器失败！");
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
            alert("你没有权限提交！");
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
            alert("连接服务器失败！");
        }
    });
}



get_bbs();
