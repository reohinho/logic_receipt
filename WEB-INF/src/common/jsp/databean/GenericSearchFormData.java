/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/GenericSearchFormData.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package common.jsp.databean;

import java.util.Vector ;

public abstract class GenericSearchFormData extends GenericWebFormData 
{
  // Search result is kept as a Vector.
  private Vector resultVector ;
  
  public GenericSearchFormData(int maxFields) 
  {
    super(maxFields);
    resultVector = null ;
  }

  public void setResultVector(Vector v) 
  {
    resultVector = v;
  }

  public Vector getResultVector()
  {
    return resultVector ;
  }
}