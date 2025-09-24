<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="kopo.poly.dto.MailInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%
    MailInfoDTO rDTO = (MailInfoDTO) request.getAttribute("rDTO");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>메일 이력 상세보기</title>
    <style>
        table {
            border-collapse: collapse;
            width: 80%;
            margin: 20px 0;
        }
        th, td {
            border: 1px solid #444;
            padding: 10px;
            text-align: left;
        }
        th {
            width: 20%;
            background-color: #f2f2f2;
        }
    </style>
</head>
<body>
<h2>메일 이력 상세보기</h2>
<hr/>

<table>
    <tr>
        <th>제목</th>
        <td><%=CmmUtil.nvl(rDTO.getTitle())%></td>
    </tr>
    <tr>
        <th>수신인</th>
        <td><%=CmmUtil.nvl(rDTO.getReceiver())%></td>
    </tr>
    <tr>
        <th>발송일</th>
        <td><%=CmmUtil.nvl(rDTO.getSendTime())%></td>
    </tr>
    <tr>
        <th>내용</th>
        <td><%=CmmUtil.nvl(rDTO.getContent())%></td>
    </tr>
</table>

<button type="button" onclick="location.href='/mail/mailList'">목록</button>

</body>
</html>
