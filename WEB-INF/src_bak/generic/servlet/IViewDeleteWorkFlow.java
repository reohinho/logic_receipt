package generic.servlet;

/**
 * This interface contains the workflow for view and delete commands
 */
public interface IViewDeleteWorkFlow 
{

    // command must exist in order to drive the workflow for "View" process
    // command list :
    //
    // 'v' -> Usually this command associates the link in detail listing page and hook up a key for displaying detail
    //         --> possible requirements: <key value>
    //
    // 'vx' -> "Close" button in view detail page which discard any action and close window from view page 
    //         --> possible requirements: <objIdx>
    //
    // 'vd' -> "Delete" button in view page jump to delete confirmation page
    //         --> possible requirements: <objIdx>
    //
    // 'dc' -> "Delete" confirmed
    //         --> possible requirements: <objIdx>
    //
    // 'db' -> "Delete" jump back to view page -> 'v'
    //         --> possible requirements: <objIdx>
    //
    // Parameter constant to be referenced in JSP
    // 'vp' : View object on HttpRequest reference id in JSP 
    // 'dp' : Delete (usually same as view object) object on HttpRequest reference id in JSP 
    


    public static final String kView = "v";
    public static final String kViewDiscard = "vx";
//    public static final String kViewJSPParams = "vp";

    public static final String kDelete = "vd"; // or named View to Delete
    public static final String kDeleteConfirm = "dc";
    public static final String kDeleteBack = "db";
//    public static final String kDeleteJSPParams = "dp";


    // 's' -> Search action on Search Criteria page
    //
    // Parameter constant to be referenced in JSP
    // 'scp' : Object on HttpRequest reference id in search criteria page JSP 
    // 'srp' : Object on HttpRequest reference id in search result page JSP 
    public static final String kSearch = "s" ;
//    public static final String kSearchCriteriaJSPParams = "scp" ;
//    public static final String kSearchResultJSPParams = "srp" ;


}