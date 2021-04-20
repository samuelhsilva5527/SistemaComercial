package br.ufpb.sistemaComercial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class SistemaComercialMap implements SistemaComercial {
	private Map<String, Cliente> clientes;
	private Map<String, Produto> produtos;
	
	public SistemaComercialMap () {
		this.clientes = new HashMap<String, Cliente>();
		this.produtos = new HashMap<String, Produto>();
	}

	@Override
	public boolean existeProduto(Produto produto) {
		if (this.produtos.containsKey(produto.getCodigo())) {
			return true;
		} else {
			return false;
		}
	}

    /**
     * Pesquisa o produto desejado e retorna seu cópdigo caso seja encontrado.
     * @param Produto que deseja pesquisar.
     * @return O código do produto caso ele seja encontrado, se não encontrar lança uma Exception.
     */
	@Override
	public Produto pesquisaProduto(String codigoProduto) throws ProdutoNaoExisteException {
		if (this.produtos.containsKey(codigoProduto)) {
			return this.produtos.get(codigoProduto);
		}
		throw new ProdutoNaoExisteException("Não foi encontrado produto com o código " + codigoProduto);
	}
    /**
     * Tentar adicionar um produto e retornar se o produto foi adicionado com sucesso.
     * @param Produto a ser adicionado.
     * @return Se o produto foi cadastrado corretamente.
     */
    @Override
    public boolean cadastraProduto(Produto produto) {
        if (produtos.size()==0) {
            this.produtos.put(produto.getCodigo(), produto);
            return true;
        }else{
            if (existeProduto(produto)) {
                return false;
            }else{
            	 this.produtos.put(produto.getCodigo(), produto);
                return true;
            }
        }
    }

    /**
     * Pesquisa os produtos da categoria pasasdda no parametro.
     * @param Categoria que deseja pesquisar.
     * @return Todos os produtos da categoria dada no parametro.
     */
	@Override
    public Collection<Produto> pesquisaProdutoDaCategoria(CategoriaProduto categoria) {
        Collection<Produto> produtosCategoria = new ArrayList<>();
        for (Produto p: this.produtos.values()) {
            if (p.getCategoria().equals(categoria)) {
                produtosCategoria.add(p);
            }
        }
        return produtosCategoria;
    }
    /**
     * Tenta cadastrar um cliente caso ele ainda não exista.
     * @param cliente que deseja cadastrar.
     * @return se o cliente foi cadastrado corretamente.
     */
    public boolean cadastraCliente(Cliente cliente){
        if (clientes.size()==0) {
            this.clientes.put(cliente.getId(), cliente);
            return true;
        } else {
            if (this.existeCliente(cliente)){
                return false;
            } else {
                this.clientes.put(cliente.getId(), cliente);
                return true;
            }
        }
    }

    @Override
    public boolean existeCliente(Cliente cliente){
        if (this.clientes.containsKey(cliente.getId())){
            return true;
        } else {
            return false;
        }
    }
	
    @Override
    public Cliente pesquisaCliente(String id) throws ClienteNaoExisteException{
        if(this.clientes.containsKey(id)){
            return this.clientes.get(id);
        }

        throw new ClienteNaoExisteException("Não foi encontrado nenhum cliente com este id: " + id);
    }

    public Collection<Produto> pesquisaProdutosEmFaixaDePreco(double limiteInferior, double limiteSuperior){

        Collection<Produto> produtosNaFaixaDePreco = new ArrayList<>();

        for (Produto p : this.produtos.values()){
            if (p.getPrecoVenda() >= limiteInferior && p.getPrecoVenda() <= limiteSuperior){
                produtosNaFaixaDePreco.add(p);
            }
        }

        return produtosNaFaixaDePreco;
    }

    public Collection<Produto> pesquisaProdutosComDescricaoComecandoCom(String prefixo){

        Collection<Produto> produtosComDescricaoComecandoCom = new ArrayList<>();

        for (Produto p : this.produtos.values()){
            if (p.getDescricao().startsWith(prefixo)){
                produtosComDescricaoComecandoCom.add(p);
            }
        }

        return produtosComDescricaoComecandoCom;
    }

    public Map<String, Cliente> pesquisarTodosOsClientes() {
        return clientes;
    }

    public Map<String, Produto> pesquisarTodosOsProdutos() {
        return produtos;
    }
}
