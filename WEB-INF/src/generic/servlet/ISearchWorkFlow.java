package generic.servlet;

public interface ISearchWorkFlow 
{
    // command must exist in order to drive the workflow for "Create" process
    // command list :
    // 's' -> Search action on Search Criteria page
    // 'si' -> init search (maybe with some default parameters in HttpServletRequest)
    // 'sb' -> Back action on Search Result page
    public static final String kSearch = "s" ;
    public static final String kSearchInit = "si" ;
    public static final String kSearchBack = "sb" ;
    public static final String kCurrentSearch="cs";//tracy 05-08-08
    public static final String kDeliverySearch="ds";//tracy 05-08-08
}