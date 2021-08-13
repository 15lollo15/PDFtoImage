package gui;

import javax.swing.*;

public class PDFConverterForm extends JFrame{
    private JPanel mainPanel;
    private JTabbedPane tabbedPane;
    private JTextField srcPDFField;
    private JButton browseSrcPDFButton;
    private JTextField destImagesField;
    private JButton browseDestImagesButton;
    private JComboBox imageFormatComboBox;
    private JSpinner DPISpinner;
    private JTextField pageIntervalField;
    private JButton convertPDFtoImagesButton;
    private JProgressBar PDFtoImagesProgressBar;
    private JTextField srcImagesField;
    private JButton browseSrcImagesButton;
    private JTextField destPDFField;
    private JButton browseDestPDFButton;
    private JButton convertImagesToPDFButton;
    private JProgressBar imagesToPDFProgressBar;
    private JCheckBox selectPagesIntervalCheckBox;

    public PDFConverterForm(){
        super();
        this.setContentPane(mainPanel);
    }

    public JTextField getSrcPDFField() {
        return srcPDFField;
    }

    public JButton getBrowseSrcPDFButton() {
        return browseSrcPDFButton;
    }

    public JTextField getDestImagesField() {
        return destImagesField;
    }

    public JButton getBrowseDestImagesButton() {
        return browseDestImagesButton;
    }

    public JComboBox getImageFormatComboBox() {
        return imageFormatComboBox;
    }

    public JSpinner getDPISpinner() {
        return DPISpinner;
    }

    public JTextField getPageIntervalField() {
        return pageIntervalField;
    }

    public JButton getConvertPDFtoImagesButton() {
        return convertPDFtoImagesButton;
    }

    public JProgressBar getPDFtoImagesProgressBar() {
        return PDFtoImagesProgressBar;
    }

    public JTextField getSrcImagesField() {
        return srcImagesField;
    }

    public JButton getBrowseSrcImagesButton() {
        return browseSrcImagesButton;
    }

    public JTextField getDestPDFField() {
        return destPDFField;
    }

    public JButton getBrowseDestPDFButton() {
        return browseDestPDFButton;
    }

    public JButton getConvertImagesToPDFButton() {
        return convertImagesToPDFButton;
    }

    public JProgressBar getImagesToPDFProgressBar() {
        return imagesToPDFProgressBar;
    }

    public JCheckBox getSelectPagesIntervalCheckBox() {
        return selectPagesIntervalCheckBox;
    }

    public JTabbedPane getTabbedPane() {
        return tabbedPane;
    }
}
