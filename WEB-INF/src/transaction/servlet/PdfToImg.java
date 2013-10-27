package transaction.servlet;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
 
import java.awt.image.BufferedImage;
import java.io.File;
 
import javax.imageio.ImageIO;
 
import multivalent.Behavior;
import multivalent.Context;
import multivalent.Document;
import multivalent.Node;
import multivalent.std.adaptor.pdf.PDF;
 
public class PdfToImg {
 
    public File inputFile;
 
    public PdfToImg(String input, String output) {
        inputFile = new File(input);
        this.convert(output);
    }
 
    public static void main(String args[]) {
    	PdfToImg conv = new PdfToImg("myDoc.pdf","myDoc");   // Change that line for your test
        
    }
    
    public void convert(String filename) {
         
        File outfile = new File(filename);
 
        try {
 
            PDF pdf = (PDF) Behavior.getInstance("AdobePDF", "AdobePDF", null, null, null);
 
            pdf.setInput(inputFile);
 
            Document doc = new Document("doc", null, null);
            pdf.parse(doc);
            doc.clear();
 
            doc.putAttr(Document.ATTR_PAGE, Integer.toString(1));
            pdf.parse(doc);
 
            Node top = doc.childAt(0);
            
            doc.formatBeforeAfter(400, 400, null);
            int w = top.bbox.width;
            int h = top.bbox.height;
            BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = img.createGraphics();
            g.setClip(0, 0, w, h);
 
            g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      
            Context cx = doc.getStyleSheet().getContext(g, null);
            top.paintBeforeAfter(g.getClipBounds(), cx);
 
            ImageIO.write(img, "png", outfile);
           
            
            /* we close everything ... */
            doc.removeAllChildren();
            cx.reset();
            g.dispose();
 
            pdf.getReader().close();
            outfile = null;
            inputFile = null;
            doc = null;
       
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

