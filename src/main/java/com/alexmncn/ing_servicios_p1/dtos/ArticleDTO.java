package com.alexmncn.ing_servicios_p1.dtos;

import java.math.BigInteger;

public class ArticleDTO {
    public BigInteger ref;
    public String detalle;
    public float pvp;
    public BigInteger codebar;

    public ArticleDTO(BigInteger ref, String detalle, float pvp, BigInteger codebar) {
        this.ref = ref;
        this.detalle = detalle;
        this.pvp = pvp;
        this.codebar = codebar;
    }

    public ArticleDTO() {

    }

    public BigInteger getRef() {
        return ref;
    }

    public void setRef(BigInteger ref) {
        this.ref = ref;
    }

    public float getPvp() {
        return pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public BigInteger getCodebar() {
        return codebar;
    }

    public void setCodebar(BigInteger codebar) {
        this.codebar = codebar;
    }
}
