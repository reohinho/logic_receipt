<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "SY_LOGIN" ;
  int pgSeq = 0; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "LoginServlet" ;
  String servletLocation = servletName ;
  
  UserData vo = new UserData();
  WebFormGenerator wfg = new WebFormGenerator(vo);
  
  String userList = GeneralDataInHtml.getPulldownUserList("userId", "", "<OPTION VALUE=\"\">---- Please Select ----", "");    
  
  
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file = "inc_common_jsp.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dispatch(key) {
  //document.cookie = "printer=\\\\counterpc3\\Printer3; expires=Fri, 12 Mar 2040 05:28:21 UTC; path=/";
  //alert(document.cookie);
  document.forms[1].<%= IBaseConstants.kCommand %>.value=key ;
  document.forms[1].submit() ;
}
</script>

<div id="container">
<div id="header">
  <form name="shortcut" method="post" action="<%=contextPath%>/servlet/shortcutServlet">

  <img src="<%=contextPath%>/img/Consulate-General.gif" width="304" height="63" />  </div>
  <div id ="toolbar">
    <table width="100%" border="0">
      <tr>
        <td width="44%" align="left" valign="bottom" nowrap="nowrap"><span class="style12"></span></td>
        <td width="33%">&nbsp;</td>
        <td width="23%" valign="bottom" nowrap="nowrap">          
        <input name="shortcut" type="text" id="shortcut" size="10" />
        <span class="style10">
		</span></td>
      </tr>
    </table>
        </form>
    
 <!-- end #header --></div>
  <div id="mainContent">
    <br/>
    <!-- =====> START OF PAGE CONTENT <====== !-->          
	<%= wfg.htmlErrorMsg() %>
<form name="form1" method="post" action="<%= contextServletPath+servletLocation %>">

    <fieldset>
    <legend><u>Login</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<td class="fieldname">User</td>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=userList%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.password)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<input type="password" name="password" id="password" value="">
		<input type="hidden" name="contextPath" id="contextPath" value="<%=contextPath%>">
		<input type="hidden" name="contextServletPath" id="contextServletPath" value="<%=contextServletPath%>">
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="login" id="login" value="Login" onClick="dispatch('<%=ICreateWorkFlow.kAddSave %>')"/>
        <input type="button" name="cancel" id="cancel" value="Cancel" />
    <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">      
    </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
 <form name="searchItem" method="post" action="<%= contextServletPath%>\LoginServlet"  >
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
</form>
<!-- end #container --></div>
</body>
</html>
