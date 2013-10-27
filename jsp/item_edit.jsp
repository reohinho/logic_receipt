<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="maintenance.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>

<%

//Page Info
  String pgId = "MA_ITEM" ;
  int pgSeq = 4; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "ItemEditServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  ItemData vo = (ItemData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
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
         <%=vo.getData(vo.itemNo)%>
         <%=wfg.htmlFieldHidden(vo.itemNo)%>
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
        <td align="left" class="oneColElsCtrHdr">
        <div align="left"><%=wfg.TDforLabel(vo.amount)%></div></td>
        <td width="144" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.amount, 8)%>
        </div></td>
        <td width="46" align="left" class="oneColElsCtrHdr"></td>
        <td width="142" align="left" class="oneColElsCtrHdr"></td>
        <td width="55" align="left" class="oneColElsCtrHdr"></td>
      </tr>
      <tr>
        <td align="center">
        <div align="left">
        <%=wfg.TDforLabel(vo.maxQty)%>
        </div></td>
        <td align="center"><div align="left">
          <%=wfg.htmlFieldInput(vo.maxQty, 8)%>
        </div></td>
        <td align="left" class="oneColElsCtrHdr">
        <%=wfg.TDforLabel(vo.itemType)%></td>
        <td align="left" class="oneColElsCtrHdr">
        <select name="<%=vo.getFieldname(vo.itemType)%>" id="<%=vo.getFieldname(vo.itemType)%>">
            <option value="A" <%if(vo.getData(vo.itemType).equals("A")){%>selected<%}%>>Type A</option>
            <option value="B" <%if(vo.getData(vo.itemType).equals("B")){%>selected<%}%>>Type B</option>
            <option value="C" <%if(vo.getData(vo.itemType).equals("C")){%>selected<%}%>>Type C</option>
        </select></td>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.paymentType)%></div></td>
        <td colspan="4" align="center"><div align="left">
          <select name="<%=vo.getFieldname(vo.paymentType)%>" id="<%=vo.getFieldname(vo.paymentType)%>">
            <option value="cash" <%if(vo.getData(vo.paymentType).equals("cash")){%>selected<%}%>>Cash</option>
            <option value="cheque" <%if(vo.getData(vo.paymentType).equals("cheque")){%>selected<%}%>>Cheque</option>
            <option value="creditcard" <%if(vo.getData(vo.paymentType).equals("creditcard")){%>selected<%}%>>Credit Card</option>
            <option value="bankin" <%if(vo.getData(vo.paymentType).equals("bankin")){%>selected<%}%>>Bank-In</option>
          </select>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="save" id="save" value="Update" onClick="dispatch('<%=IEditWorkFlow.kEditSave %>')"/>
        <input type="button" name="delete" id="delete" value="Delete" onClick="document.form1.action='ItemDeleteServlet' ; dispatch('<%=IViewDeleteWorkFlow.kDelete %>')"/>
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
