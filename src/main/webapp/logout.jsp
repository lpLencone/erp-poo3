<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
    // Invalida a sessão atual
    if (session != null) {
        session.invalidate();
    }

    // Redireciona para a página de login
    response.sendRedirect("login.jsp");
%>
