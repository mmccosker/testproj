<!DOCTYPE html>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html lang="en">
<body>
	<div>
		<div>
			<h1>Weather Map</h1>
			
			<p> ${Home} </p>
			<table>
			<form action="/api/wmap" method="post" >
    		<tr>
			<td>City</td>
			<td>
				<input name="city" type=TEXT>
		 
			</td>
			</tr>
			<td>Zip Code</td>
			<td>
				<input name="zip" type=TEXT>
		 
			</td>
			</tr>
			<tr>
			<td>
			<input type="submit" id="wsearch" value="Submit" />
		 	</td>
			
			</tr>
			</form>
			
			
			<tr>
			<td>
			<p> ${Report} </p>
		 	</td>
			
			</tr>
			
			</table>			
			Click on this <strong><a href="next">link</a></strong> to Continue.
		</div>
	</div>
</body>
</html>
