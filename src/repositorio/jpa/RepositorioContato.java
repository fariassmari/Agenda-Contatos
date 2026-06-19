package repositorio.jpa;

import jakarta.persistence.TypedQuery;
import modelo_negocio.Contato;

public class RepositorioContato extends Repositorio<Contato> {
    public Contato localizarNome(String nome) {
        TypedQuery<Contato> q = manager.createQuery("Select c from Contato c where c.nome = :n", Contato.class);
        q.setParameter("n", nome.toUpperCase());
        return q.getSingleResultOrNull();
    }
}
