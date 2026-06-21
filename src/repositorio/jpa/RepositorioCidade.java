package repositorio.jpa;

import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo_negocio.Cidade;

public class RepositorioCidade extends Repositorio<Cidade> {
    public Cidade localizarNome(String nome) {
        TypedQuery<Cidade> q = manager.createQuery(
                "select c from Cidade c where upper(c.nome) = upper(:n)", Cidade.class);
        q.setParameter("n", nome);
        List<Cidade> resultado = q.getResultList();
        return resultado.isEmpty() ? null : resultado.get(0);
    }
}
