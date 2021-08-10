import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MainClass {

    public static float DPI = 300;
    public static String IMAGE_FORMAT = "PNG";

    public static void main(String[] args) throws IOException {
        setLF();
        Controller controller = new Controller(PDFConverter.getInstance(), new PDFtoImageForm("Converter"));

        /*
        if(args.length > 0){
            File tmp = new File(args[0]);
            if(ImageIO.read(tmp) != null) {
                System.out.println("Image");



            }else {
                if(args[0].split(".")[1].toUpperCase() == "PDF"){
                    System.out.println("is a PDF");
                }else {
                    if(args[0].charAt(0) == '-') {
                        System.out.println("is a command");
                    }
                }
            }
        }

         */
    }



    public static void setLF (){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
    }

}
