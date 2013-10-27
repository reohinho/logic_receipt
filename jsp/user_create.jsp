<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_USER" ;
  int pgSeq = 2; 
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

    <fieldset>
    <legend><u>User Maintenance</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.userId)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.userId, 20)%>
         <input type="button" name="search" id="search" value="Search" onclick="document.searchItem.submit();"/>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.userName)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.userName, 40)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.password)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.password, 20)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.transaction)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.transaction, 10)%> X=Input E=Enquiry V=Void R=Reprint
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.item)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.item, 10)%> C=Create D=Delete E=Edit S=Search
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.bankCode)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.bankCode, 10)%> C=Create D=Delete E=Edit S=Search
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.country)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.country, 10)%> C=Create D=Delete E=Edit S=Search
        </div></td>
      </tr>
         <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.holiday)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.holiday, 10)%> C=Create D=Delete E=Edit S=Search
        </div></td>
      </tr>
      </tr>
         <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.user)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.user, 10)%> C=Create D=Delete E=Edit S=Search
        </div></td>
      </tr>
	    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.backup)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.backup, 10)%> Y/N
        </div></td>
      </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.message)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.message, 10)%> E=Edit
        </div></td>
      </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.report)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.report, 10)%>
        </div></td>
      </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         R1=Daily Transaction Report
        </div></td>
       </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         R2=Daily Transaction Report by Officer
        </div></td>
       </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         R3=Transaction Summary
        </div></td>
       </tr>
        <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         R4=Report 4
        </div></td>
      </tr>
      
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="Save" id="Save" value="Save" onClick="dispatch('<%=ICreateWorkFlow.kAddSave %>')"/>
    <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    <input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">      
    </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
 <form name="searchItem" method="post" action="UserSearchServlet"  >
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
</form>
<!-- end #container --></div>
</body>
</html>
