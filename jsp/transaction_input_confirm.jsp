<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="transaction.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector"%>

<%

//Page Info
  String pgId = "TX_INPUT" ;
  int pgSeq = 3; 
  String thisPageID = pgId + "," + pgSeq ;

//Servlet Info
  String servletName = "TransactionInputServlet" ;
  String servletLocation = servletName ;
  

// get view object from caller  
  TransactionData vo = (TransactionData) request.getAttribute(IBaseConstants.kGenericWebFormData) ;
  if (vo == null) 
  { // if no such object, redirect to servlet
    RequestDispatcher dispatcher = request.getRequestDispatcher("/servlet/" + servletName) ;
    dispatcher.forward(request,response);
  }
  WebFormGenerator wfg = new WebFormGenerator(vo);
  String itemList = GeneralDataInHtml.getPulldownItemList(vo.getData(TransactionData.txType), vo.getFieldname(TransactionData.txType), vo.getData(TransactionData.txType), "<OPTION VALUE=\"\">---- Please Select ----", "");    
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file = "inc_common_jsp.jsp"%>

<script language="JavaScript" type="text/JavaScript">
function dispatch(key) {
  document.forms[1].<%= IBaseConstants.kCommand %>.value=key ;
  document.forms[1].submit() ;
}
function loadItemList() { 
    document.form1.<%=IBaseConstants.kCommand%>.value='<%=ICreateWorkFlow.kReload1%>';
    document.form1.submit();
}
function showPaymentRef()
{
	if(document.form1.paymentType.value=='cheque'||document.form1.paymentType.value=='creditcard')
	{
		document.getElementById('paymentRefDiv').style.display = 'block';
	}
	else
	{
		document.getElementById('paymentRefDiv').style.display = 'none';
	}
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
    <legend><u>Transaction Input</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">
		<%=wfg.TDforLabel(vo.txNo)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.name)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.name)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.name2)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.name2)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.gender)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=vo.getData(vo.gender)%>
        </div></td>
      </tr>
       <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.dob)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
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
	double paymentAmt = 0;
	for(int i=0; i< lineVec.size(); i++)
	{
		TransactionLineData tlData = (TransactionLineData)lineVec.get(i);
		String itemNo = tlData.getData(TransactionLineData.itemNo);
		String itemDescription = "";
		String total = "";
		String qtyStr = tlData.getData(TransactionLineData.qty);
		String priceStr = tlData.getData(TransactionLineData.unitPrice);

		if(itemNo!=null&&!itemNo.equals(""))
		{
			Vector itemDetail = GeneralDataInHtml.getItemDescription(tlData.getData(tlData.itemNo));
			Double unitPrice = new Double(0);
			Double lineQty = new Double(1);
			if (qtyStr!=null && !qtyStr.equals("")) {
				lineQty = Double.parseDouble(tlData.getData(TransactionLineData.qty));
			}
			if (priceStr!=null && !priceStr.equals("")) {
				unitPrice = Double.parseDouble(tlData.getData(TransactionLineData.unitPrice));
			}
			itemDescription = (String)itemDetail.get(0) + " ($"+ unitPrice.toString() +")";
			total = new Double(unitPrice.doubleValue() * lineQty.doubleValue()).toString();
			paymentAmt += unitPrice.doubleValue() * lineQty.doubleValue();
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
         <%=paymentAmt%>
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
        <td colspan="4" align="center">
          <div align="center">
            <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    		<input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">   
    		<%=wfg.htmlFieldHidden(vo.txNo)%>
    		<%=wfg.htmlFieldHidden(vo.name)%>
    		<%=wfg.htmlFieldHidden(vo.refNo)%>
    		<%=wfg.htmlFieldHidden(vo.txType)%>
    		<%=wfg.htmlFieldHidden(vo.paymentType)%>
    		<%=wfg.htmlFieldHidden(vo.paymentAmt)%>
    		<%=wfg.htmlFieldHidden(vo.officerId)%> 		   
            </div></td>
      </tr>
    </table>
    <p align="right">&nbsp;</p>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
 <form name="searchItem" method="post" action="TransactionSearchServlet"  >
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
</form>
<!-- end #container --></div>
</body>
</html>
