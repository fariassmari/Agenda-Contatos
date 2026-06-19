package modelo_negocio;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Contato {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nome;

    @ElementCollection
    private List<String> telefones = new ArrayList<>();

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Cidade cidade;

    public Contato() {}

    public Contato(String nome, List<String> telefones, Cidade cidade) {
        this.nome = nome;
        this.telefones = telefones;
        this.cidade = cidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getNome(){
        return this.nome;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public List<String> getTelefones() {
        return new ArrayList<>(telefones);
    }

    public void adicionarTelefone(String numero) throws Exception{
        if (telefones.contains(numero)) {
            throw new Exception("Telefone já está cadastrado no sistema." + numero);
        } else {
            telefones.add(numero);
        }
    }

    public void removerTelefone(String numero) throws Exception{
        if (telefones.contains(numero)) {
            telefones.remove(numero);
        } else {
            throw new Exception("Número não exite na lista de telefones." + numero);
        }
    }

    public Cidade getCidade(){
        return cidade;
    }

    public void setCidade(Cidade c){
        this.cidade = c;
    }

    @Override
    public String toString(){
        return "id=" + id +
                ", nome=" + nome +
                ", telefones=" + telefones +
                ", cidade=" + (cidade != null ? cidade.getNome() : "sem cidade");
    }

}
