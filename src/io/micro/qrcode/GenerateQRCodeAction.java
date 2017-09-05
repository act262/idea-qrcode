package io.micro.qrcode;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * @author act262@gmail.com
 */
public class GenerateQRCodeAction extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        GenerateQRDialog dialog = new GenerateQRDialog();
        dialog.pack();
        dialog.setVisible(true);
    }
}
