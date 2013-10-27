<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String thisPageID = "RP_DATO";

//Servlet Info
  String servletName = "DailyTransactionOfficerReport" ;
  String servletLocation = servletName ;
  
  String officerList = GeneralDataInHtml.getPulldownUserList("officerId", "", "<OPTION VALUE=\"\">---- Please Select ----", "");    
  
  
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file = "inc_common_jsp.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dispatch(key) {
  document.form1.<%= IBaseConstants.kCommand %>.value=key ;
  document.form1.submit() ;
}
</script>
<script language="javascript" type="text/javascript" src="../js/datetimepicker.js">
</script>
<div id="container">
  <%@ include file = "shortcut.jsp" %>

  <div id="mainContent">
    <br/>
    <!-- =====> START OF PAGE CONTENT <====== !-->          
<form name="form1" method="post" action="<%= contextServletPath+servletLocation %>">

    <fieldset>
    <legend><u>Total Staff Transactions Report</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr">
		<td class="fieldname">Period Start (YYYYMMDDHHMMSS)</td>
		</td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <input type="text" name="periodStart" id="periodStart"/><a href="javascript:NewCal('periodStart','ddmmyyyy',true,24)"><img src="../img/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" >
		<td class="fieldname">Period End (YYYYMMDDHHMMSS)</td>
		</td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <input type="text" name="periodEnd" id="periodEnd"/><a href="javascript:NewCal('periodEnd','ddmmyyyy',false,24)"><img src="../img/cal.gif" width="16" height="16" border="0" alt="Pick a date"></a>
        </div></td>
      </tr>
      <tr>
      	<td align="left" class="oneColElsCtrHdr" >
		<td class="fieldname">Officer</td>
		</td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=officerList%>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="submit" name="submit" id="submit" value="Submit""/>
    </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
<!-- end #container --></div>
</body>
</html>
