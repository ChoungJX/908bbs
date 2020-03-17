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
            if (data.status == "1"){
                for(x in data.data){
                    var one = $("<tr></tr>");
                    one.attr("class","table-success");
                    one.append($("<td></td>").text(data.data[x].title));

                    one.append($("<td></td>").text(data.data[x].content));

                    var one_tr = $("<td></td>");
                    one_img = $("<img></img>");
                    one_img.attr("src",data.data[x].pic);
                    one_tr.append(one_img);
                    one.append(one_tr);


                    var one_tr = $("<td></td>");
                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-warning btn-sm");
                    one_button.attr("onclick","full_re_form(\""+data.data[x].title+"\",\""+data.data[x].content+"\",\""+data.data[x].rcid+"\",\""+data.data[x].pic+"\")");
                    one_button.text("修改")
                    one_tr.append(
                        one_button
                    );

                    var one_button = $("<button></button>");
                    one_button.attr("class","btn btn-danger btn-sm");
                    one_button.attr("onclick","delete_rc(\""+data.data[x].rcid+"\")")
                    one_button.text("删除")
                    one_tr.append(
                        one_button
                    );
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

function delete_rc(rcid){
    $.ajax( {
        url:"/api_admin_delete_recommon",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"rcid":rcid}),
        dataType: "json",
        //processData:false,
        //contentType: false,

        success: function(data){
            if (data.status == "1"){
                window.location.reload();
            }
        },
        error: function(){
        alert("服务器连接失败！");
        }
    });
}

function change_rc(){
    if($("#action").val() == "change"){
        var rcid = $("#rcid").val();
        var title = $("#rtitle").val();
        var content = $("#rcontent").val();
        var pic =  $("#rpic_bs64").val();

        var data = {"rcid":rcid,"pic":pic,"title":title,"content":content};
        $.ajax( {
            url:"/api_admin_change_recommon",
            type: "POST",
            contentType: "application/json;charset=UTF-8;",
            data: JSON.stringify(data),
            dataType: "json",
            //processData:false,
            //contentType: false,
    
            success: function(data){
                if (data.status == "1"){
                    window.location.reload();
                }
            },
            error: function(){
            alert("服务器连接失败！");
            }
        });
    }else{
        var title = $("#rtitle").val();
        var content = $("#rcontent").val();
        var pic =  $("#rpic_bs64").val();

        var data = {"pic":pic,"title":title,"content":content};
        $.ajax( {
            url:"/api_admin_create_recommon",
            type: "POST",
            contentType: "application/json;charset=UTF-8;",
            data: JSON.stringify(data),
            dataType: "json",
            //processData:false,
            //contentType: false,
    
            success: function(data){
                if (data.status == "1"){
                    window.location.reload();
                }
            },
            error: function(){
            alert("服务器连接失败！");
            }
        });
    }
}

function full_re_form(title,content,rcid,pic){
    $("#rtitle").val(title);
    $("#rcontent").val(content);
    $("#rcid").val(rcid);
    $("#rpic_bs64").val(pic)
    $("#action").val("change");
    $("#modal-851796").click();
}

function clean_re_form(){
    $("#rtitle").val("");
    $("#rcontent").val("");
    $("#rcid").val("");
    $("#action").val("");
    $("#rpic_bs64").val("");
    $("#modal-851796").click();
}


$("#rpic").change(function(){                                            //file点击事件
    var file = this.files[0];
//获取文件
    if (window.FileReader) {  
        $("#input_info").text("上传中...请稍后");                                                  //如果浏览器支持FileReader
        var reader = new FileReader();                                         //新建一个FileReader对象
        reader.readAsDataURL(file);                                      //读取文件url
        reader.onload=function(e){
            // console.log(e);                                                             //输出e,查看其参数
            // console.log(e.target.result);                                        //通过e,输出图片的base64码
            $("#rpic_bs64").val(e.target.result)
            // $("#output_img").attr("src",e.target.result);//将base64码填入src,用于预览
            $("#input_info").text("上传成功!");   
        };
    }
    file = null;
});

get_access();