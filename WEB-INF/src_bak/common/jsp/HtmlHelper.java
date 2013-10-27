package common.jsp;

import common.jsp.HTMLGenerator;

import java.lang.StringBuffer;

import java.util.Hashtable;
import java.util.Vector;

public class HtmlHelper 
{

  public HtmlHelper()
  {
    isCategoryArrayLoaded = false ;
  }

  public String javascriptForRowHilite()
  {
    StringBuffer sb = new StringBuffer() ;
    sb.append("\nfunction sbar(st){\n st.style.backgroundColor = '#f2dcdb'; \n} \n") ;
    sb.append("function cbar(st)\n{\n st.style.backgroundColor = ''; \n} \n") ;
    return sb.toString() ;
  }

  boolean isCategoryArrayLoaded ;

  private String arrCategoryId[] ;
  private String arrCategoryDesc[];
  private String arrSubcategoryId[][] ;
  private String arrSubcategoryDesc[][] ;   

  public Vector getAllElement () 
  {
    Vector v = new Vector () ;
    v.addElement((String[]) arrCategoryId);
    v.addElement((String[]) arrCategoryDesc);
    v.addElement((String[][]) arrSubcategoryId);
    v.addElement((String[][]) arrSubcategoryDesc);
    return v ;
  }

  

  
  
  // generate javascript required on HTML to handle dynamic menu for subcategory 
  public String javascriptForSubCategory()
  {
    if (isCategoryArrayLoaded == false)
    {
      return ("") ;
    }
       
    StringBuffer sb = new StringBuffer() ;
    sb.append("subMenu = new Array(\n") ;
    sb.append("new Array(\n") ;
    sb.append(buildJS2DArray("", "---- Please first select category ----")) ;
    sb.append("\n)");

    // loop for each category
    for (int i=0; i<arrCategoryId.length;i++)
    {
      sb.append(",\n"); 
      sb.append("new Array(\n") ;
      sb.append(buildJS2DArray("", "---- Please Select ----")) ;
      // loop for subcategory in each category
      for (int j=0;j<arrSubcategoryId[i].length;j++)
      {
        sb.append(",\n") ;
        sb.append(buildJS2DArray(arrSubcategoryId[i][j], arrSubcategoryDesc[i][j])) ;
      }
      sb.append("\n)") ;
    }   
    sb.append("\n)") ;
    
    return sb.toString();
  }
/*
 * build javascript for 2D array
 * - only used by javascriptForSubCategory()
 */
  private String buildJS2DArray(String v1, String v2)
  {
    return new String("new Array('" + v1 + "', '" + v2 + "')") ;
  }

  public String generateCategoryPulldownList(String varDefaultCategory, String varFormName, String varCategoryFieldName, String varAdditionalEvent, String firstChoice)
  {
    if (isCategoryArrayLoaded == false)
    {
      return ("") ;
    }
    return HTMLGenerator.generatePulldownList(arrCategoryId,arrCategoryDesc,varCategoryFieldName,varDefaultCategory,varAdditionalEvent,firstChoice);
  }


  public String generateCategoryAndSubcategoryPulldownList(String varDefaultCategory, String varFormName, String varCategoryFieldName, String varSubcategoryFieldName)
  {
    StringBuffer sb = new StringBuffer() ;

    sb.append(generateCategoryPulldownList(varDefaultCategory,varFormName,varCategoryFieldName, 
                                           " onChange='change_pullDownList(this.selectedIndex, document." + varFormName + ". " + varSubcategoryFieldName + ", subMenu[this.selectedIndex]);'", 
                                           "<OPTION VALUE=\"\">---- Please Select ----\n"
                                           ));
    sb.append("&nbsp;") ;
    sb.append("<select name='" + varSubcategoryFieldName + "'><OPTION VALUE=''>---- Please first select category ----</select>") ;

    return sb.toString() ;
  }
  
  public static void main(String[] args)
  {
    HtmlHelper htmlHelper = new HtmlHelper();
    System.out.println(htmlHelper.javascriptForRowHilite()) ;
  }

  public static boolean isNumberic(String str) {
    boolean ret = true;
    try {
	Double.parseDouble(str);
    }
    catch (NumberFormatException e)
    {
   	ret = false;
    }
    return ret;
  }
}