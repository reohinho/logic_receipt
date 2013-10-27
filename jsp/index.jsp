<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file = "inc_common_jsp.jsp"%>

<div id="container">
<%@ include file = "shortcut.jsp" %>
  <div id="mainContent">
    <br/>
    <table width="89%" border="0" align="center" cellpadding="5">
      <tr>
        <td width="59%" align="center"><div align="center" class="style7">
          <div align="left"><u>Process</u></div>
        </div></td>
        <td width="41%" align="center"><div align="center" class="style7">
          <div align="left"><u>Maintenance</u></div>
        </div></td>
      </tr>
      <tr>
        <td align="center" class="oneColElsCtrHdr"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=TX">TX:Transaction</a></div></td>
        <td align="center" class="oneColElsCtrHdr"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=IM">IM:Item Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center" class="oneColElsCtrHdr"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=TE">TE:Enquiry</a></div></td>
        <td align="center" class="oneColElsCtrHdr"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=BM">BM:Bank Code Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center" class="style7">
        <div align="left"><u>Report</u></div></td>
        <td align="center" class="oneColElsCtrHdr"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=HM">HM:Holiday Code Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center">
        <div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=R1">R1:Total Transactions Report</a></div>
        </td>
        <td align="center"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=CM">CM:Countries Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center">
		<div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=R2">R2:Total Staff Transactions Report</a></div>
		</td>
        <td align="center"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=UM">UM:User Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center">
		<div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=R3">R3:Transaction Summary Report</a></div>
        </td>
        <td align="center"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=ME">ME:Message Maintenance</a></div></td>
      </tr>
      <tr>
        <td align="center">
		<div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=R4">R4:Visa Summary Report</a></div>
        </td>
        <td align="center"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=PE">PE:Change Password</a></div></td>
      </tr>
	  <tr>
        <td align="center">
		<div align="left"></div>
        </td>
        <td align="center"><div align="left"><a href="<%=contextServletPath%>shortcutServlet?shortcut=BK">BK:Backup Data</a></div></td>
      </tr>
    </table>
  <!-- end #mainContent --></div>
<!-- end #container --></div>
</body>
</html>
