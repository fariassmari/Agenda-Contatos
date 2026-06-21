package repositorio.jpa;

import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo_negocio.ContatoPessoal;

public class RepositorioContatoPessoal extends Repositorio<ContatoPessoal> {
    public ContatoPessoal localizarNome(String nome) {
        TypedQuery<ContatoPessoal> q = manager.createQuery(
                "select c from ContatoPessoal c where upper(c.nome) = upper(:n)", ContatoPessoal.class);
        q.setParameter("n", nome);
        List<ContatoPessoal> resultado = q.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
