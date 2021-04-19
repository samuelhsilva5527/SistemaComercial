package br.ufpb.sistemaComercial;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SistemaComercialMapTest {
	
	SistemaComercialMap sistema;
	
	ClientePJ clientepj;
	ClientePF clientepf;
	
	Produto produtoAlimento;
	Produto produtoRoupa;
	Produto produtoLimpeza;
	

	@BeforeEach
	void setUp() {
		this.sistema = new SistemaComercialMap();
		
		this.clientepj = new ClientePJ("Chico", "Rua Manoel Guedes", "chico@email", "24458289000155");
		this.clientepf = new ClientePF("Samuel", "Rua Nova", "samuel@email", "46405336085");
		
		this.produtoAlimento = new Produto("196", "Macarrão", 6, 50, CategoriaProduto.ALIMENTO);
		this.produtoRoupa = new Produto("197", "Calça para uma jovem de 16 anos", 301, 5, CategoriaProduto.ROUPA );
		this.produtoLimpeza = new Produto("198", "Água sanitaria Dragão", 2.50, 500, CategoriaProduto.PRODUTO_DE_LIMPEZA);	
		
	}
	
	@Test
	void testCadastrarClientePJPF() {
		
		assertTrue(sistema.pesquisarTodosOsClientes().isEmpty());
		
		assertTrue(sistema.cadastraCliente(clientepj));
		
		assertTrue(sistema.pesquisarTodosOsClientes().size() == 1);
		
		assertTrue(sistema.cadastraCliente(clientepf));
		
		assertTrue(sistema.pesquisarTodosOsClientes().size() == 2);
	}
	
	@Test
	void testPesquisaCliente() {
		assertThrows(ClienteNaoExisteException.class, ()-> sistema.pesquisaCliente(clientepj.getId()));
		assertThrows(ClienteNaoExisteException.class, ()-> sistema.pesquisaCliente(clientepf.getId()));
		
	}
	
	@Test
	void testCadastrarProduto() {
		assertTrue(sistema.pesquisarTodosOsProdutos().isEmpty());
		
		//assertTrue(sistema.pesquisarTodosOsProdutos() instanceof Map<?, ?>);
		
		//assertTrue(sistema.pesquisarTodosOsProdutos() instanceof Map<String, Produto>);
		
		assertTrue(sistema.cadastraProduto(produtoAlimento));
		
		assertTrue(sistema.pesquisarTodosOsProdutos().size() == 1);
		
		assertTrue(sistema.cadastraProduto(produtoRoupa));
		
		assertTrue(sistema.pesquisarTodosOsProdutos().size() == 2);
		
		assertTrue(sistema.cadastraProduto(produtoLimpeza));
		
		assertTrue(sistema.pesquisarTodosOsProdutos().size() == 3);
		
		if(sistema.cadastraProduto(produtoAlimento)) {
			fail("Não deveria cadastrar o mesmo produto");
		}
	}
	
	@Test
	void testPesquisarProduto() {
		
		assertThrows(ProdutoNaoExisteException.class, ()-> sistema.pesquisaProduto(produtoAlimento.getCodigo()));
		
		try {
			sistema.cadastraProduto(produtoAlimento);
			assertTrue(sistema.pesquisaProduto(produtoAlimento.getCodigo()) instanceof Produto);
		} catch (ProdutoNaoExisteException e) {
			fail("Não deveria falhar");
		}
	}
	
	@Test
	void testPesquisaProdutoDaCategoria() {
		assertTrue(sistema.pesquisaProdutoDaCategoria(produtoAlimento.getCategoria()).isEmpty());
		
		
		sistema.cadastraProduto(produtoAlimento);
		sistema.cadastraProduto(produtoRoupa);
		sistema.cadastraProduto(produtoLimpeza);
		
		assertTrue(sistema.pesquisaProdutoDaCategoria(produtoAlimento.getCategoria()).size() == 1);
		assertTrue(sistema.pesquisaProdutoDaCategoria(produtoRoupa.getCategoria()).size() == 1);
		assertTrue(sistema.pesquisaProdutoDaCategoria(produtoLimpeza.getCategoria()).size() == 1);
		Produto produtoTeste = new Produto("199", "teste", 32, 10, CategoriaProduto.ROUPA );
		
		sistema.cadastraProduto(produtoTeste);
		assertTrue(sistema.pesquisaProdutoDaCategoria(produtoRoupa.getCategoria()).size() == 2);
	}
	
	@Test
	void testPesquisaProdutosEmFaixaDePreco() {
		
	}
}
