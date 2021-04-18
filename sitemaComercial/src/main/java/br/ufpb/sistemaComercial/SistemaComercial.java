package br.ufpb.sistemaComercial;

import java.util.Collection;
import java.util.Map;

public interface SistemaComercial {
	public boolean existeProduto(Produto produto);
	
	public Produto pesquisaProduto(String codigoProduto) throws ProdutoNaoExisteException;
	
	public boolean cadastraProduto(Produto produto);
	
	public boolean existeCliente(Cliente cliente);
	
	public Cliente pesquisaCliente(String id) throws ClienteNaoExisteException;
	
	public Collection<Produto> pesquisaProdutoDaCategoria(CategoriaProduto categoria);
	
	public Collection<Produto> pesquisaProdutosEmFaixaDePreco(double liteInferior, double limiteSuperior);
	
	public Collection<Produto> pesquisaProdutosComDescricaoComecandoCom(String prefixo);

	public Map<String, Cliente> pesquisarTodosOsClientes();

	public Map<String, Produto> pesquisarTodosOsProdutos();

}
