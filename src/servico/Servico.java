package servico;

import repositorio.jpa.*;

public class Servico {
    protected Servico(){}

    protected static RepositorioContato repContato = new RepositorioContato();
    protected static RepositorioContatoComercial repContatoComercial = new RepositorioContatoComercial();
    protected static RepositorioContatoPessoal repContatoPessoal = new RepositorioContatoPessoal();
    protected static RepositorioCidade repCidade = new RepositorioCidade();

    public static void conectar() throws Exception{
        Repositorio.conectar();
    }

    public static void desconectar() throws Exception{
        Repositorio.desconectar();
    }
}
