package br.ufpb.sistemaComercial;

public class ProdutoNaoExisteException extends Exception{

    private static final long serialVersionUID = 1L;

    public ProdutoNaoExisteException(String msg) {
        super(msg);
    }

}
