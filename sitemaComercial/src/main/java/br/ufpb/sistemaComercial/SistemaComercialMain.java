package br.ufpb.sistemaComercial;

import java.util.Collection;

import javax.swing.*;

public class SistemaComercialMain {

    public static void main(String[] args) {

        SistemaComercialMap sistema = new SistemaComercialMap();
        String entrada = "";


        do {
            entrada = JOptionPane.showInputDialog(null,
                    "============ DIGITE O NÚMERO DA OPÇÃO DESEJADA ============"
                            + "\n[1] - Pesquisa Produto."
                            + "\n[2] - Cadastrar um produto."
                            + "\n[3] - Pesquisa Cliente."
                            + "\n[4] - Cadastrar Cliente."
                            + "\n[5] - Pesquisar produtos de uma categoria específica."
                            + "\n[6] - Pesquisa produtos em uma faixa de preço."
                            + "\n[7] - Pesquisa produtos com uma descrição."
                            + "\n[8] - Sair.\n"
                            + "============ DIGITE APENAS O NUMERO QUE REPRESENTA AS OPÇÕES ============");

            switch (entrada) {
                case "1":
                    // Pesquisa Produto
                    while (true) {

                        try {
                            String codigoDoProduto = JOptionPane.showInputDialog(null, "Digite o código do produto: ");
                            if (codigoDoProduto.isEmpty()) {//Caso o usuário não digite nada o programa avisa que ele não digitou nada e aguarda ele digitar para poder tratar essa entrada
                                JOptionPane.showMessageDialog(null, "Digite o código para que seja feita a busca!");
                            } else {
                                Produto p = sistema.pesquisaProduto(codigoDoProduto);
                                JOptionPane.showMessageDialog(null, p.toString());

                                //Após exibir o produto encontrado ou lancar exceção caso não exista o while(true) deixa de ser verdade e para para a próxima instrução
                                break;
                            }

                        } catch (ProdutoNaoExisteException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                            break;//Já que lançou excessão o break lá em cima não foi utilizado, esse agora é utilizado para fechar o while(true)
                        }

                    }//Fim do while

                    //Esse break é para quando tenham sido feitas as tratativas da entrada do usuário o sistema retorne ao menu principal
                    break;

                case "2":
                    // Cadastrar um produto
                    boolean cadastrou = false;
                    String codigo = "", descricao = "", categoria = "";
                    double precoVenda = 0.0;
                    int quantEstoque = 0;


                    while (true) {
                        try {
                            codigo = JOptionPane.showInputDialog(null, "Digite o código do produto:");
                            descricao = JOptionPane.showInputDialog(null, "Digite a descrição do produto:");
                            precoVenda = Double.parseDouble(JOptionPane.showInputDialog(null, "Digite o preço do produto:"));
                            quantEstoque = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite a quantidade em estoque: "));
                            categoria = JOptionPane.showInputDialog(null,
                                    "Digite o número correspondente a categoria:"
                                            + "\n[1] - ALIMENTO"
                                            + "\n[2] - ROUPA"
                                            + "\n[3] - PRODUTO_DE_LIMPEZA");

                            switch (categoria) {
                                case "1":
                                    cadastrou = sistema.cadastraProduto(new Produto(codigo, descricao, precoVenda, quantEstoque, CategoriaProduto.ALIMENTO));
                                    break;
                                case "2":
                                    cadastrou = sistema.cadastraProduto(new Produto(codigo, descricao, precoVenda, quantEstoque, CategoriaProduto.ROUPA));
                                    break;
                                case "3":
                                    cadastrou = sistema.cadastraProduto(new Produto(codigo, descricao, precoVenda, quantEstoque, CategoriaProduto.PRODUTO_DE_LIMPEZA));
                                    break;
                            }

                            if (cadastrou) {
                                JOptionPane.showMessageDialog(null, "Produto cadastrado com sucesso!");
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o produto!");
                                break;
                            }

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Digite as entradas corretamente!");
                            e.getStackTrace();
                        }

                    }//Fim do while

                    //retorna ao menu principal
                    break;

                case "3":
                    // Pesquisa Cliente
                    while (true) {

                        try {

                            String id = JOptionPane.showInputDialog(null, "Digite o id do cliente: ");
                            if (id.isEmpty()) {
                                JOptionPane.showMessageDialog(null, "Digite o id para que seja feita a busca!");
                            } else {
                                Cliente c = sistema.pesquisaCliente(id);
                                JOptionPane.showMessageDialog(null, c.toString());
                                break;
                            }

                        } catch (ClienteNaoExisteException e) {
                            JOptionPane.showMessageDialog(null, e.getMessage());
                            break;
                        }

                    }//Fim do while

                    //retorna ao menu principal
                    break;

                case "4":
                    //Cadastrar Cliente
                    boolean cliente = false;
                    String nome;
                    String endereco;
                    String email;
                    int tipoCliente;
                    String idCliente;

                    while (true) {
                        try {
                            nome = JOptionPane.showInputDialog(null, "Digite o nome de cliente: ");
                            endereco = JOptionPane.showInputDialog(null, "Digite o endereço do cliente: ");
                            email = JOptionPane.showInputDialog(null, "Digite o email do cliente: ");
                            tipoCliente = Integer.parseInt(JOptionPane.showInputDialog(null, 
                                "Digite o número correspondente ao cliente:"
                                        + "\n[1] - Pessoa Fisica"
                                        + "\n[2] - Pessoa Juridica"));
                            
                            switch (tipoCliente) {
                                case 1:
                                    idCliente = JOptionPane.showInputDialog(null, "Digite o CPF do cliente: ");
                                    cliente = sistema.cadastraCliente(new ClientePF(nome, endereco, email, idCliente));
                                    break;
                                case 2:
                                    idCliente = JOptionPane.showInputDialog(null, "Digite o CNPJ do cliente: ");
                                    cliente = sistema.cadastraCliente(new ClientePJ(nome, endereco, email, idCliente));
                                    break;
                            }

                            if (cliente) {
                                JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
                                break;
                            } else {
                                JOptionPane.showMessageDialog(null, "Não foi possível cadastrar o Cliente!");
                                break;
                            }

                        }catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(null, "Digite as entradas corretamente!");
                            e.getStackTrace();
                        }
                    }//Fim do while

                    //retorna ao menu principal
                    break;

                case "5":
                    // Pesquisar produtos de uma categoria específica
                    try {
                        String opcaoCategoria = JOptionPane.showInputDialog(null,
                                   "Digite o número correspondente a categoria:"
                                            + "\n[1] - ALIMENTO"
                                            + "\n[2] - ROUPA"
                                            + "\n[3] - PRODUTO_DE_LIMPEZA");
                                            
                        switch(opcaoCategoria){
                            case "1":                                
                                Collection<Produto> produtoAlimento = sistema.pesquisaProdutoDaCategoria(CategoriaProduto.ALIMENTO);
                                
                                String stringExibirMsg1 = "";
                                for(Produto p: produtoAlimento){
                                    stringExibirMsg1 +=  p.getDescricao();
                                    stringExibirMsg1 +=  "\n";
                                }
                                JOptionPane.showMessageDialog(null, stringExibirMsg1); 
                                break;                            
                            case "2":
                                Collection<Produto> produtoRoupa = sistema.pesquisaProdutoDaCategoria(CategoriaProduto.ROUPA);
                                
                                 String stringExibirMsg2 = "";
                                     for(Produto p: produtoRoupa){
                                        stringExibirMsg2 +=  p.getDescricao();
                                        stringExibirMsg2 +=  "\n";
                                    }
                                    JOptionPane.showMessageDialog(null, stringExibirMsg2); 
                                break; 
                            case "3":
                                Collection<Produto> produtoLimpeza = sistema.pesquisaProdutoDaCategoria(CategoriaProduto.PRODUTO_DE_LIMPEZA);                                    
                                String stringExibirMsg3 = "";
                                for(Produto p: produtoLimpeza){
                                    stringExibirMsg3 +=  p.getDescricao();
                                    stringExibirMsg3 +=  "\n";
                                }
                                JOptionPane.showMessageDialog(null, stringExibirMsg3);  
                                break; 
                            }
                        
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Digite as entradas corretamente!");
                        e.getStackTrace();
                    }

                    break;
                case "6":
                    // Pesquisa produtos em uma faixa de preço

                    String faixaDePrecoInferiorStr = "";
                    String faixaDePrecoSuperiorStr = "";
                    Double faixaDePrecoInferior = 0.0;
                    Double faixaDePrecoSuperior = 0.0;
                    boolean validadeInferior = false;
                    boolean validadeSuperior = false;

                    do{
                        try{
                            faixaDePrecoInferiorStr = JOptionPane.showInputDialog(
                            "=============== PESQUISAR PRODUTOS ENTRE A FAIXA DE PREÇO x E y ==============="
                            + "\nDigite o limite infeiror");
                            if(!faixaDePrecoInferiorStr.isEmpty()){
                                faixaDePrecoInferior = Double.parseDouble(faixaDePrecoInferiorStr);
                                Double d = faixaDePrecoInferior;
                                if((d/1) == d){
                                    validadeInferior = true;
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Digite as Entradas Corretamentes");
                                    continue;
                                }   
                            }
                        }catch(Exception e){
                            e.getStackTrace();
                        }
                    }while(!validadeInferior);

                    do{
                        try{
                            faixaDePrecoSuperiorStr = JOptionPane.showInputDialog(
                            "=============== PESQUISAR PRODUTOS ENTRE A FAIXA DE PREÇO x E y ==============="
                            + "\nDigite o limite superior");
                            if(!faixaDePrecoSuperiorStr.isEmpty()){
                                faixaDePrecoSuperior = Double.parseDouble(faixaDePrecoSuperiorStr);
                                Double d = faixaDePrecoSuperior;
                                if((d/1) == d){
                                    validadeSuperior = true;
                                }
                                else{
                                    JOptionPane.showMessageDialog(null, "Digites as entradas corretamentes");
                                    continue;
                                }
                            }
                        }catch(Exception e){
                            e.getStackTrace();
                        }
                    }while(!validadeSuperior);


                    System.out.println(faixaDePrecoInferior + " e " + faixaDePrecoSuperior);

                    String stringExibir = "Lista vazia";

                    if (sistema.pesquisaProdutosEmFaixaDePreco(faixaDePrecoInferior, faixaDePrecoSuperior).isEmpty()) {
                        JOptionPane.showMessageDialog(null, stringExibir);
                    }else{
                        stringExibir = "";
                        Collection<Produto> produtosNaFaixaDePreco = sistema.pesquisaProdutosEmFaixaDePreco(faixaDePrecoInferior, faixaDePrecoSuperior);
                        
                        for(Produto p: produtosNaFaixaDePreco){
                            stringExibir +=  p.getDescricao();
                            stringExibir +=  "\n";
                        }
                        JOptionPane.showMessageDialog(null, stringExibir);
                    }

                    //Retorna ao menu principal.
                    break;

                case "7":
                    // Pesquisa produtos com uma descrição
                    String prefixo = JOptionPane.showInputDialog("Digite a descricao do produto a ser pesquisado: "); 
                    do{
                        prefixo = JOptionPane.showInputDialog("Digite uma descrição válida dos produtos a serem pesquisados:");
                    }while(prefixo.length() == 0);

                    Collection<Produto> produtos = sistema.pesquisaProdutosComDescricaoComecandoCom(prefixo);
                    String strExibir = "";
                    for(Produto p: produtos){
                        strExibir +=  p.getDescricao();
                        strExibir +=  "\n";
                    }
                    JOptionPane.showMessageDialog(null, strExibir);

                    //Retorna ao menu principal
                    break;

                case "8":
                    // Sair
                    JOptionPane.showMessageDialog(null, "Fechando programa...");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Número ou Caractere Inválido");
            }

        } while (!entrada.equals("8"));

    }

}
