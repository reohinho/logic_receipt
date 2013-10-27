<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page import="transaction.databean.*" %>
<%@ page import="common.jsp.databean.*" %>
<%@ page import="common.jsp.*" %>
<%@ page import="generic.servlet.*" %>
<%@ page import="java.util.Vector"%>

<%

//Page Info
  String pgId = "TX_INPUT" ;
  int pgSeq = 2; 
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
  	
  
  Vector txList = vo.getLineVec();
  String[] itemNo = {"","",""};
  String[] itemList = {"","",""};
  String[] qtyList = {"","",""};
  String[] priceList = {"","",""};
  WebFormGenerator wfg = new WebFormGenerator(vo);
  for(int i=0;i<3;i++)
  {
  	if(txList!=null&&txList.size()==3)
  	{
  		itemNo[i] = ((TransactionLineData)txList.get(i)).getData(TransactionLineData.itemNo);
  		qtyList[i] = ((TransactionLineData)txList.get(i)).getData(TransactionLineData.qty);
		priceList[i] = ((TransactionLineData)txList.get(i)).getData(TransactionLineData.unitPrice);
  	}
  	itemList[i] = GeneralDataInHtml.getPulldownItemList(vo.getData(TransactionData.txType), "itemNo", itemNo[i], "<OPTION VALUE=\"\">---- Please Select ----", "onchange=showQty();checkTotal()");
  } 
  String countryList = GeneralDataInHtml.getPulldownCountryList("nationality",vo.getData(TransactionData.nationality),"<OPTION VALUE=\"\">---- Please Select ----", "");
  String userId = (String)request.getSession().getAttribute("userId");
  boolean canSearch = GeneralDataInHtml.isAccessible(userId, "TE");
  
  String officerName = GeneralDataInHtml.getUserName(userId);
  vo.setData(TransactionData.officerId, userId);
   

  boolean canAdjustAmt = vo.getData(vo.txType).equals("C");
  
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<% 
String contextPath="http://192.168.0.3:8081/logic_receipt_sb";
String contextServletPath = "http://192.168.0.3:8081/logic_receipt_sb/servlet/"; %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Logic Receipt</title>
<link href="http://192.168.0.3:8081/logic_receipt_sb/css/newcirc.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
body {
	font: 100% Verdana, Arial, Helvetica, sans-serif;
	background: #666666;
	margin: 0; /* it's good practice to zero the margin and padding of the body element to account for differing browser defaults */
	padding: 0;
	text-align: center; /* this centers the container in IE 5* browsers. The text is then set to the left aligned default in the #container selector */
	color: #000000;
}

/* Tips for Elastic layouts 
1. Since the elastic layouts overall sizing is based on the user's default fonts size, they are more unpredictable. Used correctly, they are also more accessible for those that need larger fonts size since the line length remains proportionate.
2. Sizing of divs in this layout are based on the 100% font size in the body element. If you decrease the text size overall by using a font-size: 80% on the body element or the #container, remember that the entire layout will downsize proportionately. You may want to increase the widths of the various divs to compensate for this.
3. If font sizing is changed in differing amounts on each div instead of on the overall design (ie: #sidebar1 is given a 70% font size and #mainContent is given an 85% font size), this will proportionately change each of the divs overall size. You may want to adjust based on your final font sizing.
*/
.oneColElsCtrHdr #container {
	background: #FFFFFF;
	margin: 0 auto; /* the auto margins (in conjunction with a width) center the page */
	border: 1px solid #000000;
	text-align: left; /* this overrides the text-align: center on the body element. */
}
.oneColElsCtrHdr #header {
	padding: 0px 0px 0px 10px;  /* this padding matches the left alignment of the elements in the divs that appear beneath it. If an image is used in the #header instead of text, you may want to remove the padding. */
	margin: 0; /* zeroing the margin of the last element in the #header div will avoid margin collapse - an unexplainable space between divs. If the div has a border around it, this is not necessary as that also avoids the margin collapse */
	background-color: #6666FF;
} 
.oneColElsCtrHdr #header h1 {
	margin: 0; /* zeroing the margin of the last element in the #header div will avoid margin collapse - an unexplainable space between divs. If the div has a border around it, this is not necessary as that also avoids the margin collapse */
	padding: 0px; /* using padding instead of margin will allow you to keep the element away from the edges of the div */
}
.oneColElsCtrHdr #toolbar { 
	background: #000000;
	color: #FFFFFF;
	text-align: center; 
	padding: 0px 0px 0px 0px;
	margin: 0; /* zeroing the margin of the last element in the #header div will avoid margin collapse - an unexplainable space between divs. If the div has a border around it, this is not necessary as that also avoids the margin collapse */
}
.oneColElsCtrHdr #toolbar p {
	color: #000000
	margin: 0; /* zeroing the margins of the first element will avoid the possibility of margin collapse - a space between divs */
	padding: 0px 10px 0px 0px; /* padding on this element will create space, just as the the margin would have, without the margin collapse issue */
} 
.oneColElsCtrHdr #mainContent {
	padding: 10px 10px; /* remember that padding is the space inside the div box and margin is the space outside the div box */
	background: #FFFFFF;
	height: 420px;
	OVERFLOW: auto;
}
.oneColElsCtrHdr #mainContent h1{
	background: #FFFFFF;
	padding: 10px 20px; /* using padding instead of margin will allow you to keep the element away from the edges of the div */

}
.oneColElsCtrHdr #mainContent columnhead{
	padding: 0 20px; /* remember that padding is the space inside the div box and margin is the space outside the div box */
}
.oneColElsCtrHdr #footer { 
	padding: 0 10px; /* this padding matches the left alignment of the elements in the divs that appear above it. */
	background:#000000;
} 
.oneColElsCtrHdr #footer p {
	margin: 0; /* zeroing the margins of the first element in the footer will avoid the possibility of margin collapse - a space between divs */
	padding: 10px 0; /* padding on this element will create space, just as the the margin would have, without the margin collapse issue */
}
.style7 {font-size: 18px; font-weight: bold; }
.style10 {
	font-size: 12pt;
	text-indent: 10px;
}
.style12 {font-size: 12pt; text-indent: 10px; font-weight: bold; }
-->
</style>
</head>
<script language="JavaScript" src="../js/genericFunc.js"></script>
<body class="oneColElsCtrHdr" onload="document.form1.txType.focus();">

<script language="JavaScript" type="text/JavaScript">
function dispatch(key) {
  var ca = document.cookie.split(';');
  var nameEQ = "printer" + "=";
  for(var i=0; i < ca.length; i++) 
  {
	var c = ca[i];
	while (c.charAt(0)==' ') c = c.substring(1, c.length); //delete spaces
	if (c.indexOf(nameEQ) == 0) 
		document.forms[1].printerName.value = c.substring(nameEQ.length, c.length);
  }
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
function showQty()
{		
		
		if((document.form1.itemNo[0].value != '')&&(searchItem.elements('preItemNo1').value != document.form1.itemNo[0].value))
		{	
			var itemDefaultAmt = searchItem.elements(document.form1.itemNo[0].value).value;
			document.getElementById('qty1').style.display = 'block'; 
			document.getElementById('price1').style.display = 'block'; 
			document.getElementById('total1').style.display = 'block'; 
			document.form1.qty[0].value = 1 ;
			document.form1.price[0].value = itemDefaultAmt ;
			searchItem.elements('preItemNo1').value = document.form1.itemNo[0].value;
		}
		else if (document.form1.itemNo[0].value == ''){  
			document.getElementById('qty1').style.display = 'none'; 
			document.getElementById('price1').style.display = 'none'; 
			document.getElementById('total1').style.display = 'none'; 
		}

		if((document.form1.itemNo[1].value != '')&&(searchItem.elements('preItemNo2').value != document.form1.itemNo[1].value))
		{	
			var itemDefaultAmt = searchItem.elements(document.form1.itemNo[1].value).value;
			document.getElementById('qty2').style.display = 'block'; 
			document.getElementById('price2').style.display = 'block'; 
			document.getElementById('total2').style.display = 'block'; 
			document.form1.qty[1].value = 1 ;
			document.form1.price[1].value = itemDefaultAmt ;
			searchItem.elements('preItemNo2').value = document.form1.itemNo[1].value;
		}
		else if (document.form1.itemNo[1].value == ''){  
			document.getElementById('qty2').style.display = 'none'; 
			document.getElementById('price2').style.display = 'none'; 
			document.getElementById('total2').style.display = 'none'; 
		}
		
		if((document.form1.itemNo[2].value != '')&&(searchItem.elements('preItemNo3').value != document.form1.itemNo[2].value))
		{	
			var itemDefaultAmt = searchItem.elements(document.form1.itemNo[2].value).value;
			document.getElementById('qty3').style.display = 'block'; 
			document.getElementById('price3').style.display = 'block'; 
			document.getElementById('total3').style.display = 'block'; 
			document.form1.qty[2].value = 1 ;
			document.form1.price[2].value = itemDefaultAmt; 
			searchItem.elements('preItemNo3').value = document.form1.itemNo[2].value;
		}
		else if (document.form1.itemNo[2].value == ''){  
			document.getElementById('qty3').style.display = 'none'; 
			document.getElementById('price3').style.display = 'none'; 
			document.getElementById('total3').style.display = 'none'; 
		}
}

function checkTotal() {
	
<%
if(!canAdjustAmt)
{
%>
		if(document.form1.itemNo[0].value != '')
		{	
			document.form1.price[0].value = searchItem.elements(document.form1.itemNo[0].value).value;
		}

		if(document.form1.itemNo[1].value != '')
		{	
			document.form1.price[1].value = searchItem.elements(document.form1.itemNo[1].value).value;
		}

		if(document.form1.itemNo[2].value != '')
		{	
			document.form1.price[2].value = searchItem.elements(document.form1.itemNo[2].value).value;
		}


<%
}
%>

		

		if(document.form1.itemNo[0].value != '')
		{	
			if(!isNaN(document.form1.price[0].value)) {
				document.form1.total[0].value = document.form1.qty[0].value * document.form1.price[0].value;
			}
			else {
				document.form1.price[0].value = 0;
				document.form1.total[0].value = 0;
			}			
		}

		if(document.form1.itemNo[1].value != '')
		{	
			if(!isNaN(document.form1.price[1].value)) {
				document.form1.total[1].value = document.form1.qty[1].value * document.form1.price[1].value;
			}
			else {
				document.form1.price[1].value = 0;
				document.form1.total[1].value = 0;
			}			

		}

		if(document.form1.itemNo[2].value != '')
		{	
			if(!isNaN(document.form1.price[2].value)) {
				document.form1.total[2].value = document.form1.qty[2].value * document.form1.price[2].value;
			}
			else {
				document.form1.price[2].value = 0;
				document.form1.total[2].value = 0;
			}		
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

    <fieldset>
    <legend><u>Transaction Input</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">
		<%=wfg.TDforLabel(vo.txNo)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
          <input type="button" name="Search" id="Search" value="Search" onclick="document.searchItem.submit();"/>
        </div></td>
      </tr>
	<tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.txType)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
          <select name="txType" id="txType" onchange="loadItemList()">
            <option value="A" <%if(vo.getData(vo.txType).equals("A")){%>selected<%}%>>Visa</option>
            <option value="B" <%if(vo.getData(vo.txType).equals("B")){%>selected<%}%>>Others</option>
            <option value="C" <%if(vo.getData(vo.txType).equals("C")){%>selected<%}%>>Misc.</option>
      	  </select>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.name)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.name, 30, "maxlength=30")%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.name2)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.name2, 30, "maxlength=30")%>
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.gender)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <select name="gender" id="gender">
         <option value="">--Please Select--</option>
         <option value="M">M</option>
		 <option value="F">F</option>      
		 </select>   
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="102"><div align="left">
		<%=wfg.TDforLabel(vo.dob)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.dob, 10)%> (DD/MM/YY)
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">
		<%=wfg.TDforLabel(vo.refNo)%>
		</div></td>
        <td colspan="3" align="left" class="oneColElsCtrHdr"><div align="left">
         <%=wfg.htmlFieldInput(vo.refNo, 40)%>
        </div></td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.nationality)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
		<%=countryList%>
        </div></td>
      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="right"></div></td>
        <td width="152" align="center" bordercolor="#000000"><div align="center">Item</div></td>
        <td width="119" align="center" bordercolor="#000000"><div align="center">Quantity</div></td>
        <td align="center" bordercolor="#000000"><div align="center">Price</div></td>
        <td align="center" bordercolor="#000000"><div align="center">Total</div></td>
      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="center">1.</div></td>
        <td align="center" bordercolor="#000000"><div align="center">
        <%=itemList[0]%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
          <div id=qty1 style="display:<%if(itemNo[0].equals("")){%>none<%}else{%>block<%}%>">  <input name="qty" type="text" id="qty" size="8" onblur="checkTotal();" value ="<%=qtyList[0]%>" /> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=price1 style="display:<%if(itemNo[0].equals("")){%>none<%}else{%>block<%}%>">  <input name="price" type="text" id="price" size="8" onblur="checkTotal();" value ="<%=priceList[0]%>" /> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=total1 style="display:<%if(itemNo[0].equals("")){%>none<%}else{%>block<%}%>"> <input name="total" type="text" id="total" size="8" onblur="checkTotal();" value ="" /> </div> 
	  </div></td>

      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="center">2.</div></td>
        <td align="center" bordercolor="#000000"><div align="center">
        <%=itemList[1]%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
          <div id=qty2 style="display:<%if(itemNo[1].equals("")){%>none<%}else{%>block<%}%>">  <input name="qty" type="text" id="qty" size="8" onblur="checkTotal();" value="<%=qtyList[1]%>"/> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=price2 style="display:<%if(itemNo[1].equals("")){%>none<%}else{%>block<%}%>">  <input name="price" type="text" id="price" size="8" onblur="checkTotal();" value ="<%=priceList[1]%>" /> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=total2 style="display:<%if(itemNo[1].equals("")){%>none<%}else{%>block<%}%>"> <input name="total" type="text" id="total" size="8" onblur="checkTotal();" value ="" /> </div> 
	  </div></td>
      </tr>
       <tr>
        <td align="center" bordercolor="#000000"><div align="center">3.</div></td>
        <td align="center" bordercolor="#000000"><div align="center">
        <%=itemList[2]%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
          <div id=qty3 style="display:<%if(itemNo[2].equals("")){%>none<%}else{%>block<%}%>">  <input name="qty" type="text" id="qty" size="8" onblur="checkTotal();" value ="<%=qtyList[2]%>"/> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=price3 style="display:<%if(itemNo[2].equals("")){%>none<%}else{%>block<%}%>">  <input name="price" type="text" id="price" size="8" onblur="checkTotal();" value ="<%=priceList[2]%>" /> </div>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="center">
		<div id=total3 style="display:<%if(itemNo[2].equals("")){%>none<%}else{%>block<%}%>"> <input name="total" type="text" id="total" size="8" onblur="checkTotal();" value ="" /> </div> 
	  </div></td>
      </tr>
      <tr>
        <td align="center" bordercolor="#000000"><div align="left">
		<%=wfg.TDforLabel(vo.paymentType)%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="left">
 		  <select name="paymentType" id="paymentType" onchange="showPaymentRef()">
            <option value="cash" <%if(vo.getData(vo.paymentType).equals("case")){%>selected<%}%>>Cash</option>
            <option value="cheque" <%if(vo.getData(vo.paymentType).equals("cheque")){%>selected<%}%>>Cheque</option>
            <option value="creditcard" <%if(vo.getData(vo.paymentType).equals("creditcard")){%>selected<%}%>>Credit Card</option>
      	  </select>        </div></td>
        <td align="center" bordercolor="#000000">
        <div id="paymentRefDiv" align="left" style="display:none">
         <%=wfg.htmlFieldInput(vo.paymentRef, 20)%>
        </div></td>
        <td align="center" bordercolor="#000000"><div align="left">
        </div></td>
      </tr>
      <tr>
        <td align="center"><div align="left">
		<%=wfg.TDforLabel(vo.officerId)%>
		</div></td>
        <td colspan="3" align="center"><div align="left">
          <%=officerName%>
         <%=wfg.htmlFieldHidden(vo.officerId)%>
         <%=wfg.htmlFieldHidden(vo.printerName)%>
        </div></td>
      </tr>
      <tr>
        <td colspan="4" align="center">
          <div align="center">
        <input type="button" name="Save" id="Save" value="Save and Print" onClick="dispatch('<%=ICreateWorkFlow.kAddSave %>')"/>
            <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
    		<input type="hidden" name="<%= IBaseConstants.kObjectIndex %>" value="<%= vo.getSystemObjectId() %>">      
            </div></td>
      </tr>
    </table>
    <p align="right">&nbsp;</p>
    </fieldset>
    <!-- end #mainContent --></div>
 </form>
 <form name="searchItem" method="post" action="TransactionSearchServlet"  >
  <input type="hidden" name="<%= IBaseConstants.kCommand %>" value="">
  <input type="hidden" name="preItemNo1" value="">
  <input type="hidden" name="preItemNo2" value="">
  <input type="hidden" name="preItemNo3" value="">
<%=GeneralDataInHtml.getDefaultPriceOfItems()%>
</form>
<!-- end #container --></div>
</body>
</html>
