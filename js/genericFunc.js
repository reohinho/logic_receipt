function openWin(newURL, sWidth, sHeight, targetwindow) {
  if (sWidth == null || sWidth=='') 
//    sWidth = 740;
    sWidth = 900;
  if (sHeight == null || sHeight == '') 
//   sHeight = 480;
    sHeight = 600;
  newWin = window.open(newURL,targetwindow,'toolbar=yes,location=no,scrollbars=yes,resizable=yes,width='+sWidth+',height='+sHeight);
  return newWin ;
	
}

function closeWin() {
  if (confirm("Are you sure to close that window?")) {
    window.close();
  }
}

function remove_obj_closeWin(thisForm, thisFormField, inValue) {
    if (confirm("Are you sure to close that window?")) {
        set_value_submit(thisForm, thisFormField, inValue);
        window.close();
    }
}

function refreshParentWin() {
	if ((window.opener != null) && (! window.opener.closed)){
		window.opener.reload();
  	}
}
function submitToParentForm(formName){
	if (window.opener!= null && !window.opener.closed){
   if(window.opener.document.forms[formName]!=null){
    window.opener.document.forms[formName].submit();
   }else{
    //alert('parentObj missing');
   }
  }
}
function makeNumeric(o){
   o.value = o.value.replace(/([^0-9.])/g,"");
}


// This function is used to init a year list for the pull-down menu when this page load
// It will generate year list from current year - 100 to current year when called 
// and current year - 100 will be selected as a default option.


function InitYearList(thisYear, savedYear)
{
    var yearoption;
    var yearnum
    var yearrange=100;
    
    recentdate = new Date();
    recentdateYr = recentdate.getUTCFullYear();
    
    yearnum=recentdateYr - yearrange;
    var savedYearIdx = 0 ;

    for (yearoption=0;yearoption<yearrange;yearoption++)
    {
        yearnum++;
        thisYear.options[yearoption] = new Option(String(yearnum), String(yearnum));
        if (String(yearnum) == savedYear)
          savedYearIdx = yearoption ;
    }

    thisYear.length = yearoption;
    thisYear.options.length = yearoption;
    thisYear.selectedIndex = savedYearIdx;
}

// This function is used to provide an appropriate day list for the pull-down menu
// Day 1 for each month will be selected as a default option when html page first loaded

function FillDateList(thisDay, thisMonth, thisYear)
{
    var leapyear;
    var datecounter=0;
    var dateselected=0;
    var num=thisMonth.selectedIndex;
    
    dateselected=thisDay.selectedIndex;

    // check the selected year is leap year or not
    if ( (parseInt(thisYear.value) % 4 == 0 && parseInt(thisYear.value) % 100 != 0 ) 
          || parseInt(thisYear.value) % 400 == 0 ) 
    {
        leapyear = true;
    } else {
        leapyear = false;
    }
	
    // Check if the selected month is April, June, September, November or not
    if(num == 3 || num == 5 || num == 8 || num == 10){
        num=30;
    } else { // else check if it is Febuary or not
        if(num == 1) {
	        // check leap year
	        if(leapyear) num=29;
	        if(!leapyear) num=28;
        } else {
	        num= 31;
        }
    }

    if (dateselected < 0) dateselected=0;
    if (dateselected > num-1) dateselected=num-1;
	
    for (datecounter=0;datecounter<num;datecounter++)
    {
        thisDay.options[datecounter] = new Option(String(datecounter+1), String(datecounter+1));
        
    }
    
    thisDay.length = datecounter;
    thisDay.options.length = datecounter;
    thisDay.selectedIndex = dateselected;
}


function checkDateAllowEmpty(thisDateField) {
  if (thisDateField.value != '')
    if (!checkinputdate(thisDateField))
      thisDateField.focus() ;
}

// this function is used to validate the input date and make sure the input date are in dd-mm-yy format.
function checkinputdate(thisDateField) 
{
  // add by charlie, 21-09-2003. trim the value before validation
  thisDateField.value = trim(thisDateField.value);
  // end add by charlie
	var day = thisDateField.value.charAt(0).concat(thisDateField.value.charAt(1));
	var month = thisDateField.value.charAt(3).concat(thisDateField.value.charAt(4));
	var year = thisDateField.value.charAt(6).concat(thisDateField.value.charAt(7));

	// check input date is null or not
	//if (thisDateField.value.length==0) {
		//alert("Please provide the date");
		//return false;
	//}

  // skip validation if no value is input
	if (thisDateField.value.length!=0){
    // check input length 
    if (trim(thisDateField.value).length != 8) {
      alert("Input Date Format should be dd-mm-yy, ex: 01-04-01 for 1 April 2001");
      thisDateField.focus();      
      return false;
    } 

    // validate year input
    if (isNaN(year) || year < 0 || thisDateField.value.charAt(6)=="-") {
      alert("Year should between 00 to 99");
      thisDateField.focus();
      return false;
    }
	
    // validate month input
    if (isNaN(month) || month <=0 || month > 12) {
      alert("Month should between 01 to 12");
      thisDateField.focus();
      return false;
    }

    // validate day input
    if (isNaN(day) || day <= 0 || day > 31) {
      alert("Day range should between 01 to 31");
      thisDateField.focus();
      return false;
    }

    // validate max day input allow according to the input month for (April, June, September, November)
    if ((month==4 || month==6 || month==9 || month==11) && day > 30) {
      alert("Day range should between 01 to 30");
      thisDateField.focus();      
      return false;
    }
	
    // validate max day input allow for February according to input year (leap year)
    if (month==2) {
      if ( (parseInt(year) % 4 == 0 && parseInt(year) % 100 != 0 ) 
                || parseInt(year) % 400 == 0 ){
            if (day > 29) {
              alert("Day range should between 0 to 29");
              thisDateField.focus();              
              return false;
            }
          } else {
          if (day > 28) {
            alert("Day range should between 0 to 28");
            thisDateField.focus();
            return false;
          }
        }
    }
	
    // validate is it a proper seperator between day and month, also between month and year
    if (thisDateField.value.charAt(2)!="-" || thisDateField.value.charAt(5)!="-") {
      alert("Invalid input for date, use - as a seperator");
      thisDateField.focus();
      return false;
    }



  }
	
	// if input date is ok return true
	return true;
}

/*----------------------------------------------------------------------------------------*/
/* function : disableInput                                                                */
/* desc     : Disable or enable the elements depends on the check box                     */
/* param    : 1. formName - the name of the form                                          */
/*            2. checkBoxName - the name of the check box                                 */
/*            3. elementsName - the list of the element names                             */
/*----------------------------------------------------------------------------------------*/
function disableInput (formName, checkBoxName, elementsName) {
//alert("Generic");
	this.form =document.forms[formName];
	this.checkBox = this.form.elements[checkBoxName];
	var arrElementName = elementsName.split(',');
	var isDisable = true;
	if (this.checkBox.checked == false) {
		isDisable = false;
	}
	for (i=0; i<arrElementName.length;i++) {
		element = this.form.elements[arrElementName[i]];
		element.disabled = isDisable;
	}
}

/*----------------------------------------------------------------------------------------*/
/* function : disableRadioInput                                                                */
/* desc     : Disable or enable the elements depends on the radio box                     */
/* param    : 1. formName - the name of the form                                          */
/*            2. checkBoxName - the name of the check box                                 */
/*            3. disabledElementsName - the list of the element names                             */
/*            4. enabledElementsName - the list of the element names						*/
/*            5. value - the value of radio group											*/
/*----------------------------------------------------------------------------------------*/
function disableRadioInput (formName, checkBoxName, disabledElementsName, enabledElementsName, value) {
	this.form =document.forms[formName];
	this.checkBox = this.form.elements[checkBoxName];
	var disElementName = disabledElementsName.split(',');
	var enElementName = enabledElementsName.split(',');

	for (i=0; i<disElementName.length;i++) {
		element = this.form.elements[disElementName[i]];
		element.disabled = true;
	}
	for (i=0; i<enElementName.length;i++) {
		element = this.form.elements[enElementName[i]];
		element.disabled = false;
	}
    checkBox.value = value;

}

/*----------------------------------------------------------------------------------------*/
/* function : activateInput                                                                */
/* desc     : Disable or enable the elements depends on the check box                     */
/* param    : 1. formName - the name of the form                                          */
/*            2. checkBoxName - the name of the check box                                 */
/*            3. elementsName - the list of the element names                             */
/*            4. avtivateFlag - 0 for disable elements and 1 for enable elements             */
/*----------------------------------------------------------------------------------------*/
function activateInput (formName, checkBoxName, elementsName,activateFlag) {
	this.form =document.forms[formName];
	this.checkBox = this.form.elements[checkBoxName];
	var arrElementName = elementsName.split(',');
	var isEnable = activateFlag;
    if ( activateFlag == 1) {
	    if (this.checkBox.checked == true) {
               for (i=0; i<arrElementName.length;i++) {
		       element = this.form.elements[arrElementName[i]];
		       element.disabled = !activateFlag;
	           }
         } else {      
               for (i=0; i<arrElementName.length;i++) {
		       element = this.form.elements[arrElementName[i]];
		       element.disabled = activateFlag;         
               }
         }      
    }else if (activateFlag == 0){
	    if (this.checkBox.checked == true) {
               for (i=0; i<arrElementName.length;i++) {
		       element = this.form.elements[arrElementName[i]];
		       element.disabled = !activateFlag;
	           }
         } else {      
               for (i=0; i<arrElementName.length;i++) {
		       element = this.form.elements[arrElementName[i]];
		       element.disabled = activateFlag;         
               }
         }      
      }
}

/*----------------------------------------------------------------------------------------*/
/* function : change_pullDownList                                                         */
/* desc     : Change the options of second level poll down menu                           */
/*            when the first level menu changed                                           */
/* param    : 1. selectedIdx - the selected index of the first level menu                 */
/*            2. childListObj - the second level pull down menu object                    */
/*            3. childListArray - the array of the child list options                     */
/*               format : ((id1, text1), (id2, text2))                                    */
/*----------------------------------------------------------------------------------------*/
function change_pullDownList(selectedIdx, childListObj, childListArray ) {
  // Empty the child list
  for (i = childListObj.options.length; i >= 0; i--) {
    childListObj.options[i] = null; 
  }

  
  if (childListArray != null) {
    j = 0
    // add new items
    for (i = 0; i < childListArray.length; i++) {
      childListObj.options[j] = new Option();
      if (childListArray[i][0] != null) {
        childListObj.options[j].value = childListArray[i][0]; 
        if (childListArray[i][1] != null) {
          childListObj.options[j].text = childListArray[i][1];
        } else {
    	    childListObj.options[j].text = childListArray[i][0];
        }
      }
      j++;
    }

    // select first item (prompt) for sub list
    childListObj.options[0].selected = true;
  }
}

function set_cmd(thisForm, cmdValue) {
  if (cmdValue != null && cmdValue != '') 
    thisForm.cmd.value = cmdValue;
  thisForm.submit();
}

//
// prompt for user confirmation before submitting the form
//
function set_cmd_with_confirm_prompt(formname, cmd) {
    var action ;
    if (cmd == "h") {
      action = "hold" ; 
    }
    else { 
        if (cmd == "r") { 
            action = "release" ; 
        }
        else
        {
            return ;
        }
    }
    if(confirm("Are you sure you want to "+action+" this order? "))
    {
      formname.cmd.value=cmd;
      formname.submit() ;
    }
}


function set_value_submit(thisForm, thisFormField, inValue) {
    thisFormField.value=inValue;
    thisForm.submit();
}

function change_formAction(thisForm,url) {
  if (url != null && url!='') 
    thisForm.action = url;
  return true;
}

function set_pulldown_desc(thisDesc, hiddenDescObj) {
  if (thisDesc == null || thisDesc == '' ) {
    hiddenDescObj.value = '';
  } else {
    hiddenDescObj.value = thisDesc;
  }
}

function set_pulldown_desc_check_value(thisValue, thisDesc, hiddenDescObj) {
  if (thisValue != '') {
     if (thisDesc == null || thisDesc == '' ) {
         hiddenDescObj.value = '';
     } else {
        hiddenDescObj.value = thisDesc;
     }
  }else {
         hiddenDescObj.value = '';
  }   
}

// input assisting route - auto fill zero for accountno
// if first char is '0' and total length is less than 12 characters, pad '0' on the left until the field length is 12
function fillAccountNo(thisField) 
{
  if (thisField.value.length < 12) {
     if (thisField.value.charAt(0) == "0") {
       var temp = "00000000000" + thisField.value ;
       thisField.value = temp.substring(temp.length-12);
     }
  }
  return true ;
}

//*******************************************************
// ******** add by charlie for validating input  ********
// ******************************************************
function isEmpty(s)
{   return ((s == null) || (s.length == 0))
}
function isDigit (c)
{   return ((c >= "0") && (c <= "9"))
}
function isInteger (s)
{   var i;
    if (isEmpty(s)) 
       if (isInteger.arguments.length == 1) return false;
       else return (isInteger.arguments[1] == true);

    // Search through string's characters one by one
    // until we find a non-numeric character.
    // When we do, return false; if we don't, return true.

    for (i = 0; i < s.length; i++)
    {   
        // Check that current character is number.
        var c = s.charAt(i);

        if (!isDigit(c)) return false;
    }

    // All characters are numbers.
    return true;
}

// set the button value to "Processing...", avoid user presses the button more than once
function set_processing_cmd(thisForm, thisButton, cmdValue) {
  var processing_string  = "Processing..." ;
  if (thisButton.value == processing_string)
  {
    alert("Processing...") ;
    return false;
  }
  thisButton.value = processing_string ;
  if (cmdValue != null && cmdValue != '') 
    thisForm.cmd.value = cmdValue;
  thisForm.submit();
  setAllInputStatus(thisForm,true,'');
}    

// disable buttons to avoid user from pressing more than once
function disable_save_and_back(thisForm, saveButton, backButton) {
  var processing_string  = "Processing..." ;
  saveButton.value = processing_string ;
  backButton.disabled = true;
  saveButton.disabled = true;
}    

function trim(inputString) {
   // Removes leading and trailing spaces from the passed string. Also removes
   // consecutive spaces and replaces it with one space. If something besides
   // a string is passed in (null, custom object, etc.) then return the input.
   if (typeof inputString != "string") { return inputString; }
   var retValue = inputString;
   var ch = retValue.substring(0, 1);
   while (ch == " ") { // Check for spaces at the beginning of the string
      retValue = retValue.substring(1, retValue.length);
      ch = retValue.substring(0, 1);
   }
   ch = retValue.substring(retValue.length-1, retValue.length);
   while (ch == " ") { // Check for spaces at the end of the string
      retValue = retValue.substring(0, retValue.length-1);
      ch = retValue.substring(retValue.length-1, retValue.length);
   }
   return retValue; // Return the trimmed string back to the user
} // Ends the "trim" function
//********************************
//********************************


function nonAlphanumericToAsciiCode(input) {
 tempInput = "";
 for(i = 0; i < input.length; i++ ) {
    tempChar = input.charAt(i);
    result = tempChar.match(/[^\w]/);
    if (result != null) {
        tempInput += input.charCodeAt(i);
    }else {
        tempInput += tempChar;
    }
  }
 return tempInput;
}

function digitOnly(e){
  var iekey=event.keyCode;
  var realkey=String.fromCharCode(event.keyCode);
  //alert(event.keyCode);
  if (event.keyCode>=48 && event.keyCode <= 57){
    return true;  
  }else{return false;}
}
//if the inputted value is not a integer then set it to empty
function isIntegerVal(field){
	if (!isInteger(field.value)){
		field.value="";
	}
}


//
String.prototype.trim = function()
{
    return this.replace(/(^\s*)|(\s*$)/g, "");
}
function isFormEmpty(f){
	var ret = false;
	for (var i =0; i< f.length; i++){
		var tagName = f[i].tagName;
    	if (typeof tagName == "string"){
	 	 tagName = tagName.toUpperCase();
	     if (tagName == "INPUT" && f[i].type.toUpperCase()=="TEXT" &&  f[i].value!="" && f[i].value.trim().length>0) {
	     	//alert(f[i].name);
	     	ret = true;
	     }else if (tagName == "SELECT" && f[i].selectedIndex >0) {
	     	//alert(f[i].selectedIndex);
	        ret = true;
	     }else if (tagName == "RADIO" && f[i].checked==true) {
	        ret = true;
	     }
	 }
	 
  }
  if (!ret){
  	alert("Please enter some criteria!");
  }
  return ret;
}

function checkAll(isChecked,elmName,fnCallBack){
	var elms = document.getElementsByName(elmName);
	
	for (i=0;i < elms.length;i++){
		if (elms[i].disabled==false){
			elms[i].checked=isChecked;
		}
	}
	if (typeof(fnCallBack)!="undefined"){
		fnCallBack(elms);
	}
}

function changeSytle(elm, styName){
	elm.className = styName;
}

	var now = new Date();
	var maxDate = (now.getMonth()+1)+"/"+now.getDate()+"/"+now.getFullYear();
	now.setTime(now.getTime()-220*24*3600*1000);
	var minDate = (now.getMonth()+1)+"/"+now.getDate()+"/"+now.getFullYear();
	
	if (typeof(YAHOO)!='undefined'){
		YAHOO.namespace("example.calendar");
		YAHOO.example.calendar.showUp = function(actEleName, dateElmName){
			YAHOO.example.calendar.cal3 = new YAHOO.widget.Calendar("cal3","cal1Container", { title:"Choose a date:"
																						, close:true
																						//,mindate:minDate
																						//,maxdate:maxDate
																						});
			YAHOO.util.Event.addListener(document, "click", YAHOO.example.calendar.cal3.hide(), YAHOO.example.calendar.cal3, true);
			YAHOO.util.Dom.setStyle(YAHOO.example.calendar.cal3.oDomContainer, 'left', event.clientX);
			YAHOO.util.Dom.setStyle(YAHOO.example.calendar.cal3.oDomContainer, 'top', event.clientY);
			YAHOO.example.calendar.cal3.render();
			YAHOO.example.calendar.cal3.show();
			
			function setDateField(type,args,obj){ 
				var dates = args[0]; 
				var date = dates[0]; 
				var year = date[0], month = date[1], day = date[2]; 
				var day = (day.toString().length==1) ? "0"+ day : day;
		    	var month = (month.toString().length==1) ? "0"+ month:month;
				var txtDate1 = document.getElementById(dateElmName);
				txtDate1.value = day + "-" + month  +  "-" + (year+"").substring(2);
				obj.hide(); 
				
			}
			YAHOO.example.calendar.cal3.selectEvent.subscribe(setDateField, YAHOO.example.calendar.cal3, true);
		}
	}
	
	function setToDecimalPlace(elm, dp){
		try{
			var elmV = parseFloat(elm.value);
			elm.value=elmV.toFixed(dp);
		}catch (err){
		
		}
	}
	
	function cleanSelect(elmName){
		var sel = document.getElementById(elmName);
		//alert (sel);
		if (sel!=null && sel!='undefined'){
			//alert (opts);
			opts = sel.options;
			//alert (opts);
			for ( i = opts.length-1 ; opts!='undefined' && i>=1 ; i--){
				sel.remove(i);
			}
		}
	}
	
	String.prototype.cLength = function()
	{
    	var c=0;
    	for (i = 0;i<this.length; i++){
    		if (this.charCodeAt(i)<=128){
    			c++;
    		}else{
    			c+=2;
    		}
    	}
    	return c; 
	}
	
	function setAllInputStatus(f,s,exElmName){
		var elms = f.elements;
		for (i=0; i < elms.length; i++){
			if (elms[i].name!='undefined' && elms[i].nodeName.toUpperCase()=="INPUT" && elms[i].name!=exElmName){
				elms[i].disabled=s;
			}
		}
	}
	
	function validateFieldLength(field, len){
		var str = field.value;
		if (str.cLength() > len ){
			alert("This field excess input length ("+len+") limit");
			field.focus();
		}
	}