<%-- 
    Document   : index
    Created on : 19/01/2016, 13:12:33
    Author     : Gabriel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Deploy Git -> Tomcat - Token</title>
    </head>
    <body>
    <center>
        <form id="form" name="form" method="post" action="PuxarServlet">
            <table>
                <tr>
                    <td align="right">Endereço Git:</td>
                    <td align="left"><input type="text" name="git"></td>
                </tr>           
                <tr>
                    <td align="right">Token:</td>
                    <td align="left"><input type="password" name="token"></td>
                </tr>
                <tr>
                    <td align="right"></td>
                    <td align="right"><button type="submit">Aplicar</button> </td>
                </tr>
            </table>
        </form>
    </center>
</body>
</html>
