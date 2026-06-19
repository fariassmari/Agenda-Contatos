package servico;

import modelo_negocio.Contato;

public class ServicoContato extends Servico {
    private ServicoContato(){}

    public static void adicionarTelefoneContato(String numero, int id) throws Exception {
        try {
            repContato.begin();
            Contato contato = repContato.localizar(id);
            if (contato == null){
                throw new Exception("Adicionar telefone - contato não existe: " + id);
            } else if (numero == null || numero.isEmpty()) {
                throw new Exception("Adicionar telefone - número vazio");
            } else {
                contato.adicionarTelefone(numero);
                repContato.atualizar(contato);
                repContato.commit();
            }
        } catch (Exception e) {
            repContato.rollback();
            throw e;
        }

    }

    public static void apagarContato(int id) throws Exception {
        try {
            repContato.begin();
            Contato contato = repContato.localizar(id);
            if (contato == null) {
                throw new Exception("Apagar contato - id inexistente: " + id);
            } else {
                contato.setCidade(null);
                repContato.deletar(contato);
                repContato.commit();
            }
        } catch (Exception e) {
            repContato.rollback();
            throw e;
        }
    }
}
