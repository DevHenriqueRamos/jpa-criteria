package main.java.br.com.hramos.dao;

import main.java.br.com.hramos.domain.Carro;
import main.java.br.com.hramos.domain.Marca;

import java.util.List;

public interface ICarroDAO {
    public Carro cadastrar(Carro carro);
    public void remover(Carro carro);
    public List<Carro> buscarTodos();
}
