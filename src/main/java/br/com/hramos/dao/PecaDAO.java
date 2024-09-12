package main.java.br.com.hramos.dao;

import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import main.java.br.com.hramos.dao.generic.GenericDAO;
import main.java.br.com.hramos.domain.Peca;

public class PecaDAO extends GenericDAO<Peca> implements IPecaDAO {

    public PecaDAO() {
        super();
    }

    @Override
    public Class<Peca> getClassType() {
        return Peca.class;
    }

    @Override
    protected void setUpdateFields(Root<Peca> root, CriteriaUpdate<Peca> update, Peca entity) {
        update.set(root.get("nome"), entity.getNome());
    }
}
