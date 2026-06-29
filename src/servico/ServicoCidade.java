package servico;

import java.util.List;

import modelo_negocio.Cidade;

public class ServicoCidade extends Servico {
    private ServicoCidade() {}

    public static Cidade localizarCidade(int id) {
        return repCidade.localizar(id);
    }

    public static Cidade localizarCidade(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        return repCidade.localizarNome(nome.trim());
    }

    public static void criarCidade(String nome) throws Exception {
        try {
            repCidade.begin();

            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome da cidade é obrigatório.");
            }

            nome = nome.trim();

            if (repCidade.localizarNome(nome) != null) {
                throw new Exception("Cidade já cadastrada.");
            }

            Cidade cidade = new Cidade(nome);
            repCidade.criar(cidade);

            repCidade.commit();
        } catch (Exception e) {
            repCidade.rollback();
            throw e;
        }
    }

    public static List<Cidade> listarCidades() {
        return repCidade.listar();
    }
}
