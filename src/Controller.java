import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class Controller {
    private PDFConverter pdfConverter;
    private PDFtoImageForm form;

    public Controller me() {
        return this;
    }

    public Controller(PDFConverter pdfConverter, PDFtoImageForm pdFtoImageForm) {
        this.pdfConverter = pdfConverter;
        this.form = pdFtoImageForm;

        setupListeners();
        form.getSpinner1().setValue(new Integer(50));
        form.getComboBox1().addItem("PNG");
        form.getComboBox1().addItem("JPG");
        form.getProgressBar1().setVisible(false);

        form.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        form.pack();
        form.setVisible(true);
    }

    private void blockAll(boolean b) {
        form.getConvertButton1().setEnabled(!b);
        form.getConvertButton().setEnabled(!b);
    }

    private void setupListeners() {
        form.getButtonFCPDF().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter(".pdf", "pdf"));
                int rval = fc.showOpenDialog(form);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    form.getPDFField().setText(fc.getSelectedFile().getPath());
                }
            }
        });

        form.getButtonImagesPath().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                int rval = fc.showOpenDialog(form);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    form.getImagesField().setText(fc.getSelectedFile().getPath());
                }
            }
        });

        form.getSpinner1().addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if((Integer)form.getSpinner1().getValue() < 50)
                    form.getSpinner1().setValue(50);
            }
        });

        form.getConvertButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String pdfPath = form.getPDFField().getText();
                String imgDirPath = form.getImagesField().getText();

                if(pdfPath == "" || imgDirPath == "")
                    return;

                float dpi = (Integer)form.getSpinner1().getValue();
                String ext = form.getComboBox1().getSelectedItem().toString();

                form.getProgressBar1().setVisible(true);
                PDFtoImageThread thread = new PDFtoImageThread(new File(pdfPath),new File(imgDirPath), dpi, ext, null,  form.getProgressBar1(), me());
                thread.execute();
                //pdfConverter.pdfToImages(new File(pdfPath),new File(pdfPath),dpi,ext, null);
                blockAll(true);
            }
        });



        form.getFcPDFdir().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("pdf", "pdf"));
                fc.setDialogTitle("Specify a pdf to save");
                int rval = fc.showOpenDialog(form);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    String str = fc.getSelectedFile().getPath();
                    if(!str.contains(".pdf"))
                        str += ".pdf";
                    form.getPdfDestination().setText(str);
                }
            }
        });

        form.getFcImages().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                fc.setAcceptAllFileFilterUsed(false);
                fc.setMultiSelectionEnabled(true);
                fc.addChoosableFileFilter(new FileNameExtensionFilter("Immagini (jpg, png)", "jpg", "png"));
                int rval = fc.showOpenDialog(form);
                if (rval == JFileChooser.APPROVE_OPTION) {
                    String str = "";
                    for(File f : fc.getSelectedFiles())
                        str += f.getPath()+";";

                    form.getImagedPathfield().setText(str);
                }
            }
        });


        form.getConvertButton1().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String imgsPaths = form.getImagedPathfield().getText();
                String pdfDestPath = form.getPdfDestination().getText();

                if (imgsPaths == "" || pdfDestPath == "")
                    return;

                String[] splitted = imgsPaths.split(";");
                File[] imgs = new File[splitted.length];

                for(int i = 0; i<splitted.length; i++)
                    imgs[i] = new File(splitted[i]);

                try {
                    PDFConverter.getInstance().imagesToPdf(imgs, new File(pdfDestPath));
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
    }


}
