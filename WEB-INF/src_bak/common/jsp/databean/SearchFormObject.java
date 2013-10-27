package common.jsp.databean;

import java.sql.Timestamp;
import com.scmp.circ.utility.CircUtilities;

public abstract class SearchFormObject extends GenericStringData
{
  private boolean errorFlag ;
  private String labels[] ;

  public Timestamp getDataAsTimestamp(int field)
  {
    String s = getData(field) ;
    return CircUtilities.getTimestampFromString(s) ;
  }

  public String getLabel(int field)
  {
    return labels[field-1] ;
  }
  
  public boolean isValid() 
  {
    return (errorFlag == false ) ;
  }

  public boolean dataIsEmpty(int field)
  {
    String s = getData(field-1) ;
    if (s == null || s.equals("")) 
    {
      return true ;
    }
    else
    {
      return false ;
    }
  }
  
  public abstract void resetFormData() ;
 
  public SearchFormObject(int maxFields) 
  {
    super(maxFields);
    labels = new String[maxFields] ;
  }

  public void setLabel(int field, String label)
  {
    labels[field-1] = label ;
  }
}