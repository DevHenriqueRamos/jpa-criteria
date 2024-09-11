package main.java.br.com.hramos;

import jakarta.persistence.NoResultException;
import main.java.br.com.hramos.dao.CarroDAO;
import main.java.br.com.hramos.dao.ICarroDAO;
import main.java.br.com.hramos.dao.IMarcaDAO;
import main.java.br.com.hramos.dao.MarcaDAO;
import main.java.br.com.hramos.domain.Carro;
import main.java.br.com.hramos.domain.Marca;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

public class MarcaTest {

    private IMarcaDAO marcaDAO;
    private ICarroDAO carroDAO;

    public MarcaTest() {
        marcaDAO = new MarcaDAO();
        carroDAO = new CarroDAO();
    }

    public Carro criarCarro(Marca marca, String placa) {
        Carro carro = new Carro();
        carro.setModelo("Estrada");
        carro.setPlaca(placa);
        carro.setAnoFabricacao(2008);
        carro.setMarca(marca);

        return carroDAO.cadastrar(carro);
    }

    @After
    public void tearDown() {

        Collection<Marca> marcas = marcaDAO.buscarTodos();
        for (Marca marca : marcas) {
            List<Carro> carros = marca.getCarros();
            for (Carro carro : carros) {
                carroDAO.remover(carro);
            }
            marcaDAO.remover(marca);
        }
    }

    @Test
    public void cadastrarTest() {
        // Criar uma marca
        Marca marca = new Marca();
        marca.setCodigo("A1");
        marca.setNome("FIAT");

        // cadastrar marca no banco de dados
        marca = marcaDAO.cadastrar(marca);

        criarCarro(marca, "brc4488");

        //checar se o produto é recebido na variavel "marca" e tem agora um id
        Assert.assertNotNull(marca);
        Assert.assertNotNull(marca.getId());
    }

    @Test
    public void consultarTest() {
        // Criar uma marca
        Marca marca = new Marca();
        marca.setCodigo("A1");
        marca.setNome("FIAT");

        // cadastrar marca no banco de dados
        marca = marcaDAO.cadastrar(marca);

        // criar carro para a marca
        criarCarro(marca, "brc4488");

        // buscar marca criada no DB
        Marca marcaConsultada = marcaDAO.buscarPorCodigo(marca.getCodigo());

        // marca consultada nao deve ser null e o id de marca e marcaConsultada devem ser o mesmo
        Assert.assertNotNull(marcaConsultada);
        Assert.assertEquals(marca.getId(), marcaConsultada.getId());
    }

    @Test
    public void consultarTodosTest() {
        // for para cadastrar duas marcas no db
        for(int i = 1; i <= 2; i++) {
            Marca marca = new Marca();
            marca.setCodigo("A"+ i);
            marca.setNome("FIAT");
            // cadastrar marca no banco de dados
            marcaDAO.cadastrar(marca);
            // criar carro para a marca
            criarCarro(marca, "brc448" + i);
        }

        // buscar marcas criadas no DB
        Collection<Marca> marcas = marcaDAO.buscarTodos();

        // a lista de marcas nao pode ser nula e deve conter 2 items na lista
        Assert.assertNotNull(marcas);
        Assert.assertEquals(2, marcas.size());
    }

    @Test
    public void atualizarTest() {
        // Criar uma marca
        Marca marca = new Marca();
        marca.setCodigo("A1");
        marca.setNome("FIAT");

        // cadastrar marca no banco de dados
        marca = marcaDAO.cadastrar(marca);

        // criar carro para a marca
        criarCarro(marca, "brc4488");

        // alterar nome da marca
        marca.setNome("FORD");

        // atualizar no db o novo nome em marca
        marcaDAO.atualizar(marca);

        // buscar marca atualizada no DB
        Marca marcaConsultada = marcaDAO.buscarPorCodigo(marca.getCodigo());

        // marca consultada deve ter o nome FORD e o mesmo id de marca
        Assert.assertEquals("FORD", marcaConsultada.getNome());
        Assert.assertEquals(marca.getId(), marcaConsultada.getId());

    }

    // deve retornar um erro de sem resultado
    @Test(expected = NoResultException.class)
    public void removerTest() {
        // Criar uma marca
        Marca marca = new Marca();
        marca.setCodigo("A1");
        marca.setNome("FIAT");

        // cadastrar marca no banco de dados
        marca = marcaDAO.cadastrar(marca);

        // criar carro para a marca
        Carro carro = criarCarro(marca, "brc4488");

        // remover carro para entao remover marca
        carroDAO.remover(carro);

        // remover marca do DB
        marcaDAO.remover(marca);

        // buscar marca atualizada no DB
        marcaDAO.buscarPorCodigo(marca.getCodigo());
    }
}
