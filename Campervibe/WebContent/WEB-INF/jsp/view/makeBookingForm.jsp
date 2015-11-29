<%@ page session="false" %>
<%@ page import="java.util.Map, java.util.UUID" %>
<%@ page import="uk.co.luciditysoftware.campervibe.domain.entities.Vehicle" %>

<%
    @SuppressWarnings("unchecked")
    Map<UUID, Vehicle> vehicleDatabase =
            (Map<UUID, Vehicle>)request.getAttribute("vehicleDatabase");
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h1>Booking Form</h1>
	
	<form method="POST" action="booking" role="form" class="form-horizontal" >
		<div class="form-group" >
			<label class="control-label col-sm-3" for="vehicleId">Vehicle</label>
	        <div class="col-sm-6">
	        	<select id="vehicleId">
	        	<%
	        		for(UUID id : vehicleDatabase.keySet())
                	{
	                    String idString = id.toString();
	                    Vehicle vehicle = vehicleDatabase.get(id);
	                    %><option value="<%= idString %>" ><%= vehicle.getName() %></option><%
                	}
	        	%>
	        	</select>
	        </div>
	    </div>
    </form>
</body>
</html>