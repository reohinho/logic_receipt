<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector"%>

<%

//Page Info
  String pgId = "MA_COUN" ;
  int pgSeq = 1; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "CountrySearchServlet" ;
  String servletLocation = servletName ;
  
//Get Search Result Object  
  CountrySearchFormData formData = (CountrySearchFormData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
  if (formData == null) 
  { // if no such object, redirect to servlet
    RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/" + servletName) ;
    dispatcher.forward(request,response);
  }
  Vector searchResult = formData.getResultVector() ;
 
  WebFormGenerator wfg = new WebFormGenerator(formData);
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file = "inc_common_jsp.jsp"%>
<script language="JavaScript" type="text/JavaScript">
function getDetail(selectedId) {
  document.viewDetail.countryCode.value = selectedId;
  document.viewDetail.submit();
}
function dispatch(key) {
  document.forms[1].<%= IBaseConstants.kCommand %>.value=key ;
  document.forms[1].submit() ;
}
</script>
<div id="container">
  <%@ include file = "shortcut.jsp" %>

  <div id="mainContent">
    <br/>
    <!-- =====> START OF PAGE CONTENT <====== !-->          
	<%= wfg.htmlErrorMsg() %>
    <fieldset>
    <legend><u>Country List</u><u></u></legend>
    <br/>
<form name="form1" method="post" action="<%= servletLocation %>">
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="<%= ISearchWorkFlow.kSearchBack %>">
  <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= formData.getSystemObjectId() %>">
  <input type="submit" value="New search">
</form>
</td>
<td>&nbsp;</td>
</tr>
             <table width="100%" border="0" class="border">
                 <tr class="columnhead"> 
				   <%=wfg.TDforLabel(formData.countryCode)%>
				   <%=wfg.TDforLabel(formData.countryDescription)%>
                 </tr>
<%
if (searchResult == null || searchResult.size() == 0)
{
%>
                <tr><td colspan=12>No record found.</td></tr>
<%
}
else
{
  for (int i=0 ; i < searchResult.size(); i++)
  {
    CountryData o = (CountryData) searchResult.elementAt(i) ;
%>
                <tr onmouseover="sbar(this)" onmouseout="cbar(this)"> 
                  <td><a href="javascript:getDetail('<%=o.getDataForHtml(o.countryCode) %>');"><%= o.getDataForHtml(o.countryCode) %></a></td>
                  <td><%= o.getDataForHtml(o.countryDescription)%></td>
                </tr>
<%
  }
}
%>
</table>
<form name="viewDetail" method="post" action="CountryEditServlet"  >
  <input type="hidden" name="countryCode" value="">
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="<%= IEditWorkFlow.kEdit %>">
</form>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
<!-- end #container --></div>
</body>
</html>
