<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_USER" ;
  int pgSeq = 3; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "UserCreateServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  UserData vo = (UserData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
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
<input type="button" name="Submit" value="Yes" onClick="set_processing_cmd(document.form1,this,'<%=ICreateWorkFlow.kAddConfirm %>');">
<input type="button" name="Submit2" value="No" onClick="dispatch('<%=ICreateWorkFlow.kAddBack %>');">
    <fieldset>
    <legend><u>User Maintenance</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.userId)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.userId)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.userName)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.userName)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.transaction)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.transaction)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.item)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.item)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.bankCode)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.bankCode)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.country)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.country)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.holiday)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.holiday)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.user)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.user)%>
        </div></td>
      </tr>
	  <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.backup)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.backup)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.message)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.message)%>
        </div></td>
      </tr>
         <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.report)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.report)%>
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
