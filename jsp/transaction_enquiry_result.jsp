<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="transaction.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector"%>

<%

//Page Info
  String pgId = "TX_ENQU" ;
  int pgSeq = 1; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "TransactionEnquiryServlet" ;
  String servletLocation = servletName ;
  
//Get Search Result Object  
  TransactionSearchFormData formData = (TransactionSearchFormData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
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
  document.viewDetail.txNo.value = selectedId;
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
    <legend><u>Transaction Enquiry</u><u></u></legend>
    <br/>
<form name="form1" method="post" action="<%= servletLocation %>">
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="<%= ISearchWorkFlow.kSearchBack %>">
  <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= formData.getSystemObjectId() %>">
  <input type="submit" value="New Enquiry">
</form>
</td>
<td>&nbsp;</td>
</tr>
             <table width="100%" border="0" class="border">
                 <tr class="columnhead"> 
				   <td class="fieldname">Name</td>
				   <td class="fieldname">Item</td>
				   <td class="fieldname">Cash</td>
				   <td class="fieldname">Cheque</td>
   				   <td class="fieldname">Receipt No.</td>
   				   <td class="fieldname">Receipt Date</td>
   				   <td class="fieldname">Officer</td>
   				   <td class="fieldname">Status</td>
				   
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
    TransactionEnquiryData o = (TransactionEnquiryData) searchResult.elementAt(i) ;
%>
                <tr onmouseover="sbar(this)" onmouseout="cbar(this)"> 
                  <td><%= o.getDataForHtml(o.name)%></td>
                  <td><%= o.getDataForHtml(o.itemDescription)%></td>
                  <td><%= o.getDataForHtml(o.cashAmount)%></td>
                  <td><%= o.getDataForHtml(o.chequeAmount)%></td>
                  <td><a href="javascript:getDetail('<%=o.getDataForHtml(o.txNo) %>');"><%= o.getDataForHtml(o.txNo) %></a></td>
                  <td><%= o.getDataForHtml(o.txDate)%></td>
                  <td><%= o.getDataForHtml(o.officerId)%></td>
                  <td><%= o.getDataForHtml(o.status)%></td>             
                </tr>
<%
  }
}
%>
</table>
<form name="viewDetail" method="post" action="TransactionEditServlet"  >
  <input type="hidden" name="txNo" value="">
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="<%= IEditWorkFlow.kEdit %>">
</form>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
<!-- end #container --></div>
</body>
</html>
