<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>



<%
		ArrayList<String> messages = (ArrayList<String>) request.getAttribute("messages");
		if (messages != null) {
			for (String message : messages) {
%>	
				<h3> <%= message %> </h3>
<%
			}
		}%>