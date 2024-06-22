<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="css/style.css">
</head>
<body>

    <header>
        <h1>SportFit</h1>
        <jsp:include page="nav.jsp">
            <jsp:param name="actual" value="error"/>
        </jsp:include>
    </header>

    <main>
      <h2>Oei</h2>
      <p>Er liep iets fout. Probeer nog een keertje... </p>
      <p>Info voor ontwikkelaars: Je vroeg een pagina die niet bestaat (404) </p>
    </main>

    <jsp:include page="footer.jsp"></jsp:include>

</body>
</html>
