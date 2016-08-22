<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import = "init.online.application.*,java.util.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>My Applications</title>
<link href="css/bootstrap.css" rel="stylesheet" />
</head>
<body>
<h1>Applications</h1>
<%
	ApplicationsDAO dao = new ApplicationsDAO();

	//controller {
	String id = request.getParameter("id");
	String name = request.getParameter("name");
	String price = request.getParameter("price");
	String action = request.getParameter("action");
	Application ap = new Application();
	
	if("create".equals(action)){
		double priceD = Double.parseDouble(price);
		ap = new Application(name,priceD);
		dao.create(ap);
	}else if("remove".equals(action)){
		int idInt = Integer.parseInt(id);
		dao.remove(idInt);
	}else if("select".equals(action)){
		int idInt = Integer.parseInt(id);
		ap = dao.selectOne(idInt);
	}
	else if("update".equals(action)){
		int idInt = Integer.parseInt(id);
		double priceD = Double.parseDouble(price);
		ap = new Application(name,priceD);
		dao.update(idInt,ap);
	}
	
	//}
	List<Application> applications = dao.selectAll();	
	
%>
<form action="application.jsp">
<table class="table">
	<tr>
			<th>Name</th>
			<th>Price</th>
	</tr>
	<tr>	
		<td><input name="name" class="form-control" value="<%= ap.getName() %>"/></td>
		<td><input name="price" class="form-control" value="<%= ap.getPrice() %>"/></td>
		<td>
			<button class="btn btn-success"  name="action" value="create">add</button>
			<button class="btn btn-primary"  name="action" value="update">update</button>
			<input  name="id" value="<%= ap.getId() %>" type="hidden"/>
		</td>
	</tr>
<%
	for(Application app:applications){
%>		<tr>
			<td><%=app.getName() %></td>
			<td><%=app.getPrice() %></td>
			<td>
				<a class="btn btn-danger" href="application.jsp?action=remove&id=<%=app.getId() %>"  >
				delete
				</a>
				<a class="btn btn-warning" href="application.jsp?action=select&id=<%=app.getId() %>"  >
				select
				</a>
			</td>
		</tr>
<%
	}
%>
</table>
</form>

</body>
</html>