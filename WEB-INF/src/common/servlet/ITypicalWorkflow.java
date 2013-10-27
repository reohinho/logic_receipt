/**
 * $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/servlet/ITypicalWorkflow.java,v $
 * $Author: scmp $
 * $Date: 2008/04/28 02:41:15 $
 * $Revision: 1.1.1.1 $
 */

package common.servlet;

public interface ITypicalWorkflow 
{
    // command must exist in order to drive the workflow in this servlet 
    // command list :
    // 's' -> "Search" main screen
    //         --> possible requirements: search criteria fields
    //
    // 'v' -> "View" from result list page
    //         --> possible requirements: <key value>
    //
    // 'e' -> "Edit" button click (from view page)
    //         --> possible requirements: <key value>
    //
    // 'd' -> "Delete" button click (from view page)
    //         --> possible requirements: <key value>
    //
    // 'vx' -> Discard any action and close window from view page
    //         --> possible requirements: <key value>
    //
    // 'c' -> "Create" button from everywhere (jump to "Create" main page)
    //         --> possible requirements: none
    //
    // 'cs' -> "Create" save
    //         --> possible requirements: <objIdx>, and other input field(s)
    //
    // 'cx' -> "Create" discard
    //         --> possible requirements: <objIdx>
    //
    // 'cc' -> "Create" confirm
    //         --> possible requirements: <objIdx>, and other input field(s)
    //
    // 'cb' -> "Create" jump back to input page
    //         --> possible requirements: <objIdx>
    //
    // 'es' -> "Edit" save
    //         --> possible requirements: <key value>, and other input field(s)
    //
    // 'ex' -> "Edit" discard
    //         --> possible requirements: <key value>
    //
    // 'ec' -> "Edit" confirmed
    //         --> possible requirements: <key value> (not update), and other input field(s)
    //
    // 'eb' -> "Edit" jump back to input page
    //         --> possible requirements: <key value>
    //
    // 'dc' -> "Delete" confirmed
    //         --> possible requirements: <key value>
    //
    // 'db' -> "Delete" jump back to view page -> 'v'
    //         --> possible requirements: <key value>
    //
    // 'dx' -> "Delete" discard
    //         --> possible requirements: <key value>
    //

    public static final String kSearch = "s";
    public static final String kView = "v";
    public static final String kViewDiscard = "vx";
    public static final String kEdit = "ve"; // or named View to Edit
    public static final String kDelete = "vd"; // or named View to Delete
    public static final String kAdd = "c";
    public static final String kAddSave = "cs";
    public static final String kAddDiscard = "cx";
    public static final String kAddConfirm = "cc";
    public static final String kAddBack = "cb";
    public static final String kEditSave = "es";
    public static final String kEditDiscard = "ex";
    public static final String kEditConfirm = "ec";
    public static final String kEditBack = "eb";
    public static final String kDeleteConfirm = "dc";
    public static final String kDeleteBack = "db";
    public static final String kDeleteDiscard = "dx";
    public static final String kSearchCriteria = "sc";
    public static final String kSearchResult = "sr";
    public static final String kAddJSPParams = "cp";
    public static final String kEditJSPParams = "ep";
    public static final String kViewJSPParams = "vp";
    public static final String kViewDetailBack = "vb";
    public static final String kConfirmJSPParams = "mp";
    public static final String kFinalJSPParams = "fp";
    public static final String kSrchForm = "srchform";

}