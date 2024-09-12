package main.java.br.com.hramos;

import jakarta.persistence.NoResultException;
import main.java.br.com.hramos.dao.IPecaDAO;
import main.java.br.com.hramos.dao.PecaDAO;
import main.java.br.com.hramos.domain.Peca;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class PecaTest {

    private IPecaDAO pecaDAO;

    public PecaTest() {
        pecaDAO = new PecaDAO();
    }

    @After
    public void tearDown() {
        Collection<Peca> pecas = pecaDAO.buscarTodos();
        for (Peca peca : pecas) {
            pecaDAO.remover(peca);
        }
    }

    @Test
    public void cadastrarTest() {
        // Criar uma Peça
        Peca peca = new Peca();
        peca.setCodigo("A1");
        peca.setNome("Para-choque");

        // cadastrar peça no db
        peca = pecaDAO.cadastrar(peca);

        // Peca nao deve ser null e deve ter um id
        Assert.assertNotNull(peca);
        Assert.assertNotNull(peca.getId());
    }

    @Test
    public void consultarTest() {
        // Criar uma Peça
        Peca peca = new Peca();
        peca.setCodigo("A1");
        peca.setNome("Para-choque");

        // cadastrar peça no db
        peca = pecaDAO.cadastrar(peca);

        Peca pecaConsultada = pecaDAO.buscar(peca.getCodigo());

        // pecaConsultada nao deve ser null e deve ter o mesmo id de peca
        Assert.assertNotNull(pecaConsultada);
        Assert.assertEquals(peca.getId(), pecaConsultada.getId());
    }

    @Test
    public void consultarTodosTest() {
        // Criar uma Peças
        for (int i = 1; i <= 2; i++) {
            Peca peca = new Peca();
            peca.setCodigo("A"+i);
            peca.setNome("Para-choque");
            // cadastrar peça no db
            peca = pecaDAO.cadastrar(peca);
        }

        Collection<Peca> pecas = pecaDAO.buscarTodos();

        // pecas nao deve ser null e deve ter um size igual a 2
        Assert.assertNotNull(pecas);
        Assert.assertEquals(2, pecas.size());
    }

    @Test
    public void atualizarTest() {
        // Criar uma Peça
        Peca peca = new Peca();
        peca.setCodigo("A1");
        peca.setNome("Para-choque");

        // cadastrar peça no db
        peca = pecaDAO.cadastrar(peca);

        // alterar nome da peca
        peca.setNome("Retrovisor");

        // atualizar peca no db
        pecaDAO.atualizar(peca);

        //buscar pela peca atualizada
        Peca pecaAtualizada = pecaDAO.buscar(peca.getCodigo());

        // pecaConsultada nao deve ser null e deve ter o mesmo id de peca
        Assert.assertNotNull(pecaAtualizada);
        Assert.assertNotEquals("Para-choque", pecaAtualizada.getNome());
        Assert.assertEquals("Retrovisor", pecaAtualizada.getNome());
    }

    @Test(expected = NoResultException.class)
    public void removerTest() {
        // Criar uma Peça
        Peca peca = new Peca();
        peca.setCodigo("A1");
        peca.setNome("Para-choque");

        // cadastrar peça no db
        peca = pecaDAO.cadastrar(peca);

        // remover peca
        pecaDAO.remover(peca);

        // tentar buscar pela peca
        pecaDAO.buscar(peca.getCodigo());
    }
}
