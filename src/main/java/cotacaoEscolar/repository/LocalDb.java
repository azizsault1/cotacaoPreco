package cotacaoEscolar.repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import cotacaoEscolar.app.IllegalError;
import cotacaoEscolar.model.DescricaoMaterialEscolar;
import cotacaoEscolar.model.Escola;
import cotacaoEscolar.model.Estabelecimento;
import cotacaoEscolar.model.Item;
import cotacaoEscolar.model.Produto;
import cotacaoEscolar.model.listas.ListaEstabelecimento;
import cotacaoEscolar.model.listas.ListaMaterial;
import cotacaoEscolar.model.listas.ListaProduto;

@org.springframework.stereotype.Repository
public class LocalDb implements Repository {
   private Set<Escola> escolas;
   private Set<DescricaoMaterialEscolar> itens;
   private Map<String, List<Item>> listasEstaticas;
   private final List<ListaMaterial> listaMaterialEscolar = new ArrayList<>();
   private ListaEstabelecimento estabelecimentos;

   private Set<Produto> produtos;

   public LocalDb() {
      this.initEscolas();
   }

   private void initEscolas() {
      final Escola escola1 = new Escola("Escola1");
      final Escola escola2 = new Escola("Escola2");
      final Escola escola3 = new Escola("Escola3");

      this.escolas = new HashSet<>(Arrays.asList(escola1, escola2, escola3));
      this.initItens();
      this.initMateriais(escola1, escola2, escola3);
      this.initProdutos();
   }

   private void initItens() {

      final DescricaoMaterialEscolar lapisDesc = new DescricaoMaterialEscolar("Lapis 123");
      final Item lapis = new Item(lapisDesc, 5);

      final DescricaoMaterialEscolar lapisDeCorDesc = new DescricaoMaterialEscolar("Lapis de cor");
      final Item lapisDeCor = new Item(lapisDeCorDesc, 30);

      final DescricaoMaterialEscolar classificadorDesc = new DescricaoMaterialEscolar("Classificador");
      final Item classificador = new Item(classificadorDesc, 2);

      final DescricaoMaterialEscolar cadernoDesc = new DescricaoMaterialEscolar("Caderno");
      final Item caderno = new Item(cadernoDesc, 1);

      final List<DescricaoMaterialEscolar> itensFabricados = DescricaoMaterialEscolar.create(20);

      this.itens = new HashSet<>(Arrays.asList(lapisDesc, lapisDeCorDesc, classificadorDesc, cadernoDesc));
      itensFabricados.forEach(itemEntrontrado -> this.itens.add(itemEntrontrado));

      final List<Item> lista1 = Arrays.asList(lapis, lapisDeCor);
      final List<Item> lista2 = Arrays.asList(lapis, classificador);
      final List<Item> lista3 = Arrays.asList(caderno, classificador);

      this.listasEstaticas = new HashMap<>();
      this.listasEstaticas.put("Lista1", lista1);
      this.listasEstaticas.put("Lista2", lista2);
      this.listasEstaticas.put("Lista3", lista3);
   }

   private void initMateriais(final Escola escola1, final Escola escola2, final Escola escola3) {

      // Escola1
      this.listaMaterialEscolar.add(new ListaMaterial(escola1, Integer.valueOf(1), this.selecionePor(1)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola1, Integer.valueOf(2), this.selecionePor(2)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola1, Integer.valueOf(3), this.selecionePor(3)));
      // Escola2
      this.listaMaterialEscolar.add(new ListaMaterial(escola2, Integer.valueOf(3), this.selecionePor(3)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola2, Integer.valueOf(2), this.selecionePor(2)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola2, Integer.valueOf(1), this.selecionePor(1)));

      // Escola3
      this.listaMaterialEscolar.add(new ListaMaterial(escola3, Integer.valueOf(2), this.selecionePor(2)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola3, Integer.valueOf(1), this.selecionePor(1)));
      this.listaMaterialEscolar.add(new ListaMaterial(escola3, Integer.valueOf(4), this.selecionePor(4)));
   }

   private void initProdutos() {
      final List<DescricaoMaterialEscolar> listaItens = this.itens.stream().collect(Collectors.toList());
      this.produtos = new HashSet<>();

      for (int i = 0; i < listaItens.size(); i++) {
         final Produto produto = Produto.create(listaItens.get(i), BigDecimal.valueOf(i + 1), Integer.valueOf(i + 1));
         this.produtos.add(produto);
      }

      this.initEstabelecimentos();
   }

   private void initEstabelecimentos() {

      final List<Produto> todos = this.produtos.stream().collect(Collectors.toList());

      // Estabelecimento1
      final ListaProduto produtosEstabelecimento1 = new ListaProduto(todos.get(0), todos.get(1));
      final Estabelecimento estabelecimento1 = new Estabelecimento("Estabelecimento1", produtosEstabelecimento1);

      // Estabelecimento2
      final ListaProduto produtosEstabelecimento2 = new ListaProduto(todos);
      final Estabelecimento estabelecimento2 = new Estabelecimento("Estabelecimento2", produtosEstabelecimento2);

      // Estabelecimento3
      final ListaProduto produtosEstabelecimento3 = new ListaProduto(todos.get(9), todos.get(8), todos.get(7), todos.get(6), todos.get(5), todos.get(4));
      final Estabelecimento estabelecimento3 = new Estabelecimento("Estabelecimento3", produtosEstabelecimento3);

      // Estabelecimento4
      final ListaProduto produtosEstabelecimento4 = new ListaProduto(todos.get(0), todos.get(2), todos.get(4), todos.get(6), todos.get(8));
      final Estabelecimento estabelecimento4 = new Estabelecimento("Estabelecimento4", produtosEstabelecimento4);

      // Estabelecimento5
      final ListaProduto produtosEstabelecimento5 = new ListaProduto(todos.get(1), todos.get(3), todos.get(5), todos.get(7), todos.get(9));
      final Estabelecimento estabelecimento5 = new Estabelecimento("Estabelecimento5", produtosEstabelecimento5);

      this.estabelecimentos = new ListaEstabelecimento(Arrays.asList(estabelecimento1, estabelecimento2, estabelecimento3, estabelecimento4, estabelecimento5));
   }

   @Override
   public Collection<Escola> escolas() {
      return this.escolas;
   }

   @Override
   public List<Item> selecionePor(final int serie) {
      switch (serie) {
      case 1:
         return this.listasEstaticas.get("Lista1");
      case 2:
         return this.listasEstaticas.get("Lista2");
      default:
         return this.listasEstaticas.get("Lista3");
      }
   }

   @Override
   public Collection<DescricaoMaterialEscolar> todasDescricoes() {
      return this.itens;
   }

   @Override
   public Collection<ListaMaterial> materiais() {
      return this.listaMaterialEscolar;
   }

   @Override
   public void add(final ListaMaterial novaLista) {
      this.listaMaterialEscolar.add(novaLista);
   }

   @Override
   public ListaEstabelecimento estabelecimentos() {
      return this.estabelecimentos;
   }

   @Override
   public DescricaoMaterialEscolar selecionarPor(final DescricaoMaterialEscolar materialEscolar) {

      if (!this.itens.contains(materialEscolar)) {
         this.itens.add(materialEscolar);
      }

      return this.itens.stream().filter(materialEscolar::equals).findAny()
            .orElseThrow(() -> new IllegalError("Não foi possível encontrar o material escolar: [" + materialEscolar + "]"));

   }

}
