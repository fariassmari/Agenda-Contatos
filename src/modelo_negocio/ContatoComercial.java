package modelo_negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ContatoComercial extends Contato {
    private String empresa;

    public ContatoComercial() {}

    public ContatoComercial(String nome, List<String> telefones, String empresa, Cidade cidade) {
        super(nome, telefones, cidade);
        this.empresa = empresa;
    }

    public ContatoComercial(String nome, String empresa, Cidade cidade) {
        super(nome, new ArrayList<>(), cidade);
        this.empresa = empresa;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    @Override
    public String toString() {
        return super.toString() + ", empresa=" + empresa;
    }
}
