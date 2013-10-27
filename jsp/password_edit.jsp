<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_PASS" ;
  int pgSeq = 4; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "PasswordEditServlet" ;
  String servletLocation = servletName ;
  String userId = (String)request.getSession().getAttribute("userId");
  

// get view object from caller  
  UserData vo = (UserData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
  if (vo == null) 
  { 
  	vo = new UserData();
  	vo.setData(UserData.userId, userId);
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
<form name="form1" method="post" action="<%= contextServletPath+servletLocation %>">

    <fieldset>
    <legend><u>Password Change</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.password)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.password)%>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="save" id="save" value="Update" onClick="dispatch('<%=IEditWorkFlow.kEditSave %>')"/>
    <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    <input type="hidden" name="userId" value="<%=userId%>">
    <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">      
    </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
<!-- end #container --></div>
</body>
</html>
