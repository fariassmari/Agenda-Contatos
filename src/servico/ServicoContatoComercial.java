package servico;

import java.util.List;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoComercial;

public class ServicoContatoComercial extends Servico {
    private ServicoContatoComercial() {}

    public static ContatoComercial localizarContatoComercial(int id) {
        return repContatoComercial.localizar(id);
    }

    public static ContatoComercial localizarContatoComercial(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return null;
        }

        return repContatoComercial.localizarNome(nome.trim());
    }

    public static void criarContatoComercial(String nome, String empresa, int idcidade) throws Exception {
        try {
            repContatoComercial.begin();

            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Para criar contato comercial, nome é obrigatório.");
            }

            if (empresa == null || empresa.trim().isEmpty()) {
                throw new Exception("Para criar contato comercial, empresa é obrigatória.");
            }

            nome = nome.trim();
            empresa = empresa.trim();

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null) {
                throw new Exception("Para criar contato comercial, cidade é obrigatória.");
            }

            if (repContato.localizarNome(nome) != null) {
                throw new Exception("Para criar contato comercial, não pode ser criado com nome já existente.");
            }

            ContatoComercial contatoComercial = new ContatoComercial(nome, empresa, cidade);
            repContatoComercial.criar(contatoComercial);

            repContatoComercial.commit();
        } catch (Exception e) {
            repContatoComercial.rollback();
            throw e;
        }
    }

    public static void alterarContatoComercial(String nome, String empresa, int idcidade) throws Exception {
        try {
            repContatoComercial.begin();

            if (nome == null || nome.trim().isEmpty()) {
                throw new Exception("Para alterar contato comercial, nome é obrigatório.");
            }

            if (empresa == null || empresa.trim().isEmpty()) {
                throw new Exception("Para alterar contato comercial, empresa é obrigatória.");
            }

            nome = nome.trim();
            empresa = empresa.trim();

            ContatoComercial contatoComercial = repContatoComercial.localizarNome(nome);
            if (contatoComercial == null) {
                throw new Exception("Para alterar contato comercial, nome deve ser existente.");
            }

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null) {
                throw new Exception("Para alterar contato comercial, cidade é obrigatória.");
            }

            contatoComercial.setEmpresa(empresa);
            contatoComercial.setCidade(cidade);

            repContatoComercial.atualizar(contatoComercial);

            repContatoComercial.commit();
        } catch (Exception e) {
            repContatoComercial.rollback();
            throw e;
        }
    }

    public static List<ContatoComercial> listarContatosEmpresa() {
        return repContatoComercial.listar();
    }
}
