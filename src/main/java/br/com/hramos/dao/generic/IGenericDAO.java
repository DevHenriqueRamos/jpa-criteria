package main.java.br.com.hramos.dao.generic;

import main.java.br.com.hramos.domain.Persistent;

import java.util.Collection;
import java.util.List;

public interface IGenericDAO<T extends Persistent>{
    public T cadastrar(T entity);
    public void atualizar(T entity);
    public void remover(T entity);
    public T buscar(String codigo);
    public Collection<T> buscarTodos();
}
