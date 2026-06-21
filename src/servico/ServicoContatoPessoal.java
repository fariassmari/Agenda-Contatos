package servico;

import java.util.List;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoPessoal;

public class ServicoContatoPessoal extends Servico {
    private ServicoContatoPessoal() {}

    public static ContatoPessoal localizarContatoPessoal(int id) {
        return repContatoPessoal.localizar(id);
    }

    public static ContatoPessoal localizarContatoPessoal(String nome) {
        return repContatoPessoal.localizarNome(nome);
    }

    public static void criarContatoPessoal(String nome, int idcidade) throws Exception {
        try {
            repContatoPessoal.begin();
            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Nome do contato pessoal é obrigatório.");
            }

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null) {
                throw new Exception("Cidade obrigatória para contato pessoal.");
            }
            if (repContato.localizarNome(nome.trim()) != null) {
                throw new Exception("Nome já existente.");
            }

            ContatoPessoal contato = new ContatoPessoal(nome.trim(), null, cidade);
            repContatoPessoal.criar(contato);
            repContatoPessoal.commit();
        } catch (Exception e) {
            repContatoPessoal.rollback();
            throw e;
        }
    }

    public static void alterarContatoPessoal(String nome, int idcidade) throws Exception {
        try {
            repContatoPessoal.begin();
            ContatoPessoal contato = repContatoPessoal.localizarNome(nome);
            if (contato == null) {
                throw new Exception("Contato pessoal não encontrado.");
            }

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null) {
                throw new Exception("Cidade obrigatória para contato pessoal.");
            }

            contato.setCidade(cidade);
            repContatoPessoal.atualizar(contato);
            repContatoPessoal.commit();
        } catch (Exception e) {
            repContatoPessoal.rollback();
            throw e;
        }
    }

    public static List<ContatoPessoal> listarContatosPessoais() {
        return repContatoPessoal.listar();
    }
}
