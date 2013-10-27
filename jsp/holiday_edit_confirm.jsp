<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_HOLI" ;
  int pgSeq = 5; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "HolidayEditServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  HolidayData vo = (HolidayData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
  if (vo == null) 
  { // if no such object, redirect to servlet
    RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/" + servletName) ;
    dispatcher.forward(request,response);
  }
  WebFormGenerator wfg = new WebFormGenerator(vo);

%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file = "inc_common_jsp.jsp"%>

<script language="JavaScript" type="text/JavaScript">
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
<form name="form1" method="post" action="<%= servletLocation %>">
Are you sure to save this record?         
<input type="button" name="Submit" value="Yes" onClick="set_processing_cmd(document.form1,this,'<%=IEditWorkFlow.kEditConfirm %>');">
<input type="button" name="Submit2" value="No" onClick="dispatch('<%=IEditWorkFlow.kEditBack %>');">
    <fieldset>
    <legend><u>Holiday Maintenance</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.holidayDate)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.holidayDate)%>
         <%=wfg.htmlFieldHidden(vo.holidayDate)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.holidayDescription)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.holidayDescription)%>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
    <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">      
    </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
<!-- end #container --></div>
</body>
</html>
