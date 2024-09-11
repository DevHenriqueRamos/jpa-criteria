package main.java.br.com.hramos.dao;

import main.java.br.com.hramos.domain.Marca;

import java.util.List;

public interface IMarcaDAO {
    public Marca cadastrar(Marca marca);
    public void atualizar(Marca marca);
    public void remover(Marca marca);
    public Marca buscarPorCodigo(String codigo);
    public List<Marca> buscarTodos();
}
