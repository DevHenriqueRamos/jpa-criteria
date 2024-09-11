package main.java.br.com.hramos.dao;

import jakarta.persistence.criteria.*;
import main.java.br.com.hramos.dao.generic.GenericDAO;
import main.java.br.com.hramos.domain.Marca;

public class MarcaDAO extends GenericDAO<Marca> implements IMarcaDAO {

    public MarcaDAO() {
        super();
    }

    @Override
    public Class<Marca> getClassType() {
        return Marca.class;
    }

    @Override
    protected void setUpdateFields(Root<Marca> root, CriteriaUpdate<Marca> update, Marca entity) {
        update.set(root.get("nome"), entity.getNome());
    }
}
