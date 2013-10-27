/**
* $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/SelectionBox.java,v $
* $Author: scmp $
* $Date: 2008/04/28 02:41:14 $
* $Revision: 1.1.1.1 $
*/
package common.jsp;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

/**
* $Source: c:/buffer2cvs/CirculationVCs/WsViewControl/ProjectViewControl/src/com/scmp/circ/common/jsp/SelectionBox.java,v $
* $Author: scmp $
* $Date: 2008/04/28 02:41:14 $
* $Revision: 1.1.1.1 $
*/
public class

SelectionBox
{
  private String selectionName[];
  private Vector optionKey[];
  private Hashtable defaultValue;
  private Hashtable optionValue[];
  private String listName[];
  private int totalLevel;

  /** Constructor that takes a String array of names, an array of the option key vectors, 
   *  the array of the default selection of the selection
   *  and a Hashtable containing the data.
   */
  public SelectionBox(String newSelectionName[], Vector newOptionKey[], Hashtable newDefaultValue, Hashtable ht[])
  {
    selectionName = newSelectionName;
    optionKey = newOptionKey;
    defaultValue = newDefaultValue;
    for (int i = 0; i < ht.length; i++) 
    {
      Enumeration keys = ht[i].keys();
      while (keys.hasMoreElements()) 
      {
        String key = (String) keys.nextElement();
        //System.out.println ("current level: " + i);
        //System.out.println ("current key: " + key);
        if ( ht[i].get(key) instanceof Vector) {
        ht[i].put(key, (String) ((Vector) ht[i].remove (key)).firstElement());}
        //System.out.println ("current value: " + ht[i].get(key));
        //else {break;}
        //if (
        //String tmpString = (String)((Vector) ht[i].get((String)key)).firstElement();
        //ht[i].remove (key);
        //ht[i].put (key, tmpString);
      }
    }
    optionValue = ht;
    totalLevel = newSelectionName.length;
    listName = new String[totalLevel];
    for (int i = 0; i < totalLevel; i++) 
    {
      listName[i] = "list" + selectionName[i];
    }
    //System.out.println ("Inside constructor:");
    //System.out.println (optionValue[2].toString());
  }

  // Constructor for the special case of single level selection box. Created for convenience.
  public SelectionBox(String newSelectionName, Vector newOptionKey, String newDefaultValue, Hashtable ht)
  {
    selectionName = new String[1];
    selectionName[0] = newSelectionName;
    optionKey = new Vector[1];
    optionKey[0] = newOptionKey;
    defaultValue = new Hashtable();
    defaultValue.put ("ROOT", newDefaultValue);
    optionValue = new Hashtable[1];
    optionValue[0] = ht;
    totalLevel = 1;
    listName = new String[totalLevel];
    listName[0] = "list" + selectionName[0];
  } 

  /** Constructor for the special case of single level selection box with string arrays as parameters.
   */
  public SelectionBox(String newSelectionName, String newOptionKey[], String newDefaultValue, String newOptionValue[])
  {
    selectionName = new String[1];
    selectionName[0] = newSelectionName;
    optionKey = new Vector[1];
    optionKey[0] = new Vector();
    optionValue = new Hashtable[1];
    optionValue[0] = new Hashtable();
    for (int i=0; i < newOptionKey.length; i++) 
    {
      optionKey[0].addElement(newOptionKey[i]);
      optionValue[0].put (newOptionKey[i], newOptionValue[i]);
    }
    defaultValue = new Hashtable();
    defaultValue.put ("ROOT", newDefaultValue);
    totalLevel = 1;
  }

  public SelectionBox(String newSelectionName, String newOptionKey[], String newOptionValue[])
  {
    selectionName = new String[1];
    selectionName[0] = newSelectionName;
    optionKey = new Vector[1];
    optionKey[0] = new Vector();
    optionValue = new Hashtable[1];
    optionValue[0] = new Hashtable();
    optionKey[0].addElement("null");
    optionValue[0].put ("null","");
    for (int i=0; i < newOptionKey.length; i++) 
    {
      optionKey[0].addElement(newOptionKey[i]);
      optionValue[0].put (newOptionKey[i], newOptionValue[i]);
    }
    defaultValue = new Hashtable();
    defaultValue.put ("ROOT", "null");
    totalLevel = 1;
  }

  /** Constructor for the special case of single level selection box.
   * Take a string delimited by '.' as parameter
   * Take the first element in the array as default value
   */

  public SelectionBox(String newSelectionName, String newOptionKey)
  {
    selectionName = new String[1];
    selectionName[0] = newSelectionName;
    optionKey = new Vector[1];
    optionKey[0] = new Vector();
    optionValue = new Hashtable[1];
    optionValue[0] = new Hashtable();
    StringTokenizer st = new StringTokenizer(newOptionKey, ".");
    String tmpKey = "";
    String tmpValue = "";
    while (st.hasMoreTokens()) 
    {
      tmpKey = st.nextToken();
      if (tmpKey.equals("null")) {
      tmpValue = ""; } else {tmpValue=tmpKey;}
      optionKey[0].addElement(tmpKey);
      optionValue[0].put (tmpKey, tmpValue);
    }
    defaultValue = new Hashtable();
    defaultValue.put ("ROOT", optionKey[0].firstElement());
    totalLevel = 1;
  }
  
  /** Returns the number of levels of the selection box */
  public int getLevel() 
  {
    return totalLevel;
  }

  public String toHtml ()
  {
    String scriptOutput = "";
    String htmlOutput = "";
    for (int i = 0; i < totalLevel; i++)
    {
      //System.out.println ("Generating HTML level" + i);
      htmlOutput += genHtml(i);
      //System.out.println ("Generating script level " + i);
      scriptOutput += genScript(i);
    }
    return (htmlOutput + scriptOutput);
  }

  public String genScript(int level)
  {
    if (level == 0) 
    {
      return "";
    }
    
    String output = "<script language='JavaScript'>\n";
    Enumeration lastLevelKeys = optionKey[level-1].elements();
    Enumeration keys = optionKey[level].elements();
    String curKey, lastLevelCurKey, dynamicListNameParam;

    // Genearting DynamicOptionList
    
    if (level == 1) 
    {
      dynamicListNameParam = "'" + selectionName[1] + "', '" + selectionName[0] + "'";
    } else  
    {
      dynamicListNameParam = "'" + selectionName[2] + "', '" + selectionName[0] + "', '" + selectionName[1] + "'";
    }

    output += "var " + listName[level] + "= new DynamicOptionList(" + dynamicListNameParam + ");\n";

    // Generating necessary options for the pull down menu
    
    while (lastLevelKeys.hasMoreElements() )
    {
      lastLevelCurKey = (String) lastLevelKeys.nextElement();
      if (level == 1) 
      {
        // this make default = null
        //output += listName[level] + ".addOptions('" + lastLevelCurKey + "','-------'," + "'null');\n";
        // end of making default = null
        output += listName[level] + ".addOptions('" + lastLevelCurKey + "'";
        //output += listName[level] + ".setDefaultOption('" + curKey + "', '" + (String) defaultValue.get(curKey) + "');\n";
      } else
      {
        StringTokenizer st = new StringTokenizer (lastLevelCurKey, ".");
        String lastLastLevelCurKey = st.nextToken();
        //output += listName[level] + ".addOptions('" + lastLastLevelCurKey + "|" + lastLevelCurKey + "','-------'," + "'null');\n";
        output += listName[level] + ".addOptions('" + lastLastLevelCurKey + "|" + lastLevelCurKey + "'";
      }
      while  ( keys.hasMoreElements()  )
      {
        curKey = (String) keys.nextElement();
        //System.out.println (curKey);
        if (curKey.startsWith(lastLevelCurKey))
        {
//          output += ", '" + curKey + "', '" + (String) optionValue[level].get(curKey) + "'";
          output += ", '" + (String) optionValue[level].get(curKey) + "', '" + curKey + "'";
        }
      }
      keys = optionKey[level].elements();
      output += ");\n";
    }

    // Generating default options for the pull down menu

    keys = optionKey[level-1].elements();
    while (keys.hasMoreElements()) 
    {
      curKey = (String) keys.nextElement();
      //if ( defaultValue.get(curKey) != null ) 
      //{
      //  output += listName[level] + ".setDefaultOption('" + curKey + "', '" + (String) defaultValue.get(curKey) + "');\n";
     // }
    }

    output += listName[level] + ".init(document.forms[1]);\n";
    output += "</script>\n";
    return output;
  }

  public String genHtml(int level)
  {
    //level--;
    String output = "<select name=" + selectionName[level] + getOnChange(level) + ">\n";
    // 
    if (level > 2)
    {
      return ("Error: Does not support more than 3 levels.\n");
    }
    
    if (level == 2 || level == 1) 
    {
            return (output +  "<option value='null'></option></select>\n");
    }
    
    Enumeration keys = optionKey[level].elements();
    String curKey;
    String isSelected;
    //output += "<option value='null'></option>\n";
    
    while  ( keys.hasMoreElements()  )
    {
      curKey = (String) keys.nextElement();
      if (defaultValue != null && curKey.equals((String)defaultValue.get("ROOT"))) {
        isSelected = " selected";
      } else {
        isSelected = "";      
      }
      //System.out.println ("genHTML: current key is " + optionValue[level].get(curKey) );
      output = output + "<option value='" + curKey + "'" + isSelected + ">" + (String) optionValue[level].get(curKey) + "</option>\n";
    }
      
    output = output + "</select>\n";
    return output;
  }

  public String getOnChange (int level)
  {
    String output = " onChange='";
    for (int i=level+1; i < totalLevel; i++) 
    {
      output += listName[i] + ".populate();";
    }
    output +="'";
    return output;
  }

   public static void main(String[] args)
  {
  
    Vector[] va = new Vector[3];
    //v3[0] = new Vector();
    //v3[1] = new Vector();
    Vector v1 = new Vector();
    v1.addElement ("hk");
    v1.addElement ("tw");
    v1.addElement ("us");
    Vector v2 = new Vector();
    v2.addElement ("hk.hk");
    v2.addElement ("hk.kl");
    v2.addElement ("tw.t1");
    v2.addElement ("us.u1");
    Vector v3 = new Vector();
    v3.addElement ("hk.hk.cw");
    v3.addElement ("hk.hk.e");
    v3.addElement ("hk.kl.k1");
    v3.addElement ("us.u1.ny");
    va[0] = v1;
    va[1] = v2;
    va[2] = v3;
    Hashtable ht1 = new Hashtable();
    ht1.put ("hk", "Hong Kong");
    ht1.put ("tw", "Taiwan");
    ht1.put ("us", "USA");
    String[] c = new String[3];
    c[0] = "Country";
    c[1] = "Province";
    c[2] = "City";
    Hashtable defSelect = new Hashtable();
//    defSelect.put ("hk", "hk");
    defSelect.put ("ROOT", "hk");
    defSelect.put ("hk", "hk.k1");
    defSelect.put ("tw", "tw.t1");
    Hashtable ht2 = new Hashtable();
    ht2.put ("hk.hk", "Hong Kong");
    ht2.put ("hk.kl", "Kowloon");
    ht2.put ("tw.t1", "Tai 1");
    ht2.put ("us.u1", "US 1");
    Hashtable ht3 = new Hashtable();
    ht3.put ("hk.hk.cw", "Central & Western");
    ht3.put ("hk.hk.e", "Eastern");
    ht3.put ("hk.kl.k1", "Kowloon City");
    ht3.put ("us.u1.ny", "New York");
    Hashtable[] ht = new Hashtable[3];
    ht[0] = ht1;
    ht[1] = ht2;
    ht[2] = ht3;

    SelectionBox SelectMain = new SelectionBox(c, va, defSelect, ht);
    //System.out.println (SelectMain.genHtml(1));
    //System.out.println (SelectMain.genHtml(2));
    //System.out.println (SelectMain.toHtml());
    //System.out.println (SelectMain.getLevel());
    
    /*Vector v1 = new Vector();
    v1.addElement ("sp");
    v1.addElement ("sp2");
    Vector[] v = new Vector[1];
    v[0] = v1;
    Hashtable h1 = new Hashtable();
    h1.put ("sp", "Sales Person 1");
    h1.put ("sp2", "Sales Person 2");
    Hashtable[] ht = new Hashtable[1];
    ht[0] = h1;
    Hashtable def = new Hashtable();
    def.put ("ROOT", "sp");
    String[] s = new String[1];
    s[0] = "responsible";
    SelectionBox sb = new SelectionBox(s, v, def, ht);
    System.out.println (sb.toHtml());
    String teststr[] = {"1", "2", "3"}; 
    sb = new SelectionBox("crap",teststr, teststr[0], teststr);
    sb = new SelectionBox ("crap", teststr, teststr);
    System.out.println (sb.toHtml());
    System.out.println (new SelectionBox("crap", "yes.no").toHtml());
    System.out.println (SelectMain.genScript(1));*/
 
  }
}

