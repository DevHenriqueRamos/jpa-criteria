package main.java.br.com.hramos.dao;

import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Root;
import main.java.br.com.hramos.dao.generic.GenericDAO;
import main.java.br.com.hramos.domain.Carro;

public class CarroDAO extends GenericDAO<Carro> implements ICarroDAO{

    public CarroDAO() {
        super();
    }

    @Override
    public Class<Carro> getClassType() {
        return Carro.class;
    }

    @Override
    protected void setUpdateFields(Root<Carro> root, CriteriaUpdate<Carro> update, Carro entity) {
        update.set(root.get("modelo"), entity.getModelo());
        update.set(root.get("anoFabricacao"), entity.getAnoFabricacao());
    }
}
