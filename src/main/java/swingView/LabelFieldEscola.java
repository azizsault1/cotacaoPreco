package swingView;

import java.awt.event.ItemEvent;
import java.util.Collection;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import cotacaoEscolar.app.exceptions.FoiNao;
import cotacaoEscolar.app.exceptions.IllegalError;
import cotacaoEscolar.model.Escola;
import cotacaoEscolar.model.v1.EscolaReal;
import swingView.interfaces.Label;
import swingView.interfaces.LabelFieldConfiguration;
import swingView.interfaces.Posicao;

public class LabelFieldEscola extends LabelField<Escola> {

   private static final long serialVersionUID = 1L;

   public interface EventoEscolaSelecionada {
      public void escolaSelecionada(Escola escola);

      public void maisEscolas(Escola escola) throws FoiNao;
   }

   public LabelFieldEscola(final int linha1, final EventoEscolaSelecionada escolaSelecionada) {
      super(LabelFieldConfiguration.Factory.create(Posicao.Factory.create(Dimensoes.MarginColuna1.getValor(), linha1), Label.Factory.create(70, "Colégio:")));
      super.addListeners(e -> {
         if (ItemEvent.SELECTED == e.getStateChange()) {
            final EscolaReal escolaEscolhida = (EscolaReal) e.getItem();
            escolaSelecionada.escolaSelecionada(escolaEscolhida);
         }
      });

      this.botaUmAe.addActionListener(ActionListener -> {
         final String escolaDigitada = JOptionPane.showInputDialog("Qual o nome de escola?");
         try {
            final Escola escola = Escola.create(escolaDigitada);
            escolaSelecionada.maisEscolas(escola);
         } catch (final IllegalError | FoiNao e) {
            final JOptionPane optionPane = new JOptionPane(e.getMessage(), JOptionPane.ERROR_MESSAGE);
            final JDialog dialog = optionPane.createDialog("Oooopsss...");
            dialog.setAlwaysOnTop(true);
            dialog.setVisible(true);
         }
      });
   }

   public Escola getEscolaEscolhida() {
      if (super.combo.getSelectedItem() != null) {
         return (Escola) super.combo.getSelectedItem();
      }

      throw new IllegalError("Escola inválida. Tente salvar primeiro a escola.");

   }

   public void atualizar(final Collection<Escola> escolas) {
      super.atualizarLista(escolas);
   }

   public void escolaSelecionada(final Escola escola) {
      this.combo.setSelectedItem(escola);
   }

}
