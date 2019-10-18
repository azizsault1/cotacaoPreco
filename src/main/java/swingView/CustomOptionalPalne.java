package swingView;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import cotacaoEscolar.model.v1.DescricaoMaterialEscolar;
import cotacaoEscolar.model.v1.ItemImpl;

public class CustomOptionalPalne {

   public interface AcaoBotoes {
      public void salvar(ItemImpl de, ItemImpl para);

      public void remover(ItemImpl item);
   }

   public static void displayGUI(final ItemImpl item, final AcaoBotoes acao) {
      final JOptionPane optionPane = new JOptionPane(null);
      optionPane.setLayout(null);
      final JDialog dialog = optionPane.createDialog(null, "Item");
      optionPane.add(getPanel(item, dialog, acao));
      dialog.setSize(350, 300);
      dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
      dialog.setVisible(true);
   }

   private static JPanel getPanel(final ItemImpl item, final JDialog dialog, final AcaoBotoes acao) {
      final JLabel label = new JLabel("Descricao:");
      label.setBounds(5, 5, 90, 20);

      final JTextField descricao = new JTextField(item.toString());
      descricao.setBounds(3, 25, 120, 20);

      final JLabel labelQuantidade = new JLabel("Quantidade:");
      labelQuantidade.setBounds(150, 5, 90, 20);

      final Integer current = 5;
      final Integer min = 1;
      final Integer max = 100;
      final Integer step = 1;
      final SpinnerNumberModel m_numberSpinnerModel = new SpinnerNumberModel(current, min, max, step);

      final JSpinner quantidade = new JSpinner(m_numberSpinnerModel);
      quantidade.setValue(item.getQuantidade());
      quantidade.setBounds(150, 25, 120, 20);

      final JButton salvar = new JButton("Salvar");
      salvar.setBounds(5, 150, 100, 100);
      salvar.addActionListener(action -> {
         final Integer quant = (Integer) quantidade.getValue();
         final ItemImpl novoItem = new ItemImpl(descricao.getText(), quant);
         acao.salvar(item, novoItem);
         dialog.dispose();
      });

      final JButton cancelar = new JButton("Cancela");
      cancelar.setBounds(115, 150, 100, 100);
      cancelar.addActionListener(action -> {
         dialog.dispose();
      });

      final JButton apagar = new JButton("Remover Item da Lista");
      apagar.setBounds(225, 150, 100, 100);
      apagar.addActionListener(action -> {
         acao.remover(item);
         dialog.dispose();
      });

      final JPanel panel = new JPanel();
      panel.setLayout(null);
      panel.add(label);
      panel.add(descricao);
      panel.add(labelQuantidade);
      panel.add(quantidade);
      panel.add(salvar);
      panel.add(cancelar);
      panel.add(apagar);
      panel.setBounds(3, 3, 343, 273);
      return panel;
   }

   public static void main(final String[] args) {
      final DescricaoMaterialEscolar descricao = DescricaoMaterialEscolar.create("Lapis");
      final ItemImpl item = new ItemImpl(descricao, 10);

      final AcaoBotoes acao = new AcaoBotoes() {

         @Override
         public void salvar(final ItemImpl de, final ItemImpl para) {
            System.out.println("CustomOptionalPalne.main(...).new AcaoBotoes() {...}.salvar() trocando de:" + de + " para: " + para);

         }

         @Override
         public void remover(final ItemImpl item) {
            System.out.println("CustomOptionalPalne.main(...).new AcaoBotoes() {...}.main()" + item);
         }
      };
      SwingUtilities.invokeLater(() -> CustomOptionalPalne.displayGUI(item, acao));
   }
}