package common.jsp;
import common.jsp.databean.GenericStringData;

public class HtmlTable 
{

  private String[] header;
  private String[][] body;
  private String attribute;
  private int numOfColumns;
  private int numOfRows;
  
  //public HtmlTable()
  //{
  //}

  public HtmlTable (String[] newHeader, String[][] newBody, String newAttribute)
  {
    header = newHeader;
    body = newBody;
    if (newAttribute != "") {
      attribute = newAttribute;
    } else {
      attribute = "border=0";
    }
    numOfColumns = header.length;
    numOfRows = body.length;
  }

  public HtmlTable (String[] newHeader, String[][] newBody)
  {
    header = newHeader;
    body = newBody;
    attribute = "border=0";
    numOfColumns = header.length;
    numOfRows = body.length;
  }

  public String printHtml () 
  {
    String output="<table " + attribute + ">\n";
    output += "<tr>";
    for (int j = 0; j < numOfColumns; j++) 
    {
      output += "<td>" + GenericStringData.escapeDataInHtml( header[j] ) + "</td>";
    }
    output += "</tr>\n";
    for (int i = 0; i < numOfRows; i++) 
    {
      output += "<tr>";
      for (int j = 0; j < numOfColumns; j++) 
      {
        output += "<td>" + GenericStringData.escapeDataInHtml( body[i][j] ) + "</td>";
      }
      output += "</tr>\n";
    }
    
    return output + "</table>\n";
  }
  
  public static void main(String[] args)
  {
    String testHeader[] = {"header1", "header2", "header3"};
    String testBody [][] = 
    {
      {"1", "2", "3"},
      {"4", "5", "6"},
      {"7", "8", "9"}
    };
    HtmlTable htmlTable = new HtmlTable(testHeader, testBody);
    System.out.println (htmlTable.printHtml());
  }
}