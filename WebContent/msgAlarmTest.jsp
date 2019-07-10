<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebSocket client</title>
<script type="text/javascript" src="jquery-3.3.1.min.js"></script>  
<script type="text/javascript" language="javascript">

		
        var socket;
        if (typeof (WebSocket) == "undefined"){
            alert("This explorer don't support WebSocket")
        }

        function connect() {
            //Connect WebSocket server
            socket =new WebSocket("ws://192.168.3.193:8000/videostructure/alarmDataPush");
            //open
            socket.onopen = function () {
                //alert("WebSocket is open");
            }
            //Get message
            socket.onmessage = function (msg) {
				var dt = eval("("+msg.data+")");
				//alert(dt.mainTemplateUrl);
				$("#content").html(dt);
            }
            //close
            socket.onclose = function () {
                alert("WebSocket is closed");
            }
            //error
            socket.onerror = function (e) {
                alert("Error is " + e);
            }
        }

        function closeSocket() {
            socket.close();
        }

        function sendMsg() {
            socket.send("be948837aa9a11e8874c1866daf5acac");
        }
        
        function alertMsg() {
            alert("33333");
        }
        
        function guid() {
        	function S4() {
        		return (((1+Math.random())*0x10000)|0).toString(16).substring(1);
       		}
       		return (S4()+S4()+"-"+S4()+"-"+S4()+"-"+S4()+"-"+S4()+S4()+S4());
       	}
       	
    </script>
</head>
<body>
 	<button onclick="connect()">connect</button>
    <button onclick="closeSocket()">close</button>
    <button onclick="sendMsg()">sendMsg</button>
    <button onclick="alertMsg()">alert</button>
    <div id="content"></div>
</body>
</html>
