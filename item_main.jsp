<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Logic Receipt</title>
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

<body class="oneColElsCtrHdr">

<div id="container">
  <div id="header">
  <img src="img/Consulate-General.gif" width="304" height="63" />  </div>
  <div id ="toolbar">
    <table width="100%" border="0">
      <tr>
        <td width="62%" align="left" valign="bottom" nowrap="nowrap"><span class="style12"> [LEO CHING] &gt;&gt; Item Maintenance</span></td>
        <td width="15%">&nbsp;</td>
        <td width="23%" valign="bottom" nowrap="nowrap"><span class="style10">
          <input name="shortcut" type="text" id="shortcut" size="10" />
		</span></td>
      </tr>
    </table>
  <!-- end #header --></div>
  <div id="mainContent">
    <br/>
    <fieldset>
    <legend><u>Item Maintenance</u><u></u></legend>
    <table width="80%" border="0" align="center" cellpadding="5">
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">Item No.:</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
          <input type="text" name="itemno" id="itemno" />
          <input type="button" name="search" id="search" value="Search" />
          <input type="button" name="delete" id="delete" value="Delete" />
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr" width="129"><div align="left">Item:</div></td>
        <td colspan="4" align="left" class="oneColElsCtrHdr"><div align="left">
          <input name="itemname" type="text" id="itemname" size="40" />
        </div></td>
      </tr>
      <tr>
        <td align="left" class="oneColElsCtrHdr"><div align="left">Amount:</div></td>
        <td width="144" align="left" class="oneColElsCtrHdr"><div align="left">
          <input type="text" name="amount" id="amount" />
        </div></td>
        <td width="46" align="left" class="oneColElsCtrHdr">&nbsp;</td>
        <td width="142" align="left" class="oneColElsCtrHdr">&nbsp;</td>
        <td width="55" align="left" class="oneColElsCtrHdr">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><div align="left">Max Qty.:</div></td>
        <td align="center"><div align="left">
          <input type="text" name="maxqty" id="maxqty" />
        </div></td>
        <td align="left" class="oneColElsCtrHdr">Type:</td>
        <td align="left" class="oneColElsCtrHdr"><select name="type" id="type">
            <option value="A">Type A</option>
            <option value="B">Type B</option>
            <option value="C">Type C</option>
        </select></td>
        <td align="center">&nbsp;</td>
      </tr>
      <tr>
        <td align="center"><div align="left">Payment Type:</div></td>
        <td colspan="4" align="center"><div align="left">
          <select name="paymenttype" id="paymenttype">
            <option value="cash">Cash</option>
            <option value="cheque">Cheque</option>
            <option value="creditcard">Credit Card</option>
            <option value="bankin">Bank-In</option>
          </select>
        </div></td>
      </tr>
      <tr>
        <td colspan="5" align="center">
        <input type="button" name="Save" id="Save" value="Save" />
        <input type="button" name="Cancel" id="Cancel" value="Cancel" /></td>
      </tr>
    </table>
    </fieldset>
    <!-- end #mainContent --></div>
<!-- end #container --></div>
</body>
</html>
