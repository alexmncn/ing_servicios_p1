package com.alexmncn.ing_servicios_p1.dtos;

public class ArticleDTO {
    public Long ref;
    public String detalle;
    public int codfam;
    public float pvp;
    public Long codebar;
    public int stock;
    public boolean destacado;

    public ArticleDTO() {}

    public ArticleDTO(Long ref, String detalle, float pvp, int codfam, Long codebar, int stock, boolean destacado) {
        this.ref = ref;
        this.detalle = detalle;
        this.pvp = pvp;
        this.codfam = codfam;
        this.codebar = codebar;
        this.stock = stock;
        this.destacado = destacado;
    }

    public Long getRef() {
        return ref;
    }

    public void setRef(Long ref) {
        this.ref = ref;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public int getCodfam() {
        return codfam;
    }

    public void setCodfam(int codfam) {
        this.codfam = codfam;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Long getCodebar() {
        return codebar;
    }

    public void setCodebar(Long codebar) {
        this.codebar = codebar;
    }

    public boolean isDestacado() {
        return destacado;
    }

    public void setDestacado(boolean destacado) {
        this.destacado = destacado;
    }
}
