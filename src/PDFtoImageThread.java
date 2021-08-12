import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class PDFtoImageThread extends SwingWorker<Integer, Integer> {

    private PDFConverter pdfConverter;
    private File pdfFile;
    private File imgsDir;
    private float dpi;
    private String format;
    private Intervals intervals;
    private JProgressBar pb;
    private Controller c;

    public PDFtoImageThread(File pdfFile, File imgsDir, float dpi, String format, Intervals pagesInterval, JProgressBar pb, Controller c){
        this.pdfFile = pdfFile;
        this.imgsDir = imgsDir;
        this.dpi = dpi;
        this.format = format;
        if(pagesInterval != null)
            this.intervals = pagesInterval;
        else
            this.intervals = null;
        this.pb = pb;
        this.c = c;
        //TO-DO: Check format
    }

    @Override
    protected Integer doInBackground() throws Exception {
        pdfConverter = PDFConverter.getInstance();
        try {
            PDDocument pdfDoc = Loader.loadPDF(pdfFile);
            int numPages = pdfDoc.getNumberOfPages();
            int tot = numPages;
            if(intervals != null)
                tot = intervals.getIntervalsTotalSize();
            int donePages = 0;
            for(int i = 0; i<numPages; i++) {
                if(intervals == null || intervals.isInTheIntervals(i)) {
                    pdfConverter.renderAPage(pdfDoc, imgsDir, dpi, format, i);
                    donePages++;
                }
                publish((donePages*100)/tot);
            }
            publish(100);
        } catch (IOException e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }

    @Override
    protected void process(List<Integer> chunks) {
        int status = chunks.get(chunks.size()-1);
        pb.setValue(status);
    }

    @Override
    protected void done() {
        super.done();
        pb.setValue(100);
        c.blockAll(false);
        try {
            Desktop.getDesktop().open(new File(String.valueOf(imgsDir)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
