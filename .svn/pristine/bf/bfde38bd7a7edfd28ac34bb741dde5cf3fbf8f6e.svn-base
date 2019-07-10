<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="application/json; charset=UTF-8">
<title>session client</title>
<script type="text/javascript" src="jquery-3.3.1.min.js"></script>  
<script type="text/javascript" language="javascript">
	
	function testSession(){
		var urlStr = "<%=request.getContextPath()%>/test/printCache";
		var params = {};
		$.ajax({
			type: "post",
        	dataType: "json",
        	contentType : "application/json;charset=UTF-8",
        	url: urlStr,
        	data:  JSON.stringify(params),
        	success: function (data) {
            	if (data != "") {
            		alert(data.message);
	            }
    	    }
    	});
	}
	
	function login(){
		var urlStr = "<%=request.getContextPath()%>/sysUser/login";
		var params = {"userName":"admin","passWord":"111111"};
		$.ajax({
			type: "post",
        	dataType: "json",
        	url: urlStr,
        	data: JSON.stringify(params),
        	contentType : "application/json;charset=UTF-8",
        	success: function (data) {
            	if (data != "") {
            		alert(data.message);
	            }
    	    }
    	});
	}
	
	function setSession(){
		var urlStr = "<%=request.getContextPath()%>/session/test";
		var params = {"seconds":"10"};
		$.ajax({
			type: "post",
        	dataType: "json",
        	url: urlStr,
        	data: JSON.stringify(params),
        	contentType : "application/json;charset=UTF-8",
        	success: function (data) {
            	if (data != "") {
            		alert(data.message);
	            }
    	    }
    	});
	}
	
</script>
</head>
<body>
    <button onclick="login()">login</button>
    <button onclick="testSession()">testSession</button>
    <button onclick="setSession()">setSession</button>
    <div id="content"></div>
</body>
</html>
