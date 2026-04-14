/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package project;

import backend.sessionlogin;
import database.dbconnect;
import java.sql.*;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import backend.ComboItem;
/**
 *
 * @author Rezan
 */
public class grade extends javax.swing.JFrame {
    
    Connection conn;
    Statement stmt;
    ResultSet rs;
    int selectedid;
    dbconnect koneksi;
    
    DefaultTableModel dtm;
    ResultSetMetaData rsmd;
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(grade.class.getName());

    /**
     * Creates new form grade
     */
    public grade() {
        if (!sessionlogin.islogin) {
            javax.swing.JOptionPane.showMessageDialog(this, "silahkan login dulu");
            new loginpage().setVisible(true);
            dispose();
            return;
        }
        getContentPane().removeAll();
        initComponents();
        
        koneksi = new dbconnect();
        conn = koneksi.getConnection();
        
        loadComboSiswa();
        loadComboMapel();

        tblnilai.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {

                int selectedRow = tblnilai.getSelectedRow();

                if (selectedRow != -1) {

                    int modelRow = tblnilai.convertRowIndexToModel(selectedRow);

                    selectedid = Integer.parseInt(
                            tblnilai.getModel().getValueAt(modelRow, 0).toString()
                    );

                    String nis = tblnilai.getModel().getValueAt(modelRow, 1).toString();
                    String nama = tblnilai.getModel().getValueAt(modelRow, 2).toString();
                    String mapel = tblnilai.getModel().getValueAt(modelRow, 3).toString();
                    String nilai = tblnilai.getModel().getValueAt(modelRow, 4).toString();

                    txtnilai.setText(nilai);
                    setSelectedSiswa(nis, nama);
                    setSelectedMapel(mapel);
                }
            }
        });
        tampiltabel();
    }
    
    private DefaultTableModel buattabelmodel (ResultSet rs) throws SQLException {
        dtm = new DefaultTableModel();
        rsmd = rs.getMetaData();
        
        int jmlkolom = rsmd.getColumnCount();
        
        String [] namaKolom = new String[jmlkolom];
        for (int i = 0; i < jmlkolom; i++) {
            namaKolom[i] = rsmd.getColumnName(i + 1);
            } 
        dtm.setColumnIdentifiers(namaKolom);
            
            while (rs.next()) {
                String[] users = new String[jmlkolom];
                for (int i = 0; i < jmlkolom; i++) {
                    users [i] = rs.getString(i + 1);
                }
                dtm.addRow(users);
        }
            return dtm;
    }
    
    private boolean tampiltabel() {
        String sql = "SELECT grade.id, student.NIS, student.Name, mapel.NAMA_MAPEL, grade.NILAI "
                + "FROM grade JOIN student ON grade.Student_Id = student.ID JOIN mapel ON grade.ID_MAPEL = mapel.ID";
          try {
              stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                      ResultSet.CONCUR_READ_ONLY);
              rs = stmt.executeQuery(sql);
              
              rs.last();
              if (rs.getRow() == 0){
                  JOptionPane.showMessageDialog(null, "data nilai tidak ada");
              } else {
                  rs.beforeFirst();
                  dtm = buattabelmodel(rs);
                  tblnilai.setModel(dtm);
                  tblnilai.setModel(dtm);
                  tblnilai.getColumnModel().getColumn(0).setMinWidth(0);
                  tblnilai.getColumnModel().getColumn(0).setMaxWidth(0);
                  tblnilai.getColumnModel().getColumn(0).setWidth(0);
                  
                  tblnilai.setAutoCreateRowSorter(true);
              }
              return true;
          }catch (SQLException ex){
              JOptionPane.showMessageDialog(null, "error: " + ex.getMessage());
          }
          return false;   
    }
        
    private void loadComboSiswa() {
        String sql = "SELECT ID, NIS, Name FROM student";

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);

            cbxnamasiswa.removeAllItems();
            cbxnamasiswa.addItem(new ComboItem(0, "Silahkan pilih siswa"));

            while (rs.next()) {
                int id = rs.getInt("ID");
                String nis = rs.getString("NIS");
                String name = rs.getString("Name");

                cbxnamasiswa.addItem(
                    new ComboItem(id, nis + " - " + name)
                );
            }
            cbxnamasiswa.setSelectedIndex(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void loadComboMapel() {
        String sql = "SELECT ID, NAMA_MAPEL FROM mapel";

        try {
            Statement st = conn.createStatement();
            rs = st.executeQuery(sql);

            cbxnamamapel.removeAllItems();
            cbxnamamapel.addItem(new ComboItem(0, "Silahkan pilih mapel"));
            
            while (rs.next()) {
                int id = rs.getInt("ID");
                String nama = rs.getString("NAMA_MAPEL");

                cbxnamamapel.addItem(
                    new ComboItem(id, nama)
                );
            }
            cbxnamamapel.setSelectedIndex(0);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }
    
    private void setSelectedSiswa(String nis, String nama) {

        for (int i = 0; i < cbxnamasiswa.getItemCount(); i++) {

            ComboItem item = (ComboItem) cbxnamasiswa.getItemAt(i);

            if (item.toString().equals(nis + " - " + nama)) {
                cbxnamasiswa.setSelectedIndex(i);
                break;
            }
        }
    }
    
    private void setSelectedMapel(String namaMapel) {

        for (int i = 0; i < cbxnamamapel.getItemCount(); i++) {

            ComboItem item = (ComboItem) cbxnamamapel.getItemAt(i);

            if (item.toString().equals(namaMapel)) {
                cbxnamamapel.setSelectedIndex(i);
                break;
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblnilai = new javax.swing.JTable();
        btnback = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtnilai = new javax.swing.JTextField();
        btndelete = new javax.swing.JButton();
        btnupdate = new javax.swing.JButton();
        btntambah = new javax.swing.JButton();
        cbxnamasiswa = new javax.swing.JComboBox();
        cbxnamamapel = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane2.setViewportView(jTextArea1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(126, 27, 29));

        tblnilai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblnilai);

        btnback.setBackground(new java.awt.Color(255, 119, 109));
        btnback.setForeground(new java.awt.Color(0, 153, 153));
        btnback.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/left-arrow.png"))); // NOI18N
        btnback.addActionListener(this::btnbackActionPerformed);

        jLabel1.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Nama Siswa:");

        jLabel3.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Mapel");

        jLabel4.setFont(new java.awt.Font("MS UI Gothic", 1, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Nilai: ");

        btndelete.setBackground(new java.awt.Color(248, 231, 232));
        btndelete.setFont(new java.awt.Font("MS UI Gothic", 1, 12)); // NOI18N
        btndelete.setForeground(new java.awt.Color(255, 255, 255));
        btndelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/delete.png"))); // NOI18N
        btndelete.addActionListener(this::btndeleteActionPerformed);

        btnupdate.setBackground(new java.awt.Color(248, 231, 232));
        btnupdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/update.png"))); // NOI18N
        btnupdate.addActionListener(this::btnupdateActionPerformed);

        btntambah.setBackground(new java.awt.Color(248, 231, 232));
        btntambah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/image/list.png"))); // NOI18N
        btntambah.addActionListener(this::btntambahActionPerformed);

        cbxnamasiswa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxnamasiswa.addActionListener(this::cbxnamasiswaActionPerformed);

        cbxnamamapel.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxnamamapel.addActionListener(this::cbxnamamapelActionPerformed);

        jLabel2.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("tambah");

        jLabel5.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ubah");

        jLabel6.setFont(new java.awt.Font("MS UI Gothic", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("hapus");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(481, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(26, 26, 26)
                                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(24, 24, 24))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(260, 260, 260)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(31, 31, 31)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtnilai)
                                    .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbxnamamapel, 0, 202, Short.MAX_VALUE)
                                    .addComponent(cbxnamasiswa, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                        .addGap(41, 41, 41))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cbxnamasiswa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbxnamamapel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtnilai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnupdate, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btntambah, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btndelete, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(5, 5, 5)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnback, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnbackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnbackActionPerformed
        // TODO add your handling code here:
        dispose();
        dashboard fdashboard = new dashboard() ;
        fdashboard.setVisible(true);
    }//GEN-LAST:event_btnbackActionPerformed

    private void cbxnamasiswaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxnamasiswaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxnamasiswaActionPerformed

    private void cbxnamamapelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbxnamamapelActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbxnamamapelActionPerformed

    private void btntambahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btntambahActionPerformed
        // TODO add your handling code here:
        if (selectedid != 0) {
            JOptionPane.showMessageDialog(this, 
                    "Anda sedang memilih data! klik tombol ubah jika ingin mengubah");
            return;
        }
        ComboItem siswa = (ComboItem) cbxnamasiswa.getSelectedItem();
        ComboItem mapel = (ComboItem) cbxnamamapel.getSelectedItem();
        String nilaiText = txtnilai.getText().trim();

        if (siswa == null || mapel == null || nilaiText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Siswa, Mapel, dan Nilai wajib diisi!");
            return;
        }
        
        double nilai;
        try {
            nilai = Double.parseDouble(nilaiText.replace(",", "."));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "Nilai harus berupa angka!");
            return;
        }
        if (nilai < 0 || nilai > 100) {
            JOptionPane.showMessageDialog(this,
                    "Nilai harus antara 0 - 100");
            return;
        }
        String sql = "INSERT INTO grade (Student_Id, ID_MAPEL, NILAI) VALUES (?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, siswa.getId());
            ps.setInt(2, mapel.getId());
            ps.setDouble(3, nilai);

            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(this, 
                        "data nilai berhasil ditambahkan");

                // 🔥 Reset form
                txtnilai.setText("");
                cbxnamasiswa.setSelectedIndex(0);
                cbxnamamapel.setSelectedIndex(0);

                tampiltabel();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "gagal menambahkan data nilai");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error insert: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_btntambahActionPerformed

    private void btnupdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnupdateActionPerformed
        // TODO add your handling code here:
        if (selectedid == 0) {
        JOptionPane.showMessageDialog(this, 
                "Pilih data nilai dari tabel terlebih dahulu!");
        return;
    }
        ComboItem siswa = (ComboItem) cbxnamasiswa.getSelectedItem();
        ComboItem mapel = (ComboItem) cbxnamamapel.getSelectedItem();
        String nilaiText = txtnilai.getText().trim();

        if (siswa == null || mapel == null || nilaiText.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "Siswa, Mapel, dan Nilai wajib diisi!");
            return;
        }
        int nilai;
        try {
            nilai = Integer.parseInt(nilaiText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                    "Nilai harus berupa angka!");
            return;
        }
        String sql = "UPDATE grade SET Student_Id = ?, ID_MAPEL = ?, NILAI = ? WHERE id = ?";
        PreparedStatement ps = null;
        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, siswa.getId());
            ps.setInt(2, mapel.getId());
            ps.setInt(3, nilai);
            ps.setInt(4, selectedid);

            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Data nilai berhasil diubah");

                txtnilai.setText("");
                cbxnamasiswa.setSelectedIndex(0);
                cbxnamamapel.setSelectedIndex(0);
                selectedid = 0;

                tampiltabel();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Gagal mengubah data nilai");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error update: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_btnupdateActionPerformed

    private void btndeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btndeleteActionPerformed
        // TODO add your handling code here:
        if (selectedid == 0) {
        JOptionPane.showMessageDialog(this, 
                "Pilih data dari tabel terlebih dahulu!");
        return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Yakin ingin menghapus data nilai ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        String sql = "DELETE FROM grade WHERE id = ?";
        PreparedStatement ps = null;

        try {
            ps = conn.prepareStatement(sql);
            ps.setInt(1, selectedid);

            int hasil = ps.executeUpdate();

            if (hasil > 0) {
                JOptionPane.showMessageDialog(this, 
                        "Data nilai berhasil dihapus");

                txtnilai.setText("");
                cbxnamasiswa.setSelectedIndex(0);
                cbxnamamapel.setSelectedIndex(0);
                selectedid = 0;

                tampiltabel();
            } else {
                JOptionPane.showMessageDialog(this, 
                        "Gagal menghapus data nilai");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                    "Error delete: " + ex.getMessage());
        } finally {
            try {
                if (ps != null) ps.close();
            } catch (SQLException e) {
            }
        }
    }//GEN-LAST:event_btndeleteActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new grade().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnback;
    private javax.swing.JButton btndelete;
    private javax.swing.JButton btntambah;
    private javax.swing.JButton btnupdate;
    private javax.swing.JComboBox cbxnamamapel;
    private javax.swing.JComboBox cbxnamasiswa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTable tblnilai;
    private javax.swing.JTextField txtnilai;
    // End of variables declaration//GEN-END:variables
}
