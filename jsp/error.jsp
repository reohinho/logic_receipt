<%--
$Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/public_html/error.jsp,v $
$Author: scmp $
$Date: 2008/04/28 02:41:02 $
$Revision: 1.1.1.1 $
--%>
<%@ page import="com.scmp.circ.common.jsp.*,
com.scmp.circ.common.jsp.databean.*,
com.scmp.circ.common.databean.*,
com.scmp.circ.utility.Log,
com.scmp.circ.security.ejb.object.UserVO"%>
<%@ include file="/inc_common_jsp_header.jsp" %>
<%


  String errTx = (String)request.getAttribute ("ERROR_TX") ;
  if (errTx!=null) errTx = ":"+errTx ;
  else errTx = "" ;
  String errCode = (String)request.getAttribute ("ERROR_CODE") ;
  String errMsg = (String)request.getAttribute ("ERROR_MSG") ;
  String errReturnPageId = (String)request.getAttribute ("ERROR_BACKID") ;
  String errReturnPageName = (String)request.getAttribute ("ERROR_BACKNAME") ;
  boolean isClosewindow = (String)request.getAttribute ("ERROR_CLOSEWINDOW") != null ;

  JSPShares shObj = JSPShares.getInstance () ;
//  Log log = shObj.getLogObject () ;

//System.out.println ("error page: " + errCode + "-" + errMsg + "-" + errReturnPageId + "-" + errReturnPageName) ;
  
  // log into trace file
  Log.doLogFlush(Log.LOGTO_USER_STREAM, Log.LOGFROM_APPLICATION, Log.LOG_ALL, errCode+errTx, errMsg, shObj.getLogWebTraceStream()) ;

  // log into user activity file
  String uid =   ((UserVO)session.getAttribute("UserVO")).getUserid();
  shObj.logUserStatus(uid, "DF_ERROR", "EP:"+ errCode);
  
  
//  String backurl = "circlogin.jsp";
  String backurl = "login.jsp";
  if (shObj.getPageDisplayAttributesByMenuId(errReturnPageId) != null) {
    String backurl_sys = shObj.getPageDisplayAttributesByMenuId(errReturnPageId)[1] ;
    if (backurl_sys != null) 
      backurl = backurl_sys ;
    if (errReturnPageName==null || errReturnPageName.trim().equals(""))
      errReturnPageName = shObj.getPageDisplayAttributesByMenuId(errReturnPageId)[0] ;;      
  }

//  backurl = shObj.rootJSPURI + backurl ;
  // this assume call from servlet!
  backurl = "../" + backurl ;
  
  
%>

<html>

<jsp:include page="inc_common_html_head.jsp" flush="false">
  <jsp:param name="titlePrefix" value="Error" />
</jsp:include>

<body>


<BR><BR>

<CENTER>

<H1>Error</H1>

<BR><BR>

<H2><%=errMsg%> (<%=errCode%>)</H2>

<BR><BR>

<% if (isClosewindow) { %>
<A href="" onclick="window.close();">Click here to close this window.</A>
<%} else {%>
Please go back <a href="<%=backurl%>"><%=errReturnPageName%></a> and try again.
<%}%>
<BR><BR>

</CENTER>

</Body>

</html>


