<?xml version='1.0' encoding='utf-8'?>

<%@ page isErrorPage='true' import='java.io.*'%>

<!DOCTYPE html PUBLIC '-//W3C//DTD XHTML 1.0 Strict//EN'
 'http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd'>
<html xmlns='http://www.w3.org/1999/xhtml' xml:lang='en' lang='en'>

<head>
  <link rel='stylesheet' type='text/css' href='/widgets/common/exceptionHandler/style.css' />
  <title>ERROR Occurred!</title>
</head>

<body>
  <p class='error'><%=exception.getMessage()%></p>
</body>

</html>
