package servico;

import java.util.List;

import modelo_negocio.Cidade;
import modelo_negocio.ContatoComercial;

public class ServicoContatoComercial extends Servico{
    private ServicoContatoComercial(){}

    public static ContatoComercial localizarContatoComercial(int id){
        return repContatoComercial.localizar(id);
    }

    public static ContatoComercial localizarContatoComercial(String nome){
        return repContatoComercial.localizarNome(nome);
    }

    public static void criarContatoComercial(String nome, String empresa, int idcidade) throws Exception{
        try {
            repContatoComercial.begin();
            if (empresa == null || empresa.isEmpty()) {
                throw new Exception("Para criar contato comercial, empresa é obrigatoria.");
            }

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null) {
                throw new Exception("Para criar contato comercial, cidade é obrigatoria.");
            }
            if (repContatoComercial.localizarNome(nome.trim()) != null) {
                throw new Exception("Para criar contato comercial, não pode ser criado com nome já existente.");
            }

            ContatoComercial contatoComercial = new ContatoComercial(nome.trim(), empresa.trim(), cidade);
            repContatoComercial.criar(contatoComercial);
            repContatoComercial.commit();
        } catch (Exception e){
            repContatoComercial.rollback();
            throw e;
        }
    }

    public static void alterarContatoComercial(String nome, String empresa, int idcidade) throws Exception{
        try {
            repContatoComercial.begin();
            ContatoComercial contatoComercial = repContatoComercial.localizarNome(nome.trim());
            if (contatoComercial == null) {
                throw new Exception("Para alterar contato comercial, nome deve ser existente.");
            }

            if (empresa == null || empresa.trim().isEmpty()) {
                throw new Exception("Para alterar contato comercial, empresa é obrigatoria.");
            }

            Cidade cidade = repCidade.localizar(idcidade);
            if (cidade == null){
                throw new Exception("Para alterar contato comercial, cidade é obrigatoria.");
            }

            contatoComercial.setCidade(cidade);
            contatoComercial.setEmpresa(empresa.trim());
            repContatoComercial.atualizar(contatoComercial);
            repContatoComercial.commit();
        } catch (Exception e){
            repContatoComercial.rollback();
            throw e;
        }
    }

    public static List<ContatoComercial> listarContatosEmpresa() {
        return repContatoComercial.listar();
    }
}
