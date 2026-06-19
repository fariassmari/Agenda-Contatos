package repositorio;

import java.util.List;

public interface CRUDInterface<T> {
    public void criar(T objeto);
    public T localizar(int id);
    public T atualizar(T objeto);
    public void deletar(T objeto);
    public List<T> listar();
    public void begin();
    public void commit();
    public void rollback();
}