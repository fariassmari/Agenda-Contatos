package modelo_negocio;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ContatoPessoal extends Contato {
    public ContatoPessoal() {}

    public ContatoPessoal(String nome, List<String> telefones, Cidade cidade) {
        super(nome, telefones, cidade);
    }
}
