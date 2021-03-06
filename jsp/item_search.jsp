<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_ITEM" ;
  int pgSeq = 0; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "ItemSearchServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  ItemSearchFormData vo = (ItemSearchFormData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
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

    <fieldset>
    <legend><u>Item Maintenance</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.itemNo)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
        <%=wfg.htmlFieldInput(vo.itemNo, 10)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.itemDescription)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.itemDescription, 40)%>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="Search" id="Save" value="Search" onClick="dispatch('<%=ISearchWorkFlow.kSearch%>')"/>
        <input type="button" name="cancel" id="cancel" value="Cancel" />
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
