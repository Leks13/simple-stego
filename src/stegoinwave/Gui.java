/*
 GPL v3
 */
package stegoinwave;

import java.io.*;
import javax.swing.JFileChooser;

public class Gui extends javax.swing.JFrame {

    static void run() {
        new Gui().setVisible(true);
    }

    public Gui() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDialog1 = new javax.swing.JDialog();
        jScrollPane1 = new javax.swing.JScrollPane();
        textField = new javax.swing.JTextPane();
        cryptButton = new javax.swing.JButton();
        decryptButton = new javax.swing.JButton();
        filePathField = new javax.swing.JTextField();
        statusField = new javax.swing.JLabel();
        fileFoundButton = new javax.swing.JButton();

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jScrollPane1.setViewportView(textField);

        cryptButton.setText("Crypt");
        cryptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                CryptMouse(evt);
            }
        });

        decryptButton.setText("Decrypt");
        decryptButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                DecryptMouse(evt);
            }
        });

        filePathField.setText("1.wav");

        statusField.setText("...");

        fileFoundButton.setText("...");
        fileFoundButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                fileFound(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cryptButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(decryptButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(filePathField, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(fileFoundButton, javax.swing.GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)))
                .addContainerGap())
            .addComponent(statusField, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cryptButton)
                    .addComponent(decryptButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filePathField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fileFoundButton, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, 15, Short.MAX_VALUE)
                .addComponent(statusField))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CryptMouse(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CryptMouse
        System.out.println("Crypt");
        String path = filePathField.getText();
        File in = new File(path);
        byte[] bytes = new byte[(int) in.length()];
        try (InputStream is = new FileInputStream(in)) {
            is.read(bytes);
        } catch (IOException ex) {
            statusField.setText("File read error");
        }
        String message = textField.getText();
        byte[] m = message.getBytes();
        int j = 80;
        int j1 = 0;
        int len = m.length;
        bytes[60] = (byte) m.length;
        for (int i = 0; i < bytes.length; i++) {
            if (i == j && len != 0) {
                bytes[i] = m[j1];
                j += 20;
                j1 += 1;
                len--;
            }
        }

        try (OutputStream os = new FileOutputStream("crypt.wav")) {
            os.write(bytes);
            os.close();
        } catch (IOException ex) {
            statusField.setText("File write error");
        }

        System.out.println("Crypted!");
        statusField.setText("File write");
    }//GEN-LAST:event_CryptMouse

    private void DecryptMouse(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_DecryptMouse
        System.out.println("Decrypt");
        String path = filePathField.getText();
        File in = new File(path);
        byte[] bytes = new byte[(int) in.length()];
        try (InputStream is = new FileInputStream(in)) {
            is.read(bytes);
            is.close();
        } catch (IOException ex) {
            statusField.setText("File read error");
        }
        String decrypt = "Decrypt - ";
        int len = (int) bytes[60];
        int j = 80;
        int j1 = 0;
        byte[] m = new byte[(int) in.length()];
        for (int i = 0; i < m.length; i++) {
            if (i == j && len != 0) {
                m[j1] = bytes[j];
                j += 20;
                j1 += 1;
                len--;
                if (len == 0) {
                    break;
                }
            }
        }
        String mes = null;
        try {
            mes = new String(m, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            statusField.setText("Error on decrypt");
        }
        decrypt += mes;
        textField.setText(decrypt);
        repaint();
        System.out.println("Decrypted!");
        statusField.setText("File decryted");
    }//GEN-LAST:event_DecryptMouse

    private void fileFound(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileFound
        JFileChooser fileopen = new JFileChooser();
        int ret = fileopen.showDialog(null, "Open File");
        File file = fileopen.getSelectedFile();
        filePathField.setText(file.getName());
    }//GEN-LAST:event_fileFound

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cryptButton;
    private javax.swing.JButton decryptButton;
    private javax.swing.JButton fileFoundButton;
    private javax.swing.JTextField filePathField;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel statusField;
    private javax.swing.JTextPane textField;
    // End of variables declaration//GEN-END:variables
}