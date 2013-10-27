<div id="header">
  <img src="http://192.168.0.1:8080/logic_receipt/img/Consulate-General.gif" width="304" height="63" />  </div>
  <div id ="toolbar">
  
<% 
		HttpSession hs = request.getSession();
		String userId2 = (String)hs.getAttribute("userId") ; 
		String uid = (String)hs.getAttribute("userName") ; 
		if(uid==null)
		{
			response.sendRedirect ("login.jsp");	
		}
%>
  <form name="shortcut" method="post" action="http://192.168.0.1:8080/logic_receipt/servlet/shortcutServlet">
    <table width="100%" border="0">
      <tr>
        <td width="44%" align="left" valign="bottom" nowrap="nowrap"><span class="style12"> Main Menu (MM)</span></td>
        <td width="33%"></td>
        <td width="23%" valign="bottom" nowrap="nowrap"><span class="style10">
          <%=uid%>
          <input name="shortcut" type="text" id="shortcut" size="10" />
          <input type="hidden" name="contextPath" id="contextPath" value="http://192.168.0.1:8080/logic_receipt">
          <input type="button" name="logout" id="logout" value="Logout" onclick="window.location.href('http://192.168.0.1:8080/logic_receipt');"
		</span></td>
      </tr>
    </table>
    </form>
 <!-- end #header --></div>