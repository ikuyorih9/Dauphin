package com.dauphin.dauphin.services;

import java.util.List;

import org.hibernate.annotations.TenantId;

import com.dauphin.dauphin.models.TemAmizade;
import com.dauphin.dauphin.models.TemAmizade.TemAmizadeId;

public interface TemAmizadeService {
    public TemAmizade buscar(String username1, String username2);
    public TemAmizade criar(String username1, String username2);
    public void apagar(String username1, String username2);
    public List<TemAmizade> listar(String username);
}
