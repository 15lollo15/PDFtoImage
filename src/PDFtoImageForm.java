import javax.swing.*;

public class PDFtoImageForm extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane1;
    private JTextField PDFField;
    private JButton buttonFCPDF;
    private JTextField ImagesField;
    private JButton buttonImagesPath;
    private JButton convertButton;
    private JSpinner spinner1;
    private JComboBox comboBox1;
    private JTextField imagedPathfield;
    private JButton fcImages;
    private JTextField pdfDestination;
    private JButton fcPDFdir;
    private JButton convertButton1;
    private JProgressBar progressBar1;

    public PDFtoImageForm(String title){
        super(title);
        this.setContentPane(mainPanel);
    }

    public JProgressBar getProgressBar1() {
        return progressBar1;
    }

    public JTextField getPDFField() {
        return PDFField;
    }

    public JButton getButtonFCPDF() {
        return buttonFCPDF;
    }

    public JTextField getImagesField() {
        return ImagesField;
    }

    public JButton getButtonImagesPath() {
        return buttonImagesPath;
    }

    public JButton getConvertButton() {
        return convertButton;
    }

    public JSpinner getSpinner1() {
        return spinner1;
    }

    public JComboBox getComboBox1() {
        return comboBox1;
    }

    public JTextField getImagedPathfield() {
        return imagedPathfield;
    }

    public JButton getFcImages() {
        return fcImages;
    }

    public JTextField getPdfDestination() {
        return pdfDestination;
    }

    public JButton getFcPDFdir() {
        return fcPDFdir;
    }

    public JButton getConvertButton1() {
        return convertButton1;
    }
}
