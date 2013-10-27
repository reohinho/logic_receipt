<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="transaction.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector" %>


<%

//Page Info
  String pgId = "TX_INPUT" ;
  int pgSeq = 4; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "TransactionEditServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  TransactionData vo = (TransactionData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
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
function dispatch(key, isVoid) {

  var ca = document.cookie.split(';');
  var nameEQ = "printer" + "=";
  for(var i=0; i < ca.length; i++) 
  {
	var c = ca[i];
	while (c.charAt(0)==' ') c = c.substring(1, c.length); //delete spaces
	if (c.indexOf(nameEQ) == 0) 
		document.forms[1].printerName.value = c.substring(nameEQ.length, c.length);
  }

  document.forms[1].isVoid.value = isVoid;
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
<%

  boolean isAllowVoid = GeneralDataInHtml.isAllowAccess(userId2, "TRANSACTION", "V");
  boolean isAllowReprint = GeneralDataInHtml.isAllowAccess(userId2, "TRANSACTION", "R");

%>
<form name="form1" method="post" action="<%= servletLocation %>">

    <fieldset>
    <legend><u>Transaction</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
    <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.txNo)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.txNo)%>
         <%=wfg.htmlFieldHidden(vo.txNo)%>
         <input type="button" name="void" id="void" value="Void" <%if(!isAllowVoid){%> disabled=true <%}%> onClick="dispatch('<%=IEditWorkFlow.kEditSave %>','Y')"/>
         <input type="button" name="reprint" id="reprint" value="Re-Print" <%if(!isAllowReprint){%> disabled=true <%}%> onClick="dispatch('<%=IEditWorkFlow.kEditSave %>','N')"/>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.name)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=vo.getData(vo.name)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.name2)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=vo.getData(vo.name2)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.gender)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=vo.getData(vo.gender)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="129">
        <div align="left">
		<%=wfg.TDforLabel(vo.dob)%>
		</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
		<%=vo.getData(vo.dob)%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">
		<%=wfg.TDforLabel(vo.refNo)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.refNo)%>
        </div></td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.txType)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
          <%=GeneralDataInHtml.getItemTypeDesc(vo.getData(TransactionData.txType))%>
        </div></td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.nationality)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
         <%=vo.getData(vo.nationality)%>
        </div></td>
      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="right"></div></td>
        <td width="152" align="center" bordercolor="#000000"><div align="center">Item</div></td>
        <td width="119" align="center" bordercolor="#000000"><div align="center">Quantity</div></td>
        <td align="center" bordercolor="#000000"><div align="center">Total</div></td>
      </tr>
<%
	Vector lineVec = vo.getLineVec();
	for(int i=0; i< lineVec.size(); i++)
	{
		TransactionLineData tlData = (TransactionLineData)lineVec.get(i);
		String itemNo = tlData.getData(TransactionLineData.itemNo);
		String itemDescription = "";
		String total = "";
		if(itemNo!=null&&!itemNo.equals(""))
		{
			Vector itemDetail = GeneralDataInHtml.getItemDescription(tlData.getData(tlData.itemNo));
			Double unitPrice = Double.parseDouble(tlData.getData(TransactionLineData.unitPrice));
			Double lineQty = Double.parseDouble(tlData.getData(TransactionLineData.qty));
			itemDescription = (String)itemDetail.get(0) + " ($"+ unitPrice.toString() +")";
			total = new Double(unitPrice.doubleValue() * lineQty.doubleValue()).toString();
		}
%>      
      <tr>
        <td align="center" bordercolor="#000000"><div align="center">
		<%=tlData.getData(TransactionLineData.lineNo)%></div></td>
        <td align="center" bordercolor="#000000"><div align="center">
        <%=itemDescription%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<%=tlData.getData(TransactionLineData.qty)%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center"><%=total%>
        </div></td>
      </tr>
 <%
 	}
 %>     
      <tr>
        <td align="center" bordercolor="#000000"><div align="left">
		<%=wfg.TDforLabel(vo.paymentAmt)%>
		</div></td>
        <td colspan="3" align="center" bordercolor="#000000"><div align="left">
         <%=vo.getData(vo.paymentAmt)%>
        </div></td>
      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="left">
		<%=wfg.TDforLabel(vo.paymentType)%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="left">
 		  <%=GeneralDataInHtml.getPaymentTypeDesc(vo.getData(TransactionData.paymentType))%></div></td>
        <td align="center" bordercolor="#000000">
        <div align="left">
         <%=wfg.htmlFieldInput(vo.paymentRef, 20, "readonly=true")%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="left">
        </div></td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.officerId)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
         <%=vo.getData(vo.officerId)%>
        </div></td>
      </tr>
     <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.voidReason)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
         <%=wfg.htmlFieldInput(vo.voidReason)%>
         <%=wfg.htmlFieldHidden(vo.isVoid)%>
         <%=wfg.htmlFieldHidden(vo.printerName)%>
        </div></td>
      </tr>
      <tr>
        <td colspan="3" align="center">
        
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
