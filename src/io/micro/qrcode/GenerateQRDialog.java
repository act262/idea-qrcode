package io.micro.qrcode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.intellij.notification.Notification;
import com.intellij.notification.NotificationGroup;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.text.StringUtil;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

/**
 * 生成二维码的窗口
 *
 * @author act262@gmail.com
 */
public class GenerateQRDialog extends JDialog {
    private JPanel contentPane;
    private JEditorPane editorPane1;
    private JButton buttonOK;
    private JPanel imagePanel;
    private QRImageCanvas canvas;

    GenerateQRDialog() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        editorPane1.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if (e.isAltDown() && e.getKeyCode() == KeyEvent.VK_ENTER) {
                    render();
                }
            }
        });
        buttonOK.addActionListener(e -> onOK());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e ->
                onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        canvas = new QRImageCanvas();
        imagePanel.add(canvas);
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
        String contents = editorPane1.getText();
        if (StringUtil.isEmptyOrSpaces(contents)) {
            showNotify("没有内容");
            return;
        }

        try {
            BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, 100, 100);
            BufferedImage bufferedImage = MatrixToImageWriter.toBufferedImage(bitMatrix);
            canvas.setImage(bufferedImage);
            canvas.repaint();
        } catch (Exception e) {
            e.printStackTrace();
            showNotify("error");
        }
    }

    private void showNotify(String message) {
        ApplicationManager.getApplication().invokeLater(() -> {
            Notification notification =
                    NotificationGroup.balloonGroup("tip")
                            .createNotification("tip", message, NotificationType.INFORMATION, null);
            Notifications.Bus.notify(notification);
        });
    }

    public static void main(String[] args) {
        GenerateQRDialog dialog = new GenerateQRDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
