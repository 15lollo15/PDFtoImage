package model;

import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import utils.Intervals;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class PDFConverter{

    private static PDFConverter pdfConverter = null;

    private PDFConverter() {
    }

    public static PDFConverter getInstance() {
        if (pdfConverter == null)
            pdfConverter = new PDFConverter();
        return pdfConverter;
    }

    public void imagesToPdf(File[] images, File pdfFile) throws IOException {
        pdfFile.createNewFile();
        PDDocument pdfDocument = new PDDocument();
        int i = 0;
        for (File imgF : images) {
            addAImagePage(imgF, pdfDocument);

        }
        pdfDocument.save(pdfFile);
    }

    public void addAImagePage(File imgF, PDDocument pdfDocument) throws IOException {
        BufferedImage img = ImageIO.read(imgF);
        PDPage page = new PDPage(new PDRectangle(img.getWidth(), img.getHeight()));
        pdfDocument.addPage(page);

        PDImageXObject pdImage = PDImageXObject.createFromFile(imgF.getPath(), pdfDocument);
        PDPageContentStream contentStream = new PDPageContentStream(pdfDocument, page);

        contentStream.drawImage(pdImage, 0, 0);
        contentStream.close();
    }

    public void pdfToImages(File pdfFile, File imgsDir, float dpi, String imgFormat, Intervals pagesIntervals) throws IOException {
        PDDocument pdfDocument = Loader.loadPDF(pdfFile);
        PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);

        for (int i = 0; i < pdfDocument.getNumberOfPages(); i++) {
            if(pagesIntervals.isInTheIntervals(i))
                renderAPage(pdfDocument, imgsDir, dpi, imgFormat, i);
        }
    }

    public void renderAPage(PDDocument pdfDocument, File imgsDir, float dpi, String imgFormat, int pageIndex) throws IOException {
        PDFRenderer pdfRenderer = new PDFRenderer(pdfDocument);
        BufferedImage bufferedImage = pdfRenderer.renderImageWithDPI(pageIndex, dpi);
        File imgFile = new File(imgsDir.getAbsoluteFile().getAbsolutePath() + "/" + pageIndex + "." + imgFormat.toLowerCase());
        imgFile.createNewFile();
        ImageIO.write(bufferedImage, imgFormat, imgFile);
    }

}
