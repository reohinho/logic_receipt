package generic.servlet;

public interface IEditWorkFlow 
{
    // command must exist in order to drive the workflow for "Edit" process
    // command list :
    //
    // 'e' -> "Edit" init (maybe with some default parameter
    //
    // 'es' -> "Edit" save
    //         --> possible requirements: <objIdx>, and other input field(s)
    //
    // 'ex' -> "Edit" discard
    //         --> possible requirements: <objIdx>
    //
    // 'ec' -> "Edit" confirmed
    //         --> possible requirements: <objIdx> (not update), and other input field(s)
    //
    // 'eb' -> "Edit" jump back to input page
    //         --> possible requirements: <objIdx>
    //
    // 
    // 'rl' -> "Reload" the page
    // 'exr' -> "Discard" with redirection the page

    public static final String kEdit = "e";
    public static final String kEditSave = "es";
    public static final String kEditDiscard = "ex";
    public static final String kEditConfirm = "ec";
    public static final String kEditBack = "eb";
    public static final String kReload = "rl";
    // add by charlie 06-12-2003
    public static final String kEditBack2 = "eb2";
    public static final String kEditSave2 = "es2";
    public static final String kEditDiscardRedirct = "exr";
    // end add by charlie
//    public static final String kEditJSPParams = "ep";


}