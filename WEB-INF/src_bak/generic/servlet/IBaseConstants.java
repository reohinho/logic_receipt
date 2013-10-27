package generic.servlet;

/**
 * Some constants (or names) use in the application
 */
public interface IBaseConstants 
{

  /** 
   * String constant for command name from input. 
   * Use when read form parameters in servet and hidden field in JSP form.
   **/
  public static final String kCommand = "cmd" ;
  
  /** 
   * String constant for object reference index from input.
   * Use when read form parameters in servet and hidden field in JSP form.
   */
  public static final String kObjectIndex = "objIdx" ;

  /** 
   * Full menu id for a JSP page
   * Use when read form parameters in servet and hidden field in JSP form.
   */
  public static final String kPageId = "pgId" ;

  /**
   * Common session reference on JSP (HttpServletSequest) for GenericWebFormData object 
   */
  public static final String kGenericWebFormData = "kgwfd" ;
  

  // Some common use page ids
  public static final String kPageIdLogin = "DF_LOGIN" ;
  public static final String kPageIdUserDefault = "DF_TOP" ;
  public static final String kPageIdError = "DF_ERROR" ;


  // Session reference for user metadata
  public static final String kUserMetaDataSessionId = "METADATA" ;
  

}