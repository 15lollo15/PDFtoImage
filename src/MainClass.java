import com.formdev.flatlaf.FlatLightLaf;
import gui.Controller;
import model.PDFConverter;

import javax.swing.*;
import java.io.IOException;

public class MainClass {

    public static float DPI = 300;
    public static String IMAGE_FORMAT = "PNG";

    public static void main(String[] args) throws IOException {
        FlatLightLaf.setup();

        Controller controller = new Controller();
        controller.showWindow();



        /*
        utils.Interval i1 = utils.Interval.getIntervalFromString("1-3");
        utils.Interval i2 = utils.Interval.getIntervalFromString("4-6");
        utils.Intervals intervals = utils.Intervals.getIntervalsFromString("1-3,4-6");
        intervals.addInterval(i1);
        intervals.addInterval(i2);


        System.out.println(intervals.isInTheIntervals(7));
        */
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
