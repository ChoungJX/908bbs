var E = window.wangEditor;
var editor = new E('#content');
editor.customConfig.uploadImgServer = '/upload_pic';
editor.customConfig.uploadFileName = 'files';
editor.create();

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
                $("#title").val(data.info.title);
                editor.txt.html(data.info.content);
            }
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}

function change_info(){
    var title = $("#title").val();
    var content = editor.txt.html();
    var data = {"api":"del_bbs","title":title,"content":content};

    $.ajax( {
        url:"/api_admin_change_info",
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
            alert("连接服务器失败！");
        }
    });
}

show_info();