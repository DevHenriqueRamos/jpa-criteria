package main.java.br.com.hramos;

import jakarta.persistence.NoResultException;
import main.java.br.com.hramos.dao.CarroDAO;
import main.java.br.com.hramos.dao.ICarroDAO;
import main.java.br.com.hramos.dao.IMarcaDAO;
import main.java.br.com.hramos.dao.MarcaDAO;
import main.java.br.com.hramos.domain.Carro;
import main.java.br.com.hramos.domain.Marca;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class CarroTest {

    private ICarroDAO carroDAO;
    private static IMarcaDAO marcaDAO;
    private static Marca marca;

    public CarroTest() {
        carroDAO = new CarroDAO();
        marcaDAO = new MarcaDAO();
    }

    private Marca criarMarca() {
        Marca marca = new Marca();
        marca.setCodigo("A1");
        marca.setNome("FIAT");

        return marcaDAO.cadastrar(marca);
    }

    public Carro criarCarro() {
        if (marca == null) {
            marca = criarMarca();
        }
        Carro carro = new Carro();
        carro.setModelo("Estrada");
        carro.setPlaca("brc4488");
        carro.setAnoFabricacao(2008);
        carro.setMarca(marca);
        return carro;
    }

    @After
    public void tearDown() {
        Collection<Carro> carros = carroDAO.buscarTodos();
        for (Carro carro : carros) {
            carroDAO.remover(carro);
        }
    }

    @AfterClass
    public static void apagarMarca() {
        marcaDAO.remover(marca);
    }

    @Test
    public void cadastrarTest() {
        // Criar um Carro
        Carro carro = criarCarro();

        // cadastrar carro no banco de dados
        carro = carroDAO.cadastrar(carro);

        //checar se carro é recebido na variavel "carro" e tem agora um id
        Assert.assertNotNull(carro);
        Assert.assertNotNull(carro.getId());
    }

    @Test
    public void consultarTest() {
        // Criar um Carro
        Carro carro = criarCarro();

        // cadastrar carro no banco de dados
        carro = carroDAO.cadastrar(carro);

        // buscar carro no db
        Carro carroConsultado = carroDAO.buscar(carro.getPlaca());

        // carro consultado nao deve ser nulo e o id do carro e carroConsultado devem ser o mesmo
        Assert.assertNotNull(carroConsultado);
        Assert.assertEquals(carro.getId(), carroConsultado.getId());
    }

    @Test
    public void consultarTodosTest() {
        // Cadastrar 2 carros no db
        Carro carro1 = criarCarro();
        carroDAO.cadastrar(carro1);

        Carro carro2 = new Carro();
        carro2.setModelo("Uno");
        carro2.setPlaca("uno4477");
        carro2.setAnoFabricacao(2010);
        carro2.setMarca(marca);
        carroDAO.cadastrar(carro2);


        // buscar todos os carros cadastrados
        Collection<Carro> carros = carroDAO.buscarTodos();

        // carro consultado nao deve ser nulo e o id do carro e carroConsultado devem ser o mesmo
        Assert.assertNotNull(carros);
        Assert.assertEquals(2, carros.size());
    }

    @Test
    public void atualizarTest() {
        // Criar um Carro
        Carro carro = criarCarro();

        // cadastrar carro no banco de dados
        carro = carroDAO.cadastrar(carro);

        // atualizar os campos de carro
        carro.setModelo("Mobi");
        carro.setAnoFabricacao(2013);

        // salvar as mudanças no db
        carroDAO.atualizar(carro);

        // buscar carro no db
        Carro carroConsultado = carroDAO.buscar(carro.getPlaca());

        // carro nao deve ser nulo, o nome nao pode ser o mesmo que o original "Estrada"
        // e o ano de fabricação deve ser diferente de 2008
        Assert.assertNotNull(carroConsultado);
        Assert.assertNotEquals(carroConsultado.getModelo(), "Estrada");
        Assert.assertNotEquals(carroConsultado.getAnoFabricacao(), 2008);
    }

    // deve retornar um erro de sem resultado
    @Test(expected = NoResultException.class)
    public void removerTest() {
        // Criar um Carro
        Carro carro = criarCarro();

        // cadastrar carro no banco de dados
        carro = carroDAO.cadastrar(carro);

        // remover carro do db
        carroDAO.remover(carro);

        // buscar pelo carro excluido no db
        carroDAO.buscar(carro.getPlaca());
    }
}
