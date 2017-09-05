package io.micro.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;

import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

/**
 * @author act262@gmail.com
 */
public class GenerateQRDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JTextArea textArea1;

    public GenerateQRDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
        render();
//        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void render() {
        String contents = textArea1.getText();
        JFrame f = new JFrame();
        QRImageCanvas mc = new QRImageCanvas();
        f.add(mc);
        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 200, 200);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            mc.setImage(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mc.repaint();
        f.setSize(300, 300);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args) {
        GenerateQRDialog dialog = new GenerateQRDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
