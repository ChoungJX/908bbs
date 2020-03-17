var head = '<div class="row">		<div class="col-md-12">			<nav class="navbar navbar-expand-lg navbar-light bg-light navbar-dark bg-dark fixed-top">				 				<button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">					<span class="navbar-toggler-icon"></span>				</button> <a class="navbar-brand" href="/">908bbs</a>				<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">					<ul class="navbar-nav">						<li class="nav-item active">							 <a class="nav-link" href="/">主页 <span class="sr-only">(current)</span></a>						</li>					</ul>					<ul class="navbar-nav ml-md-auto">						<form class="form-inline">							<input id="search_input" class="form-control mr-sm-2" type="text" /> 							<button onclick="search()" class="btn btn-primary my-2 my-sm-0" type="button">								搜索							</button>						</form>						<li class="nav-item active">							 <a class="nav-link" href="#" id="iflogin">获取信息中...<span class="sr-only">(current)</span></a>						</li>						<li class="nav-item dropdown">							 <a class="nav-link dropdown-toggle" href="http://example.com" id="navbarDropdownMenuLink" data-toggle="dropdown">功能栏</a>							<div class="dropdown-menu dropdown-menu-right" aria-labelledby="navbarDropdownMenuLink">								 <a class="dropdown-item" href="/message">查看消息</a> <a class="dropdown-item" href="/myPage">个人中心</a> <a class="dropdown-item" href="/mySetting">设置</a>								<div class="dropdown-divider">								</div> <a class="dropdown-item" href="/admin_index">管理员</a><a class="dropdown-item" href="/about">关于</a>  <a onclick="logout()" class="dropdown-item">登出</a>							</div>						</li>					</ul>				</div>			</nav>		</div>	</div>	<br><br><br><br>';
var aaa = $("#first_container");
aaa.prepend(head);

function search(){
    window.location.href = "/bbs_view_search?word="+$("#search_input").val();
}

$.ajax( {
    url:"/api_index_viewer",
    type: "POST",
    contentType: "application/json;charset=UTF-8;",
    data: JSON.stringify({"api":"index_viewer"}),
    dataType: "json",
    //processData:false,
    //contentType: false,

    success: function(data){
        aaa = document.getElementById("iflogin");
        if (data.status == "1"){
            aaa.innerHTML = data.username;
            aaa.href = data.url;
        }else{
            aaa.innerHTML = "登录/注册";
            aaa.href = "/login";
        }
    },
    error: function(){
        aaa = document.getElementById("iflogin");
        aaa.innerHTML = "登录/注册";
        aaa.href = "/login";
    }
});


function logout(){
    $.ajax( {
        url:"/api_logout",
        type: "POST",
        contentType: "application/json;charset=UTF-8;",
        data: JSON.stringify({"api":"logout"}),
        dataType: "json",
        //processData:false,
        //contentType: false,
    
        success: function(data){
            alert(data.message);
            window.location.reload();
        },
        error: function(){
            alert("连接服务器失败！");
        }
    });
}