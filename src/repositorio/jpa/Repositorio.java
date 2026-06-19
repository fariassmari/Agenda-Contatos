package repositorio.jpa;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import repositorio.CRUDInterface;

public abstract class Repositorio<T> implements CRUDInterface<T> {
    protected static EntityManager manager;

    private static final Logger logger = LogManager.getLogger(Util.class);

    public static void conectar() throws Exception {
        manager = Util.conectar();
    }

    public static void desconectar() throws Exception {
        Util.desconectar();
        manager = null;
    }

    @Override
    public void criar(T objeto) {
        manager.persist(objeto);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T localizar(int id) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];

        T resultado = (T) manager.find(classe, id);
        return resultado;
    }

    public T atualizar(T objeto) {
        return manager.merge(objeto);
    }

    public void deletar(T objeto) {
        manager.remove(objeto);
    }

    @SuppressWarnings("unchecked")
    public List<T> listar() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        Class<T> classe = (Class<T>) type.getActualTypeArguments()[0];

        TypedQuery<T> q = manager.createQuery("select x from " + classe.getSimpleName() + " x	order by x.id", classe);
        List<T> resultados = q.getResultList();
        return resultados;
    }

    public void begin() {
        if (!manager.getTransaction().isActive())
            manager.getTransaction().begin();
    }

    public void commit() {
        if (manager.getTransaction().isActive()) {
            manager.getTransaction().commit();
            manager.clear();
        }
    }

    public void rollback() {
        if (manager.getTransaction().isActive())
            manager.getTransaction().rollback();
    }

}
