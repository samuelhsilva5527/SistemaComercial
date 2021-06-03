package br.ufpb.sistemaComercial;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;

//IMPORTS READER-WRITER
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;

public class SistemaComercialMap implements SistemaComercial {
	private Map<String, Cliente> clientes;
	private Map<String, Produto> produtos;
	
	public SistemaComercialMap () {
		this.clientes = new HashMap<String, Cliente>();
		this.produtos = new HashMap<String, Produto>();
        recuperarDados();
	}
    
    @Override
    public boolean gravarDados(){
        File fileClientes = new File("clientes.txt");
        File fileProdutos = new File("produtos.txt");
        try {
            FileWriter fileWriterC = new FileWriter(fileClientes);
            BufferedWriter bwC = new BufferedWriter(fileWriterC);
            for (Cliente cliente : clientes.values()) {
                String stringClienteComposta = cliente.getNome() +"#"+ cliente.getEndereco() +"#"+ cliente.getEmail() +"#"+ cliente.getId();
                bwC.write(stringClienteComposta);
                bwC.newLine();
            }
            bwC.flush();
            fileWriterC.close();

            FileWriter fileWriterP = new FileWriter(fileProdutos);
            BufferedWriter bwP = new BufferedWriter(fileWriterP);
            for (Produto produto : produtos.values()) {
                String stringProdutoComposto = produto.getCodigo() +"#"+ produto.getDescricao() +"#"+ produto.getPrecoVenda() +"#"+ produto.getQuantidadeEmEstoque() +"#"+ produto.getCategoria();
                bwP.write(stringProdutoComposto);
                bwP.newLine();
            }
            bwP.flush();
            fileWriterP.close();


            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            return false;
        }
    }

    @Override
    public void recuperarDados(){
        File arquivoClientes = new File("clientes.txt");
        File arquivoProdutos = new File("produtos.txt");
        try{
            FileReader fileReaderC = new FileReader(arquivoClientes);
            BufferedReader brC = new BufferedReader(fileReaderC);
            String lineC;
            while((lineC = brC.readLine()) != null){
                String nome, endereco, email, id;
                String vetor [] = lineC.split("#");
                nome = vetor[0];
                endereco = vetor[1];
                email = vetor[2];
                id = vetor[3];
                Cliente c = new ClientePF(nome, endereco, email, id); //ERRADO
                this.clientes.put(c.getId(), c );        
            }

            FileReader fileReaderP = new FileReader(arquivoProdutos);
            BufferedReader brP = new BufferedReader(fileReaderP);
            String lineP;
            while((lineP = brP.readLine()) != null){
                String codigo, descricao;
                Double precoVenda;
                int quantidadeEmEstoque;
                
                String vetor [] = lineP.split("#");
                codigo = vetor[0];
                descricao = vetor[1];
                precoVenda = Double.parseDouble(vetor[2]);
                quantidadeEmEstoque = Integer.parseInt(vetor[3]);
                
                
                switch(vetor[4]){
                    case "ALIMENTO":
                        this.produtos.put(codigo, new Produto(codigo, descricao, precoVenda, quantidadeEmEstoque, CategoriaProduto.ALIMENTO));  
                            break;
                    case "ROUPA":
                        this.produtos.put(codigo, new Produto(codigo, descricao, precoVenda, quantidadeEmEstoque, CategoriaProduto.ROUPA));
                            break;
                    case "PRODUTO_DE_LIMPEZA":
                        this.produtos.put(codigo, new Produto(codigo, descricao, precoVenda, quantidadeEmEstoque, CategoriaProduto.PRODUTO_DE_LIMPEZA));
                            break;
                }   
            }
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Map<String,Produto> getP(){
        return this.produtos;
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
                gravarDados();
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
