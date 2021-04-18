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

	@Override
	public Produto pesquisaProduto(String codigoProduto) throws ProdutoNaoExisteException {
		if (this.produtos.containsKey(codigoProduto)) {
			return this.produtos.get(codigoProduto);
		}
		throw new ProdutoNaoExisteException("Não foi encontrado produto com o código " + codigoProduto);
	}

    @Override
    public boolean cadastraProduto(Produto produto) {
        if (produtos.size()==0) {
            this.produtos.put(produto.getCodigo(), produto);
            return true;
        }else{
            if (existeProduto(produto)) {
                return false;
            }else{
                return true;
            }
        }
    }

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
