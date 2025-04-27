<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="erp.model.ActivityLog, java.util.List" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Lista de Logs</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
        }
        th {
            background-color: #f2f2f2;
            text-align: left;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <h1>Lista de Logs</h1>

    <!-- Exibe mensagem de erro se existir -->
    <%
        String error = (String) request.getAttribute("error");
        if (error != null) {
    %>
        <p style="color: red;"><%= error %></p>
    <%
        }
    %>

    <!-- Tabela com os logs -->
    <table>
        <thead>
            <tr>
                <th>Ação</th>
                <th>Data e Hora</th>
                <th>Endereço IP</th>
                <th>Agente do Usuário</th>
            </tr>
        </thead>
        <tbody>
            <%
                List<ActivityLog> logs = (List<ActivityLog>) request.getAttribute("logs");
                if (logs != null) {
                    for (ActivityLog log : logs) {
            %>
                <tr>
                    <td><%= log.action %></td>
                    <td><%= log.timestamp %></td>
                    <td><%= log.ipAddress %></td>
                    <td><%= log.userAgent %></td>
                </tr>
            <%
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
