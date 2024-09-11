package main.java.br.com.hramos.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "TB_CARRO")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "carro_seq")
    @SequenceGenerator(name = "carro_seq", sequenceName = "sq_carro", initialValue = 1, allocationSize = 1)
    private Long id;

    @Column(name = "PLACA", length = 7, nullable = false, unique = true)
    private String placa;

    @Column(name = "MODELO", length = 50, nullable = false)
    private String modelo;

    @Column(name = "ANO_FABRICACAO", nullable = false)
    private int anoFabricacao;

    @ManyToOne
    @JoinColumn( name = "id_marca_fk",
        foreignKey = @ForeignKey(name = "fk_marca_matricula"),
        referencedColumnName = "id", nullable = false)
    private Marca marca;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public int getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(int anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }
}
