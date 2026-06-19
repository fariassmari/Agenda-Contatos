package repositorio.jpa;

import java.util.List;

import jakarta.persistence.TypedQuery;
import modelo_negocio.ContatoComercial;

public class RepositorioContatoComercial extends Repositorio<ContatoComercial> {
    public ContatoComercial localizarNome(String nome) {
        TypedQuery<ContatoComercial> q = manager.createQuery("select c from ContatoComercial c where c.nome = :n", ContatoComercial.class);
        q.setParameter("n", nome.toUpperCase());
        return q.getSingleResultOrNull();
    }

    @Override
    public List<ContatoComercial> listar(){
        TypedQuery<ContatoComercial> q = manager.createQuery("select c from ContatoComercial c", ContatoComercial.class);
        return q.getResultList();
    }
}
