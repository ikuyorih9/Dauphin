package com.dauphin.dauphin.services;

import com.dauphin.dauphin.dtos.GrupoCreateDTO;
import com.dauphin.dauphin.models.Grupo;

public interface GrupoService {
    public static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public Grupo buscar(Integer id);
    public Grupo criar(GrupoCreateDTO dto);
    public void apagar(Integer id);
    public String gerarConvite(Integer id, Long expirationTime);
    public long aceitarConvite(String token);
}
