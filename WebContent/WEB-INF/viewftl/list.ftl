<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>服务调用日志</title>
<script language="javascript" type="text/javascript" src="${base}/jquery-3.3.1.min.js"></script>
<script>
//从本地上传并导入
function exportExcel(){
 	document.forms[0].action = "export";
    document.forms[0].submit();
   
}
</script>
</head>

<body>
<form  method="post"  id="queryForm">

<input type="button" value="导出" onclick="exportExcel()">
</form>
</body>
</html>

