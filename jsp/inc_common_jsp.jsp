<% 
String contextPath="http://192.168.0.1:8080/logic_receipt";
String contextServletPath = "http://192.168.0.1:8080/logic_receipt/servlet/"; %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Logic Receipt</title>
<link href="http://192.168.0.1:8080/logic_receipt/css/newcirc.css" rel="stylesheet" type="text/css">
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
<body class="oneColElsCtrHdr" onload="document.shortcut.shortcut.focus();">