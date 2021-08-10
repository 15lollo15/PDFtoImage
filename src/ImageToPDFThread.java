import org.apache.pdfbox.pdmodel.PDDocument;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class ImageToPDFThread extends SwingWorker<Integer, Integer> {

    private File[] images;
    private File pdfFile;
    private Controller controller;
    private JProgressBar pb;

    public ImageToPDFThread(File[] images, File pdfFile, Controller controller, JProgressBar pb) {
        this.images = images;
        this.pdfFile = pdfFile;
        this.controller = controller;
        this.pb = pb;
    }

    @Override
    protected Integer doInBackground() throws Exception {
        pdfFile.createNewFile();
        PDFConverter converter = PDFConverter.getInstance();
        PDDocument pdfDocument = new PDDocument();

        int i = 0;
        for (File imgF : images) {
            converter.addAImagePage(imgF, pdfDocument);
            publish((i*100)/images.length);
            i++;
        }
        pdfDocument.save(pdfFile);
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
        controller.blockAll(false);
        try {
            Desktop.getDesktop().open(new File(String.valueOf(pdfFile)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
