<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="transaction.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector" %>


<%

//Page Info
  String pgId = "TX_INPUT" ;
  int pgSeq = 5; 
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
  
  String isVoid = vo.getData(TransactionData.isVoid);

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
<% if(isVoid.equals("Y")){
%>
Are you sure to void this receipt?    
<%}else {%>
Are you sure to re-print?    
<%} %>
<input type="button" name="Submit" value="Yes" onClick="set_processing_cmd(document.form1,this,'<%=IEditWorkFlow.kEditConfirm %>');">
<input type="button" name="Submit2" value="No" onClick="dispatch('<%=IEditWorkFlow.kEditBack %>');">
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
			//unitPrice = (Double)itemDetail.get(1);
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
         <%=vo.getData(vo.voidReason)%>
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
