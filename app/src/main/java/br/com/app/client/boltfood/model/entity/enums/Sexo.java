package br.com.app.client.boltfood.model.entity.enums;

public enum Sexo {

    MASCULINO(1, "Masculino"),
    FEMININO(2, "Feminino"),
    INFEDINIDO(3, "Indefinido");

    private int codigo;
    private String descricao;

    private Sexo(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Sexo toEnum(Integer codigo) {
        if (codigo == null)
            return null;
        for (Sexo sexo : Sexo.values()) {
            if (codigo.equals(sexo.getCodigo())) {
                return sexo;
            }
        }
        throw  new IllegalArgumentException("Id inválido: " + codigo);
    }

}
