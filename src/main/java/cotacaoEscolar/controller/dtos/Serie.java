package cotacaoEscolar.controller.dtos;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import cotacaoEscolar.app.exceptions.IllegalError;
import cotacaoEscolar.model.Escola;
import cotacaoEscolar.model.v1.helpers.EscolaValidator;
import io.swagger.annotations.ApiModel;

@JsonRootName("serie")
@ApiModel(description = "Classe ou ano curricular de uma escola.")
public class Serie {

   private String escola;
   private String serie;

   @JsonCreator
   public Serie(@JsonProperty("escola") final String escola, @JsonProperty("serie") final String serie) {
      this.validate();
      this.escola = escola;
      this.serie = serie;
   }

   public String getEscola() {
      return this.escola;
   }

   public void setSerie(final String serie) {
      this.serie = serie;
   }

   public void setEscola(final String escola) {
      this.escola = escola;
   }

   public String getSerie() {
      return this.serie;
   }

   public void validate() {
      if (this.escola == null) {
         throw new IllegalError("Escola invalida");
      }

      EscolaValidator.create(this.escola).validate();

      if ((this.serie == null) || (this.serie.trim().isEmpty())) {
         throw new IllegalError("Serie invalida");
      }
   }

   @JsonIgnore
   public Escola getEscolaModel() {
      return Escola.create(this.escola);
   }

   @JsonIgnore
   public cotacaoEscolar.model.v1.Serie getSerieModel() {
      return cotacaoEscolar.model.v1.Serie.create(this.serie);
   }

}