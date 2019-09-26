package cotacaoEscolar.model;

import java.math.BigDecimal;

import cotacaoEscolar.app.IllegalError;
import io.swagger.annotations.ApiModel;

@ApiModel(description = "Representa um produto que é disponibilizado em um estabelecimento.")
public class Produto {
   private final DescricaoMaterialEscolar materialEscolar;
   private final BigDecimal valor;
   private final Integer quantidadeEstoque;

   private Produto(final DescricaoMaterialEscolar descricao, final BigDecimal valor, final Integer quantidade) {
      this.materialEscolar = descricao;
      this.valor = valor;
      this.quantidadeEstoque = quantidade;
   }

   public boolean equivale(final Item item) {
      final boolean result = this.materialEscolar.equals(item.getMaterialEscolar());
      return result;
   }

   public BigDecimal getValor() {
      return this.valor;
   }

   public Integer getQuantidade() {
      return this.quantidadeEstoque;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = (prime * result) + ((this.materialEscolar == null) ? 0 : this.materialEscolar.hashCode());
      return result;
   }

   @Override
   public boolean equals(final Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (this.getClass() != obj.getClass()) {
         return false;
      }
      final Produto other = (Produto) obj;
      if (this.materialEscolar == null) {
         if (other.materialEscolar != null) {
            return false;
         }
      } else if (!this.materialEscolar.equals(other.materialEscolar)) {
         return false;
      }
      return true;
   }

   @Override
   public String toString() {
      return "Produto [materialEscolar=" + this.materialEscolar + ", valor=" + this.valor + "]";
   }

   public static Produto create(final DescricaoMaterialEscolar materialEscolar, final BigDecimal valor, final Integer quantidade) {
      validar(valor, quantidade);
      return new Produto(materialEscolar, valor, quantidade);
   }

   private static void validar(final BigDecimal valor, final Integer quantidade) {
      if (valor == null) {
         throw new IllegalError("Você precisa definir uma qu");
      }
      if (valor.intValue() <= 0) {
         throw new IllegalError("O valor de um prduto não poder ser menor ou igual a zero.");
      }
   }

   public static Produto create(final String materialEscolar, final BigDecimal valor, final Integer quantidade) {
      final DescricaoMaterialEscolar materialEscolarCriado = DescricaoMaterialEscolar.create(materialEscolar);
      return create(materialEscolarCriado, valor, quantidade);
   }

}
