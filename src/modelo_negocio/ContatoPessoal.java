package modelo_negocio;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ContatoPessoal extends Contato {
    private int grauProximidade;

    public ContatoPessoal() {}

    public ContatoPessoal(String nome, List<String> telefones, Cidade cidade, int grauProximidade) {
        super(nome, telefones, cidade);
        this.grauProximidade = grauProximidade;
    }

    public int getGrauProximidade() {
        return grauProximidade;
    }

    public void setGrauProximidade(int grauProximidade) {
        this.grauProximidade = grauProximidade;
    }
}
