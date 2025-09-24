<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.MailInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    List<MailInfoDTO> rList = (List<MailInfoDTO>) request.getAttribute("rList");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>메일 발송 이력</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
        }
        th, td {
            border: 1px solid #444;
            padding: 8px;
            text-align: center;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #f9f9f9;
        }
        a {
            margin-top: 10px;
            display: inline-block;
        }
    </style>
    <script>
        function doDetail(mailId) {
            location.href = "/mail/mailInfo?mailId=" + mailId;
        }
    </script>
</head>
<body>
<h2>메일 발송 이력</h2>
<hr/>

<table>
    <thead>
    <tr>
        <th>순번</th>
        <th>제목</th>
        <th>내용</th>
        <th>수신인</th>
        <th>발송시간</th>
    </tr>
    </thead>
    <tbody>
    <%
        if (rList != null) {
            for (MailInfoDTO dto : rList) {
    %>
    <tr>
        <td><%=dto.getMailId()%></td>
        <td onclick="doDetail('<%=dto.getMailId()%>')" style="cursor:pointer; color:blue; text-decoration:underline;">
            <%=CmmUtil.nvl(dto.getTitle())%>
        </td>
        <td><%=CmmUtil.nvl(dto.getContent())%></td>
        <td><%=CmmUtil.nvl(dto.getReceiver())%></td>
        <td><%=CmmUtil.nvl(dto.getSendTime())%></td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>

<a href="/mail/mailForm">메일 작성하기</a>

</body>
</html>
