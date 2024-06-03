package IDE;


import Gals.LexicalError;
import Gals.Lexico;
import Gals.SemanticError;
import Gals.SemanticUtils.ReferencePointer;
import Gals.Semantico;
import Gals.Sintatico;
import Gals.SyntaticError;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

/**
 *
 * @author eas
 */
public class Main extends javax.swing.JFrame {

    /**
     * Creates new form MainWindow
     */
    private ArrayList<ReferencePointer> references = new ArrayList<>();
    public Main() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        sourceInput = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        console = new javax.swing.JTextArea();
        buttonCompile = new javax.swing.JButton();
        buttonTable = new javax.swing.JButton();
        buttonAssembly = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("VVR Language");
        setFont(new java.awt.Font("Andale Mono", 0, 18)); // NOI18N

        sourceInput.setColumns(20);
        sourceInput.setFont(new java.awt.Font("Helvetica Neue", 0, 18)); // NOI18N
        sourceInput.setRows(5);
        jScrollPane1.setViewportView(sourceInput);

        console.setEditable(false);
        console.setColumns(20);
        console.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        console.setLineWrap(true);
        console.setRows(5);
        console.setTabSize(4);
        jScrollPane2.setViewportView(console);

        buttonCompile.setFont(new java.awt.Font("Helvetica Neue", 0, 14)); // NOI18N
        buttonCompile.setText("Compilar");
        buttonCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCompileActionPerformed(evt);
            }
        });

        buttonTable.setFont(new java.awt.Font("Helvetica Neue", 0, 14));;
        buttonTable.setText("Gerar tabela");
        buttonTable.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonTableActionPerformed(evt);}
        });

        buttonAssembly.setFont(new java.awt.Font("Helvetica Neue", 0, 14));;
        buttonAssembly.setText("Gerar Assembly");
        buttonAssembly.addActionListener(new java.awt.event.ActionListener(){
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                try {
                    buttonAssemblyActionPerformed(evt);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
                                        .addComponent(jScrollPane2)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(buttonCompile)
                                                .addComponent(buttonTable)
                                                .addComponent(buttonAssembly)
                                        )
                                )
                                .addContainerGap())
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(3, 3, 3)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(buttonCompile)
                                        .addComponent(buttonTable)
                                        .addComponent(buttonAssembly)
                                )
                        )
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private static boolean isSuccess = false;
    private JFrame tableWindow;
    private JFrame assemblyFilePath;

    private void buttonCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCompileActionPerformed

        Lexico lex = new Lexico();
        Sintatico sint = new Sintatico();
        Semantico sem = new Semantico();


        StringReader reader = new StringReader(sourceInput.getText());

        lex.setInput(reader);

        try {
            sint.parse(lex, sem);
            String retorno = "Compilado com sucesso!\n";
            isSuccess = true;
            references = sem.getReferences();
            assemblyCode = sem.generateAssembly();

            for(ReferencePointer pointer : references){
                String nome = pointer.getNome();
                boolean iniciada = pointer.isIniciada();
                boolean utilizada = pointer.isUtilizada();
                if(!iniciada && !utilizada){
                    retorno += "Aviso: variável "+nome+" foi declarada mas não utilizada\n";
                }
                else if(!iniciada && utilizada){
                    retorno += "Aviso: variável "+nome+" foi usada sem ser declarada. Lixo de memória.\n";
                }
            }
            console.setText(retorno);

        } catch (LexicalError | SyntaticError | SemanticError ex) {
            console.setText("Problema na compilação: "+ex.getLocalizedMessage());
            isSuccess = false;
        }

    }//GEN-LAST:event_buttonCompileActionPerformed

    private void buttonTableActionPerformed(java.awt.event.ActionEvent evt){
        if (isSuccess) {
            ArrayList<ReferencePointer> references = this.references; // Obtenha a lista de referências
            if (tableWindow != null && tableWindow.isVisible()) {
                // Atualize os dados da tabela existente
                JTable existingTable = (JTable) ((JScrollPane) tableWindow.getContentPane().getComponent(0)).getViewport().getComponent(0);
                DefaultTableModel tableModel = (DefaultTableModel) existingTable.getModel();
                tableModel.setRowCount(0); // Limpe os dados existentes
                for (ReferencePointer pointer : references) {
                    tableModel.addRow(new Object[]{
                            pointer.getNome(),
                            pointer.getTipo(),
                            pointer.isIniciada(),
                            pointer.isUtilizada(),
                            pointer.getEscopo(),
                            pointer.isParameter(),
                            pointer.getPosicaoParameto(),
                            pointer.isVector(),
                            pointer.isReference(),
                            pointer.isFunction()
                    });
                }
            } else {
                // Crie uma nova tabela
                DefaultTableModel tableModel = new DefaultTableModel();
                tableModel.addColumn("Nome");
                tableModel.addColumn("Tipo");
                tableModel.addColumn("Iniciada");
                tableModel.addColumn("Utilizada");
                tableModel.addColumn("Escopo");
                tableModel.addColumn("Ultimo Valor");
                tableModel.addColumn("É Parâmetro");
                tableModel.addColumn("Posição Parâmetro");
                tableModel.addColumn("É Vetor");
                tableModel.addColumn("Tamanho Vetor");
                tableModel.addColumn("É Referência");
                tableModel.addColumn("É Função");

                for (ReferencePointer pointer : references) {
                    tableModel.addRow(new Object[]{
                            pointer.getNome(),
                            pointer.getTipo(),
                            pointer.isIniciada(),
                            pointer.isUtilizada(),
                            pointer.getEscopo(),
                            pointer.getLastValue(),
                            pointer.isParameter(),
                            pointer.getPosicaoParameto(),
                            pointer.isVector(),
                            pointer.getVectorSize(),
                            pointer.isReference(),
                            pointer.isFunction()
                    });
                }

                JTable table = new JTable(tableModel);
                JScrollPane scrollPane = new JScrollPane(table);
                tableWindow = new JFrame("Tabela");
                tableWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                tableWindow.add(scrollPane);
                tableWindow.setSize(500, 500);
                tableWindow.setLocationRelativeTo(null);
                tableWindow.setVisible(true);
            }
        }
        else {
            console.setText("É necessário compilar o código antes de gerar a tabela.");
        }
    }

    private void buttonAssemblyActionPerformed(java.awt.event.ActionEvent evt) throws IOException {
        if (isSuccess) {
                //Escolhe diretório para salvar
                JFileChooser jd = new JFileChooser();

                jd.setDialogTitle("Diretório para salvar assembly");
                int returnVal= jd.showOpenDialog(null);

                //Verifica se diretório é valido
                if(returnVal != JFileChooser.APPROVE_OPTION){
                    console.setText("Erro ao salvar arquivo");
                    return;
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(jd.getSelectedFile().getPath().toString()+".asm"));
                writer.write(assemblyCode);

                writer.close();
        }
        else {
            console.setText("É necessário compilar o código antes de gerar a tabela.");
        }
    }

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCompile;
    private javax.swing.JTextArea console;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea sourceInput;
    private javax.swing.JButton buttonTable;
    private javax.swing.JButton buttonAssembly;
    String assemblyCode;
    // End of variables declaration//GEN-END:variables
}