package gui;

import model.ImageToPDFThread;
import model.PDFtoImageThread;
import org.apache.pdfbox.debugger.ui.ExtensionFileFilter;
import utils.Intervals;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Controller {
    private static final String WINDOW_TITLE = "Converter";
    private static final int WINDOW_WIDTH = 500;
    private static final int WINDOW_HEIGHT= 320;

    private static final int DEFAULT_DPI = 300;
    private static final String DEFAULT_IMAGE_FORMAT = "png";

    private PDFConverterForm window;

    public Controller() {
        window = new PDFConverterForm();

        setupWindow();
        addListeners();
    }

    public Controller me() {
        return this;
    }

    private void setupWindow() {
        window.setTitle(WINDOW_TITLE);
        window.setSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));
        window.setResizable(false);
        window.setDefaultCloseOperation(PDFConverterForm.EXIT_ON_CLOSE);
        window.setFocusable(true);
        window.setLocationRelativeTo(null);

        window.getDestImagesField().setEnabled(false);
        window.getDestPDFField().setEnabled(false);
        window.getSrcImagesField().setEnabled(false);
        window.getSrcPDFField().setEnabled(false);

        window.getDPISpinner().setValue(DEFAULT_DPI);
        String[] compatibleFormats = ImageIO.getReaderFileSuffixes();
        for(String format : compatibleFormats)
            window.getImageFormatComboBox().addItem(format);
        window.getImageFormatComboBox().setSelectedItem(DEFAULT_IMAGE_FORMAT);

        window.getSelectPagesIntervalCheckBox().setSelected(false);
        window.getPageIntervalField().setEnabled(false);
    }

    public void showWindow() {
        window.setVisible(true);
    }

    private void addListeners() {
        window.getBrowseSrcPDFButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
                int ris = fileChooser.showOpenDialog(window);
                if(ris == JFileChooser.APPROVE_OPTION) {
                    window.getSrcPDFField().setText(fileChooser.getSelectedFile().getPath());
                }
            }
        });

        window.getBrowseDestImagesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int rval = fc.showOpenDialog(window);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    window.getDestImagesField().setText(fc.getSelectedFile().getPath());
                }
            }
        });

        window.getSelectPagesIntervalCheckBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                window.getPageIntervalField().setEnabled(window.getSelectPagesIntervalCheckBox().isSelected());
            }
        });

        window.getBrowseSrcImagesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setMultiSelectionEnabled(true);
                //fc.addChoosableFileFilter(new FileNameExtensionFilter("Immagini (jpg, png)", "jpg", "png"));
                fc.addChoosableFileFilter(new ExtensionFileFilter(ImageIO.getReaderFileSuffixes(), "Immagini"));
                int rval = fc.showOpenDialog(window);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    String str = "";
                    for(File f : fc.getSelectedFiles())
                        str += f.getPath()+";";

                    window.getSrcImagesField().setText(str);
                }
            }
        });

        window.getBrowseDestPDFButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("pdf", "pdf"));
                fc.setDialogTitle("Specify a pdf to save");
                int rval = fc.showOpenDialog(window);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    String str = fc.getSelectedFile().getPath();
                    if(!str.contains(".pdf"))
                        str += ".pdf";
                    window.getDestPDFField().setText(str);
                }
            }
        });

        window.getConvertPDFtoImagesButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pdfPath = window.getSrcPDFField().getText();
                String imgDirPath = window.getDestImagesField().getText();

                if(pdfPath == "" || imgDirPath == "")
                    return;

                float dpi = (Integer)window.getDPISpinner().getValue();
                String ext = window.getImageFormatComboBox().getSelectedItem().toString();

                String intervalString = null;
                if(window.getSelectPagesIntervalCheckBox().isSelected()) {
                    intervalString = window.getPageIntervalField().getText();
                    if(intervalString.equals(""))
                        return;
                }
                Intervals intervals = null;
                if(intervalString != null)
                    intervals = Intervals.getIntervalsFromString(intervalString);
                PDFtoImageThread thread = new PDFtoImageThread(new File(pdfPath),
                        new File(imgDirPath),
                        dpi,
                        ext,
                        intervals,
                        window.getPDFtoImagesProgressBar(),
                        me());
                thread.execute();
                blockAll(true);
            }
        });

        window.getConvertImagesToPDFButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imagesPaths = window.getSrcImagesField().getText();
                String pdfPath = window.getDestPDFField().getText();

                if(imagesPaths == "" || pdfPath == "")
                    return;

                String[] paths = imagesPaths.split(";");
                File[] imgs = new File[paths.length];
                for(int i = 0; i<paths.length; i++)
                    imgs[i] = new File(paths[i]);

                ImageToPDFThread thread = new ImageToPDFThread(imgs,
                        new File(pdfPath),
                        me(),
                        window.getImagesToPDFProgressBar());
                thread.execute();
                blockAll(true);
            }
        });
    }

    public void blockAll(boolean b) {
        window.getSrcPDFField().setEnabled(!b);
        window.getDestImagesField().setEnabled(!b);
        window.getBrowseSrcPDFButton().setEnabled(!b);
        window.getBrowseDestImagesButton().setEnabled(!b);
        window.getImageFormatComboBox().setEnabled(!b);
        window.getDPISpinner().setEnabled(!b);
        window.getSelectPagesIntervalCheckBox().setEnabled(!b);
        window.getPageIntervalField().setEnabled(!b);
        if(!b && !window.getSelectPagesIntervalCheckBox().isSelected()){
            window.getPageIntervalField().setEnabled(false);
        }
        window.getSrcImagesField().setEnabled(!b);
        window.getDestPDFField().setEnabled(!b);
        window.getTabbedPane().setEnabled(!b);
        window.getConvertImagesToPDFButton().setEnabled(!b);
        window.getConvertPDFtoImagesButton().setEnabled(!b);
    }
}
