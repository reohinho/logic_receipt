package generic.servlet;

public interface ICreateWorkFlow 
{
    // command must exist in order to drive the workflow for "Create" process
    // command list :
    // 'c' -> "Add" button from everywhere (jump to "Create" main page)
    //         --> possible requirements: none (or objIdx)
    //
    // 'cs' -> "Add" save => save button in "Add" main page
    //         --> possible requirements: <objIdx>, and other input field(s)
    //
    // 'cx' -> "Add" discard => close button in "Add" main page
    //         --> possible requirements: <objIdx>
    //
    // 'cc' -> "Add" confirm => ok button in "Add" confirmation page
    //         --> possible requirements: <objIdx>
    //
    // 'cb' -> "Create" jump back to input page => back button in "Add" confirmation page
    //         --> possible requirements: <objIdx>
    //
    // 'cp' -> object reference to be set in HttpRequest for JSP to reference
    // 
    // 'rl1' -> reload the first page

    public static final String kAdd = "c";
    public static final String kAddSave = "cs"; 
    public static final String kAddDiscard = "cx"; // abort current add operation
    public static final String kAddConfirm = "cc"; // proceed to confirm page
    public static final String kAddBack = "cb"; // back to page 1
    public static final String kAddPage2 = "c2" ; // go to page 2
    public static final String kAddBack2 = "cb2" ; // go to page 2
    public static final String kReload1 = "rl1"; // reload page 1

    // 14-09-05 added by Wisley Lui for develop HRP individual payment
    public static final String kAddPage3 = "c3" ; // go to page 3
    public static final String kAddBack3 = "cb3" ; // back to page 3
    // end added by Wisley Lui
//    public static final String kAddJSPParams = "cp";


}