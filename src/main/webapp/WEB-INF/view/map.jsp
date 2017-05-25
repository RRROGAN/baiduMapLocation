<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%> 
<%@include file="common.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>不是首页么</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
    <link href="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/js/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="${ctx}/static/js/animate.css"  rel="stylesheet" type="text/css"/>
    <link href="${ctx}/static/js/bootstrap/css/bootstrapValidator.min.css" rel="stylesheet" type="text/css" />
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=pz2MsEP5ajlNGi0qfxdzZHzM447IBfkK
"></script>
    <script language="javascript" src="${ctx}/static/js/jquery-1.9.1.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/bootstrap-notify.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/bootstrap/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/bootstrap/js/bootstrapValidator.min.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/cookie_util.js"></script>
    <script type="text/javascript" src="${ctx}/static/js/stylelist.js"></script>
	<script type="text/javascript" src="http://api.map.baidu.com/library/TrafficControl/1.4/src/TrafficControl_min.js"></script>
<style type="text/css">
html {
    height: 100%
}

body {
    height: 100%;
    margin: 0px;
    padding: 0px;
}

#map {
    height: 100%;
}
ul{margin-left:2px;list-style-type:none}

img.x
{
position:absolute;
right:0px;
top:0px;
z-index:1;
 margin:20px 30px 30px 5px;
}
.detail_info-box{
display: none;
position:absolute;
background-color: #fff;
width: 200px;
right:0px;
top:0px;
z-index:1;
 margin:73px 30px 30px 0px;
 box-shadow: 1px 2px 1px rgba(0,0,0,.15);
}

.tab{
display: none;
position:absolute;
background-color: #fff;
width: 350px;
left:70px;
top:20px;
z-index:1;
 margin:30px 30px 30px 0px;
 box-shadow: 1px 2px 1px rgba(0,0,0,.15);
}

searchbox .x{
position:absolute;
left:0px;
top:0px;
z-index:1;
margin:0;
}
.fav-poi-item {
    padding: 10px;
    cursor: pointer;
    color: #999;
}
.fav-poi-item .fav-poi-icon {
    float: left;
    background: url(http://webmap2.map.bdstatic.com/wolfman/static/common/images/fav/fav_2aa39db.png) no-repeat 0 -60px;
    width: 15px;
    height: 23px;
    margin-right: 10px;
}
.fav-poi-item .fav-poi-actions {
    padding-left: 10px;
/*     display: none;
 */}
.fav-poi-item .fav-poi-actions a {
    color: #999;
    padding-left: 16px;
    margin-right: 10px;
    background-image: url(http://webmap2.map.bdstatic.com/wolfman/static/common/images/fav/fav_2aa39db.png);
    background-repeat: no-repeat;
    text-decoration: none;
}
.fav-poi-item .fav-poi-rename {
    background-position: 0 -20px;
}
.fav-poi-item .fav-poi-actions a.fav-poi-remove {
    margin-right: 0;
}
.fav-poi-item .fav-poi-remove {
    background-position: 0 -40px;
}
.col-center-block {  
    float: none;  
    display: block;  
    margin-left: auto;  
    margin-right: auto;  
} 

#searchbox {
    border-radius: 2px;
    width: 425px;
    position: relative;
    z-index: 5;
}
#searchbox #searchbox-container {
    position: relative;
    z-index: 2;
    pointer-events: auto;
    width: 368px;
    float: left;
    box-sizing: border-box;
    box-shadow: 1px 2px 1px rgba(0,0,0,.15);
}
#searchbox #search-button {
    pointer-events: auto;
    background: url(http://webmap0.map.bdstatic.com/wolfman/static/common/images/new/searchbox_f175577.png) no-repeat 0 -76px #3385ff;
    width: 57px;
    height: 38px;
    float: left;
    border: 0;
    padding: 0;
    cursor: pointer;
    border-radius: 0 2px 2px 0;
    box-shadow: 1px 2px 1px rgba(0,0,0,.15);
}

</style>
<script type="text/javascript">
    $(function(){
    	
    	 init();
    	//初始化模板选择的下拉框
    	var sel = document.getElementById('stylelist');
    	for(var key in mapstyles){
    		var style = mapstyles[key];
    		var item = new  Option(style.title,key);
    		sel.options.add(item);
    	}
       /*  var transit = new BMap.TransitRoute(map, {
    		renderOptions: {map: map, panel: "r-result"}
    	});
    	transit.search("王府井", "西单"); */
    });
    
    function changeMapStyle(style){
		map.setMapStyle({style:style});
	}
    
    //创建地图函数
    function createMap() {
    	var map = new BMap.Map("map");
        var point = new BMap.Point(112.24186581,30.33259052);
       map.centerAndZoom(point,12);
		window.map = map;  //将map变量存储为全局变量
    }
    //设置地图事件
    function setMapEvent() {
    	 map.enableDragging();   //允许拖拽
         map.enableDoubleClickZoom();//启用鼠标双击放大，默认启用(可不写)
         map.enableKeyboard();//启用键盘上下左右键移动地图
         map.enableScrollWheelZoom();   //启用滚轮放大缩小，默认禁用
         map.enableContinuousZoom();    //启用地图惯性拖拽，默认禁用
         map.enableScrollWheelZoom(true); 
         map.addEventListener("click", closePanel);
    }
    
    //地图控件添加函数
    function addMapControl() {
    	var off = new BMap.Size(10, 50);
    	map.addControl(new BMap.NavigationControl()); //添加默认缩放平移控件
        var bottom_left_control = new BMap.ScaleControl();// 右下角，添加比例尺
        map.addControl(bottom_left_control);   
        map.addControl(new BMap.OverviewMapControl()); //缩略图
        var overViewOpen = new BMap.OverviewMapControl({isOpen:true, anchor: BMAP_ANCHOR_BOTTOM_RIGHT});
        map.addControl(overViewOpen);
        map.addControl(new BMap.MapTypeControl({anchor:BMAP_ANCHOR_BOTTOM_RIGHT}));
        map.addControl(new BMap.CopyrightControl());
        // 初始化地图,设置城市和地图级别。
        var size = new BMap.Size(160, 8);
        var geoc = new BMap.Geocoder();  
        map.addControl(new BMap.CityListControl({
            anchor: BMAP_ANCHOR_TOP_RIGHT,
            offset: size,
            onChangeAfter:function(){
                point = map.getCenter();
                citylng = point.lng;
                citylat = point.lat;
                geoc.getLocation(point, function(rs){
                    var addComp = rs.addressComponents;
                }); 
            }
        }));
            var ctrl = new BMapLib.TrafficControl({
                showPanel: true //是否显示路况提示面板
            });      
            map.addControl(ctrl);
            ctrl.setOffset(new BMap.Size(85, 8));
            ctrl.setAnchor(BMAP_ANCHOR_TOP_RIGHT);
 
    }
    
    function init(){
    	createMap();
    	setMapEvent();
    	addMapControl();
    	autocomplete();
            var geolocation = new BMap.Geolocation();
             geolocation.getCurrentPosition(function(r){
                 if(this.getStatus() == BMAP_STATUS_SUCCESS){
                     var mk = new BMap.Marker(r.point);
                     var point = new BMap.Point(r.point.lng,r.point.lat);
                    map.centerAndZoom(point,12);
                     map.addOverlay(mk);
                     map.panTo(r.point);
                     var bs = map.getBounds();
                     var bssw = bs.getSouthWest();
                     var bsne = bs.getNorthEast();
                     console.info("当前地图可视化区域：" + bssw.lng + "," 
                    		 	+ bssw.lat + "到" + bsne.lng + "," + bsne.lat);
                 }
                 else {
                     alert('failed'+this.getStatus());
                 }        
             },{enableHighAccuracy: true})  
           // 添加定位控件
            var geolocationControl = new BMap.GeolocationControl(); //在左下角
           geolocationControl.addEventListener("locationSuccess", function(e){
             // 定位成功事件
             console.info(e);
             var address = '';
             address += e.addressComponent.province;
             address += e.addressComponent.city;
             address += e.addressComponent.district;
             address += e.addressComponent.street;
             address += e.addressComponent.streetNumber;
             //alert("当前定位地址为：" + address);   //无法定位到当前位置 将百度地图API中的示例原样复制 也获取不到准确地址 暂且不管
           });
           geolocationControl.addEventListener("locationError",function(e){
             // 定位失败事件
             alert(e.message);
           });
           map.addControl(geolocationControl); 
    		   
    }
    
    function addMarker(point, index){  // 创建图标对象   
        var myIcon = new BMap.Icon("${ctx}/static/img/loc_ok.png", new BMap.Size(23, 25), {    
        // 指定定位位置。   
        // 当标注显示在地图上时，其所指向的地理位置距离图标左上    
        // 角各偏移10像素和25像素。您可以看到在本例中该位置即是   
           // 图标中央下端的尖角位置。    
           offset: new BMap.Size(10, 25),    
           // 设置图片偏移。   
           // 当您需要从一幅较大的图片中截取某部分作为标注图标时，您   
           // 需要指定大图的偏移位置，此做法与css sprites技术类似。    
           imageOffset: new BMap.Size(0, 0 - index * 25)   // 设置图片偏移    
         });      
        // 创建标注对象并添加到地图   
        var marker = new BMap.Marker(point, {icon: myIcon});
            console.info(marker);
         map.addOverlay(marker);    
        }    
    
    var myValue;
    function autocomplete() {
    	var ac = new BMap.Autocomplete(    //建立一个自动完成的对象
    			{"input" : "suggestId"
    			,"location" : map
    		});
    	var ac2 = new BMap.Autocomplete(    //建立一个自动完成的对象
    			{"input" : "suggestId2"
    			,"location" : map
    		});
    	
    	var ac3 = new BMap.Autocomplete(    //建立一个自动完成的对象
        			{"input" : "suggestId3"
            			,"location" : map
            		});
    	
    		ac.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
    		var str = "";
    			var _value = e.fromitem.value;
    			var value = "";
    			if (e.fromitem.index > -1) {
    				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
    			}    
    			str = "FromItem<br />index = " + e.fromitem.index + "<br />value = " + value;
    			
    			value = "";
    			if (e.toitem.index > -1) {
    				_value = e.toitem.value;
    				value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
    			}    
    			str += "<br />ToItem<br />index = " + e.toitem.index + "<br />value = " + value;
    			$("#searchResultPanel").html(str);
    		});

    		
    		ac.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件
    		var _value = e.item.value;
    			myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
    			$("#searchResultPanel").html("onconfirm<br />index = " + e.item.index + "<br />myValue = " + myValue);
    			setPlace();
    		});	
    }
    	var addr = null;
		function setPlace(){
			map.clearOverlays();    //清除地图上所有覆盖物
			function myFun(){
				 addr = local.getResults().getPoi(0);
				console.info(addr);
				var pp = addr.point;    //获取第一个智能搜索的结果
				map.centerAndZoom(pp, 12);
				var bs = map.getBounds();
                var bssw = bs.getSouthWest();
                var bsne = bs.getNorthEast();
                console.info("当前地图可视化区域：" + bssw.lng + "," 
               		 	+ bssw.lat + "到" + bsne.lng + "," + bsne.lat);
                var bound = {"lng1":bssw.lng,"lng2":bsne.lng,
                				"lat1":bssw.lat,"lat2":bsne.lat};
                 setInterval(_randomLngLat(bound) ,5000);
				//创建右键菜单
				var markerMenu=new BMap.ContextMenu();
				markerMenu.addItem(new BMap.MenuItem('收藏',function(){
				var location_info = {start_posi : addr.title , start_detail_posi : addr.address,
						start_lat : pp.lat, start_lng : pp.lng};
				console.info(location_info);
				$.post("${ctx}/favorite/collectPoint", location_info,
						   function(data){
							if (data.code == '100200') {
								alert(data.msg);
							} else {
						     alert(data.msg);
							}
						   });
				}));
				/* markerMenu.addItem(new BMap.MenuItem('取消收藏', function() {
					
				})); */
				var marker = new BMap.Marker(pp);
				marker.addContextMenu(markerMenu);
				marker.flag = 1;
				map.addOverlay(marker);    //添加标注
			}
			var local = new BMap.LocalSearch(map, { //智能搜索
			  onSearchComplete: myFun
			});
			local.search(myValue);
		}
		
function _randomLngLat(bound){ 
		       return function(){ 
		    	   randomLngLat(bound); 
		       } 
		} 
function randomLngLat(bound) {
	$.post("${ctx}/favorite/locate",bound,
			function(r) {
			if (r.code == '100200') {
				console.info(addr.point);
				var overlays = map.getOverlays();
				console.info("length:" + overlays.length);
				for (var j = 0; j < overlays.length - 20; j++) {
					if (!addr.point.equals(overlays[j].point)) {
						map.removeOverlay(overlays[j]);
					}
				}
				  for (var i = 0; i < r.data.count; i++) {
					var point =  new BMap.Point(r.data.data[i][0],r.data.data[i][1]);
					var marker = new BMap.Marker(point);
					marker.flag = 1;
					map.addOverlay(marker); 
					marker.setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
				}  
				  
			}
	});
}
</script>
</head>
<body >

 <img id="loginIcon" src="${ctx}/static/img/login.png" class="x" style="width:50px;height:50px;display: inline;" />
<div id="detail_info" class="detail_info-box">
 <div style="background: #3385ff;position: relative;width: 100%;
    height: 30px;"> 
    <span style="width: 110px;font-size: 14px;color: #fff;margin-top:5px;margin-left:8px">
    	Hi,${sessionScope.user.login_name }</span>
  </div>  
    <div>
	<ul>
	<li><a id="favorite" href="javascript:void 0"><span>我的收藏</span></a></li>
	<li><a id="modifyPasswd"  href="javascript:void 0">修改密码</a></li>
	<li><a id="logout" href="javascript:void 0"><span>退出账号</span></a></li>
	<li>
	设置主题
	<select id="stylelist" onchange="changeMapStyle(this.value)"></select>
	 </li>
	</ul>    
	</div>
</div> 
<div id="favoritePanel" class="tab">
<ul id="myTab" class="nav nav-tabs" >
   <li class="active"><a href="#point" data-toggle="tab" >
     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地点&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
<!--    <li><a href="#route" data-toggle="tab">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;线路&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
 -->   <li><a href="javascript:void 0" id="close">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;✘&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</a></li>
   
</ul>
<div id="myTabContent" class="tab-content">
   <div class="tab-pane fade in active" id="point">
  <div   style=" overflow:scroll; height:400px">
  <ul>
	<c:forEach items="${sessionScope.favList}" var="item" >
	<li class="fav-poi-item">
	<div>
	<a href="javascript:void(0)" onclick='relocation(${item.start_lat}, ${item.start_lng});'>${item.start_posi}</a>
	</div>
	<div class="fav-poi-icon"></div>
	<div>
	${item.start_detail_posi}
	</div>
	<div>
	<fmt:formatDate value="${item.create_time }" pattern="yyyy-MM-dd HH:mm:ss"/>
	</div>
	<div class="fav-poi-actions fav-item-line" id="renameAndDel" style="font-size:12px">
	<a href="javascript:void(0)" class="fav-poi-rename" onclick="rename('${item.id}');">重命名</a>
	<a href="javascript:void(0)" class="fav-poi-remove" onclick="del('${item.id}');">删除</a> 
	</div>
	</li>
	</c:forEach>
	</ul>
	</div>		
   </div>
   <!-- <div class="tab-pane fade" id="route">
      <p></p>
   </div> -->
</div>
</div>
<div  style="z-index: 2;left:70px;top:20px;position: absolute;">
 <input type="text" id="suggestId" maxlength="256" placeholder="搜地点..." value="" style="width:250px;" />
 </div>
 <div id="routeDiv" style="z-index: 2;left:70px;top:20px;position: absolute;display:none">
 <input type="text" id="suggestId2" maxlength="256" placeholder="输入起点..."
 				 value="" style="width:250px;" />
 		
 	 	
 
 <br/> <input type="text" id="suggestId3" maxlength="256" placeholder="输入终点..."
 				 value="" style="width:250px;margin-top:10px;" />

 <img id="searchImg" src="${ctx}/static/img/search.png" style="position:absolute;z-index:1;
  				width:37px;height:26px;display: inline;top:36px;left:304px;"
 />	
 <select id="routeSel" style="height:26px;">
			<option value="0">公交</option>
			<option value="1">驾车</option>
			<option value="2">步行</option>
		</select>  
	  
	<!--  <select id="way" style="height:26px;">
			<option value="0">最少时间</option>
			<option value="1">最短距离</option>
			<option value="2">避开高速</option>
		</select> -->

 </div>
  
  <img id="toggerRouteDiv" src="${ctx}/static/img/route.png" style="position:absolute;z-index:1;
  				width:37px;height:26px;display: inline;left:360px;top:19px;"
 /> 
  
  <div id="r-result" style="z-index: 2;left:70px;top:85px;position: absolute;width:300px" ></div>
 <div id="map">
 </div> 
 <div class="modal fade" id="loginModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="loginModalLabel">
                  用户登录
                </h4>
            </div>
            <div class="modal-body">
                <form class="bs-example bs-example-form" id="loginForm" role="form">
                    <div class="input-group">
                        <span class="input-group-addon">用户名</span>
                        <input  type="text" class="form-control" id="username" name="username"
                         placeholder="请输入手机号/邮箱" />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">密　码</span>
                        <input type="password" class="form-control" id="passwd" name="passwd" 
                        placeholder="请输入密码" />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">验证码</span>
                        <input type="text" class="form-control" id="verifyCode" name="verifyCode" 
                        placeholder="请输入验证码" />
                    </div>
                    <span align="right"><img id="codeImg" alt="验证码" src="${ctx }/user/code.do"/></span>
                    <br>
                    <div class="checkbox">  
                       <label>  
                      <input type="checkbox" id="rememberMe"  name="rememberMe" value="remember-me">  
                                                   下次自动登录 
                      </label>  
                      <span style="color:red" id="tipMsg"></span>
                     </div> 
                     <!-- <span>其他账号登录<br/> 
                     <a target="_blank" href="https://graph.qq.com/oauth2.0/authorize?response_type=code&amp;client_id=100571486&amp;redirect_uri=https%3A%2F%2Fid.amap.com%2Findex%2Fqq%3Fpassport%3D1" hover="qq" actions="click:loginByQQ">
                    QQ登录</a>
                    <a href="https://api.weibo.com/oauth2/authorize?client_id=884965267&amp;redirect_uri=https%3A%2F%2Fid.amap.com%2Findex%2Fweibo%3Fpassport%3D1" hover="sina" actions="click:loginBySina">
                                新浪微博登录</a>
                   </span> -->
                   </form>
            </div>
            <div class="modal-footer" >
            <button class="btn btn-lg btn-primary btn-block" id="login" type="button">登录</button>  
                <br/>
                 <a id="forgetPasswd"  href="javascript:void 0" class="btn btn-link">忘记密码?</a>
               <a id="forwordReg" target="_blank" href="javascript:void 0" class="btn btn-link">免费注册</a>
            </div>
            
        </div>
    </div>
</div>

 <div class="modal fade" id="registerModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="registerModalLabel">
                    免费注册
                </h4>
            </div>
            <div class="modal-body">
                <form class="bs-example bs-example-form" id="registerForm" role="form" >
                 <div class="input-group">
                        <span class="input-group-addon">邮　箱　</span>
                        <input type="text" id="email" class="form-control" name="email"
                         />
                    </div>
                    <br>
                     <div class="input-group">
                        <span class="input-group-addon">手机号　</span>
                        <input type="text" id="phone" class="form-control" name="phone"
                          />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">昵称　</span>
                        <input type="text" id="nickname" class="form-control" name="nickname"
                        />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">密　码　</span>
                        <input type="password"  class="form-control" id="passwd1" name="passwd1" 
                        placeholder="请输入密码（6-16位任意字符，区分大小写）" />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">重复密码</span>
                        <input type="password"  class="form-control" id="passwd2" name="passwd2" 
                        placeholder="请再次输入密码" />
                    </div>
                    <br/>
                    <span style="color:red" id="tipMsg2"></span>
                </form>
            </div>
            <div class="modal-footer" >
            <button class="btn btn-lg btn-primary btn-block" id="register" type="button">完成注册</button>  
                <br/>
            </div>
        </div>
    </div>
</div>


<div class="modal fade" id="modifyModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="modifyModalLabel">
                    修改密码
                </h4>
            </div>
            <div class="modal-body">
                <form class="bs-example bs-example-form" id="modifyForm" role="form" >
                <div class="input-group">
                        <span class="input-group-addon">用&nbsp;户&nbsp;名　</span>
                        <input type="text" id="username2" class="form-control" name="username" 
                        placeholder="请输入邮箱/手机号" />
                    </div>
                    <br/>
                    <div class="input-group">
                        <span class="input-group-addon">原&nbsp;密&nbsp;码　</span>
                        <input type="password" id="old_passwd" class="form-control" name="old_passwd" 
                        placeholder="请输入密码" />
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group-addon">新&nbsp;密&nbsp;码　</span>
                        <input type="password" id="new_passwd" class="form-control" name="new_passwd" 
                        placeholder="请输入新密码（6-16位任意字符，区分大小写）"/>
                    </div>
                    <!-- <br/>
                    <div class="input-group">
                        <span class="input-group-addon">确认新密码</span>
                        <input type="password" id="passwd2" class="form-control" name="confirm_new_passwd" 
                        placeholder="请再次输入新密码" />
                    </div> -->
                    <span style="color:red" id="tipMsg4"></span>
                </form>
            </div>
            <div class="modal-footer" >
            <button class="btn btn-lg btn-primary btn-block" id="modify" type="button">确定</button>  
                <br/>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="forgetModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="forgetModalLabel">
                    找回密码
                </h4>
            </div>
            <div class="modal-body">
                <form class="bs-example bs-example-form" id="forgetForm" role="form" >
                <div class="input-group">
                        <span class="input-group-addon">用&nbsp;户&nbsp;名　</span>
                        <input type="text" id="username" class="form-control" name="username" 
                        placeholder="请输入您要找回密码的邮箱" />
                    </div>
                    <span style="color:red" id="tipMsg5"></span>
                </form>
            </div>
            <div class="modal-footer" >
            <button class="btn btn-lg btn-primary btn-block" id="forget" type="button">下一步</button>  
                <br/>
            </div>
        </div>
    </div>
</div>
<div class="modal fade" id="renameModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="renameModalLabel">
                    重命名
                </h4>
            </div>
            <div class="modal-body">
                <form class="bs-example bs-example-form" id="renameForm" role="form" >
                    <div class="input-group">
                        <span class="input-group-addon">名&nbsp;称&nbsp;　</span>
                        <input type="text" id="newname" class="form-control" name="newname" 
                        placeholder="请输入新名称"/>
                    </div>
                </form>
            </div>
            <div class="modal-footer" >
             <button class="btn  btn-default " id="cancel" type="button" data-dismiss="modal">取消</button>  
            <button class="btn  btn-primary " id="rename" type="button">保存</button>
            </div>
        </div>
    </div>
</div>

<div class="modal fade" id="delModal" tabindex="-1" role="dialog"  aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="delModalLabel">
                   删除
                </h4>
            </div>
            <div class="modal-body">
               	确定删除该收藏点？
            </div>
            <div class="modal-footer" >
            <button class="btn  btn-primary " id="del" type="button">确定</button>
            <button class="btn  btn-default " type="button" data-dismiss="modal">取消</button> 
            </div>
        </div>
    </div>
</div>


</body>
<script type="text/javascript">
$(document).ready(function() {
	
	 $.post("${ctx}/user/login"
	);
	 
	 $('.fav-poi-item').mouseover(function(){
		 var index = $('li .fav-poi-item').index();
		 console.info(index);
         $('#renameAndDel').css('display','block');
        }).mouseout(function(){
         $('#renameAndDel').css('display','none'); 
        });
	
   $("#close").click(function() {
	   $("#favoritePanel").animate({
		   height: 'toggle', opacity: 'toggle'
	   }, "slow");
	   
   });
	 
   $("#loginIcon").click(function(){
	   var nickname =  "${sessionScope.user.login_name }";
	   if ( nickname != '' ) {
		   $("#detail_info").toggle("slow");
	   } else {
		   emptyForm();
		   $('#loginModal').modal('show');
	       $("#registerModal").modal('hide');
	       $('#modifyModal').modal('hide');
	   }
   });
   
   $("#forwordReg").click(function(){
       $('#loginModal').modal('hide');
       $("#registerModal").modal('show');
       emptyForm();
       $('#modifyModal').modal('hide');

   });
   
   $("#modifyPasswd").click(function() {
	   $('#loginModal').modal('hide');
       $("#registerModal").modal('hide');
       emptyForm();
       $('#modifyModal').modal('show');
       
   });
   
   $("#favorite").click(function() {
	   //加载收藏数据
	   $.post("${ctx}/favorite/listFavorite",{uid:"${sessionScope.user.uid}"},function(data){
		   if (data.code == '100200') {
			   data = data.data;
			   console.info(data);
			   console.info("${sessionScope.favList[0].uid}");
			  /*  $("#point").html(
				'<c:forEach items="${favList}" var="item" ><p>item.start_posi<br/>item.create_time</p></c:forEach>'
			   ); */
		   } else {
			   alert("网络超时");
		   }
	   });
	   $("#detail_info").hide("slow");
	   $("#routeDiv").hide();
	   $("#suggestId").show();
	   $("#favoritePanel").animate({
		   height: 'toggle', opacity: 'toggle'
	   }, "slow");
   });
   
   $("#forgetPasswd").click(function() {
	   emptyForm();
	   $('#loginModal').modal('hide');
       $("#registerModal").modal('hide');
       $('#modifyModal').modal('hide');
       $('#forgetModal').modal('show');
   });
   
   $("#logout").click(function() {
	   $.get("${ctx}/user/logout", function(data){
	  		window.location.href=window.location.href;
		 });
   });
   
   $("#toggerRouteDiv").click(function() {
	   console.info("xxx");
	   $("#favoritePanel").hide();
	   $("#suggestId").toggle();
	   $("#routeDiv").toggle();
	   $("#suggestId2").val("");
	   $("#suggestId3").val("");
	   
   });
   
   $("#searchImg").click(function() {
	  // if ()
	 //  setPlace();
	  var overlays = map.getOverlays();
	   for(var i = 0; i < overlays.length; i++) {
		 console.info(overlays[i].flag);
		 if ( overlays[i].flag == undefined) {
			 map.removeOverlay(overlays[i]);
		 }
	 }
	   var sId2 = $("#suggestId2").val();
	   var sId3 = $("#suggestId3").val();
	   var routeSelValue = $("#routeSel").val();
	   if (sId2 == "" || sId2 == null) {
		   $("#suggestId2").focus();
		   return;
	   }
	   if (sId3 == "" || sId3 == null) {
		   $("#suggestId3").focus();
		   return;
	   }
	   getRouteBySel(sId2,sId3,routeSelValue);
	   $("#r-result").show();
	   
   });
  
   function getRouteBySel(start_posi, end_posi, routeSelValue) {
	   if (routeSelValue == 0 || routeSelValue == '0') {
		   transitRoute(start_posi, end_posi);
	   }
		if (routeSelValue == 1 || routeSelValue == '1') {
			driverRoute(start_posi, end_posi);
			   }
		if (routeSelValue == 2 || routeSelValue == '2') {
			walkingRoute(start_posi, end_posi);
		}
   }
   //驾车路线
   function driverRoute(start_posi, end_posi) {
	   
	   var routePolicy = [BMAP_DRIVING_POLICY_LEAST_TIME,
	                      BMAP_DRIVING_POLICY_LEAST_DISTANCE,
	                      BMAP_DRIVING_POLICY_AVOID_HIGHWAYS];
	   
	   
	  	var output = "从" + start_posi + "到" + end_posi + "驾车需要";
		var searchComplete = function (results){
			if (transit.getStatus() != BMAP_STATUS_SUCCESS){
				return ;
			}
			var plan = results.getPlan(0);
			output += plan.getDuration(true) + "\n";                //获取时间
			output += "总路程为：" ;
			output += plan.getDistance(true) + "\n";             //获取距离
		}
		 var transit = new BMap.DrivingRoute(map, {renderOptions: {map: map,panel: "r-result",enableDragging : true },
			 policy: routePolicy[2],
			onSearchComplete: searchComplete
		});
		transit.search(start_posi,end_posi);
	  }
   //公交路线
   function transitRoute(start_posi, end_posi) {
	   var routePolicy = [BMAP_TRANSIT_POLICY_LEAST_TIME,
	                      BMAP_TRANSIT_POLICY_LEAST_TRANSFER,
	                      BMAP_TRANSIT_POLICY_LEAST_WALKING,
	                      BMAP_TRANSIT_POLICY_AVOID_SUBWAYS];
	   var transit = new BMap.TransitRoute(map, {
			renderOptions: {map: map, panel: "r-result",enableDragging : true}
		});
		transit.search(start_posi, end_posi);
   }
   //步行路线
   function walkingRoute(start_posi, end_posi) {
		var walking = new BMap.WalkingRoute(map, {renderOptions: {map: map, panel: "r-result", autoViewport: true,enableDragging : true}});
		walking.search(start_posi, end_posi);
   }
   //登录
   $("#login").click(function(){
	   var obj = $("#loginForm").serialize();
	   console.info(obj);
	   $.ajax({
		   url : "${ctx}/user/login",
		   data : obj,
		   type : "post",
		   dateType : "text",
		   success : function(data){
			  	if (data.code == '100200') {
			  		alert("登录成功");
			  		data = data.data;
			  		console.info(data.login_name);
			  		window.location.href=window.location.href;
			  	} else {
			  		$("#tipMsg").html(data.msg);
			  	}
		   }
	   });
   });
   //注册
   $("#register").click(function(){
	   var obj = $("#registerForm").serialize();
	   console.info(obj);
	   var result = check();
	   console.info(result);
	   if (result.isPass == false) {
		   $("#tipMsg2").html(result.errMsg);
		   return;
	   }
	   $.ajax({
		   url : "${ctx}/user/register",
		   data : obj,
		   type : "post",
		   dateType : "text",
		   success : function(data){
			   console.info(data);
			   if (data.code == '100200') {
				 //$('#successTooltip').modal("show");
				 alert("注册成功！");
				 window.location.href=window.location.href;
			   } else {
			   $("#tipMsg2").html(data.msg);
			   }
		   }
	   });
   });
   
   //修改密码
   $("#modify").click(function(){
	   var obj = $("#modifyForm").serialize();
	   var result = modifyChk();
	   if (result.isPass == false) {
		   $("#tipMsg4").html(result.errMsg);
		   return;
	   }
	   $.ajax({
		   url : "${ctx}/user/modify",
		   data : obj,
		   type : "post",
		   dateType : "text",
		   success : function(data){
			   console.info(data);
			   if (data.code == '100200') {
				 //$('#successTooltip').modal("show");
				 alert("修改密码成功！");
				 window.location.href=window.location.href;
			   } else {
			   $("#tipMsg4").html(data.msg);
			   }
		   }
	   });
 });
   
 //忘记密码
   $("#forget").click(function(){
	   var obj = $("#forgetForm").serialize();
	   $.ajax({
		   url : "${ctx}/user/forget",
		   data : obj,
		   type : "post",
		   dateType : "text",
		   success : function(data){
			   if (data.code == '100200') {
				   alert(data.msg);
				 window.location.href=window.location.href;
			   } else {
			   $("#tipMsg5").html(data.msg);
			   }
		   }
	   });
 });
   
 /*   $('#verifyCode').blur(function(){
		var code = $(this).val();
		console.log('code:'+code);
		var url = "${ctx}/user/checkCode.do?code="+code;

		$.getJSON(url,function(result){
			if(result.code == "100200"){
				$("#tipMsg").html(result.msg);
			}
			else{
				$("#tipMsg").html(result.msg);
			}				
		});
}); */
   $('#codeImg').click(function(){
		var url = "${ctx}/user/code.do?id="+Math.random();
		$(this).attr('src',url);
	});
});

//从收藏处重定位
function relocation(lat, lng) {
	map.clearOverlays(); 
	var new_point = new BMap.Point(lng,lat);
	var marker = new BMap.Marker(new_point);  // 创建标注
	map.addOverlay(marker);              // 将标注添加到地图中
	map.panTo(new_point);
}
//重命名
function rename(id) {
	$("#renameModal").modal("show");
	$("#rename").click(function() {
	   var obj = $("#renameForm").serialize();
	   $.get("${ctx}/favorite/rename?id=" + id,obj, function(data) {
			if (data.code == '100200') {
				alert(data.msg);
				$("#renameModal").modal("hide");
			}
		}); 
	});
	/* $("#cancel").click(function() {
		$("#renameModal").modal("hide");
	}); */
}
//取消收藏
function del(id) {
	$("#delModal").modal("show");
	$("#del").click(function() {
		$.get("${ctx}/favorite/del?id=" + id, function(data) {
			if (data.code == '100200') {
				alert(data.msg);
				$("#delModal").modal("hide");
			}
		});
	});
	
}
function closePanel() {
	 $("#favoritePanel").hide("slow");
	 $("#detail_info").hide("slow");
	 $("#r-result").hide("slow");
	 var overlays = map.getOverlays();
	 for(var i = 0; i < overlays.length; i++) {
		 console.info(overlays[i].flag);
		 if ( overlays[i].flag == undefined) {
			 map.removeOverlay(overlays[i]);
		 }
	 }
}

//清空表单
function emptyForm() {
	$("#email").val("");
	$("#phone").val("");
	$("input[id^=username]").val("");
    $("#nickname").val("");
	$("input[type=password]").val("");
	$("span[id^=tipMsg]").html("");
}

 function check() {
	 var isPass = true;
	 var errMsg = "";
	 var email = $("#email").val();
	 var phone = $("#phone").val();
	 var nickname = $("#nickname").val();
	 var passwd1 = $("#passwd1").val();
	 var passwd2 = $("#passwd2").val();
	 console.info(email);
	//正则验证邮箱格式
	 var emailReg=/^[_a-z 0-9]+@([_a-z 0-9]+\.)+[a-z 0-9]{2,3}$/;   
	 //匹配以字母开头，长度在6~18之间，只能包含字符、数字和下划线
	 var passwdReg = /^[a-zA-Z]\w{5,17}$/; 
	 var f = false;
	 if (email == "" || email == null || (f = !emailReg.test(email))) {
		 isPass = false;
		 errMsg = f ? "邮箱格式不正确" : "邮箱不能为空";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 
	 if (phone == "" || phone == null || (f = phone.length != 11)) {
		 isPass = false;
		 errMsg = f ? "手机号码格式不正确" : "手机号不能为空";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 
	 if (nickname == "" || nickname == null) {
		 isPass = false;
		 errMsg = "用户名不能为空";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 if (passwd1 == "" || passwd1 == null || !passwdReg.test(passwd1)) {
		 isPass = false;
		 errMsg = "密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 if (passwd1 !== passwd2) {
		 isPass = false;
		 errMsg = "两次密码输入不一致";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 return {"isPass":isPass, "errMsg":errMsg};
 }
 
 function modifyChk() {
	 var isPass = true;
	 var errMsg = "";
	 var username = $("#username2").val();
	 var passwd = $("#new_passwd").val();
	 //匹配以字母开头，长度在6~18之间，只能包含字符、数字和下划线
	 var passwdReg = /^[a-zA-Z]\w{5,17}$/; 
	 var f = false;
	 if (username == "" || username == null) {
		 isPass = false;
		 errMsg = "昵称不能为空";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 if (passwd == "" || passwd == null || !passwdReg.test(passwd)) {
		 isPass = false;
		 errMsg = "新密码以字母开头，长度在6~18之间，只能包含字符、数字和下划线";
		 return {"isPass":isPass, "errMsg":errMsg};
	 }
	 return {"isPass":isPass, "errMsg":errMsg};
	 
 }
</script>

</html>