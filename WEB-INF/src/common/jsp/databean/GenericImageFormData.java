/*
  $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/databean/GenericImageFormData.java,v $
  $Author: scmp $
  $Date: 2008/04/28 02:41:15 $
  $Revision: 1.1.1.1 $
*/
package common.jsp.databean;

import java.util.Hashtable;
import java.util.Vector;

public abstract class GenericImageFormData extends GenericSearchFormData 
{
  // Search result is kept as a Vector.
  private Hashtable ht ;
  
  public GenericImageFormData(int maxFields) 
  {
    super(maxFields);
    ht = null ;
  }

/**
 * set image & type into hashtable of vector
 * @param Image Id
 * @param Image byte array
 * @param Image type
 */
  public void setImage(String imageId, byte[] img, String type) {
    if (ht == null) { ht = new Hashtable();  }
    Vector vec = new Vector();
    vec.addElement(img);
    vec.addElement(type);
    ht.put(imageId,vec);
  }

/**
 * get image by id
 * @param image id
 * @return image byte array
 */
  public byte[] getImage(String imageId){
      Vector vec = (Vector)ht.get(imageId);
      return (byte[])vec.get(0); 
    }

/**
 * get image type by Id
 * @param image id
 * @return image type string
 */
   public String getImageType(String imageId){
      Vector vec = (Vector)ht.get(imageId);
      return (String)vec.get(1); 
    }
    
}