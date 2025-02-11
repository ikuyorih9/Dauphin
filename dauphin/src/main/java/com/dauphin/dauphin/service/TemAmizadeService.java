package com.dauphin.dauphin.service;

import com.dauphin.dauphin.model.TemAmizade;
import com.dauphin.dauphin.model.Usuario;

public interface TemAmizadeService {
    public boolean possuemAmizade(Usuario usuario1, Usuario usuario2);
    public TemAmizade criar(Usuario usuario1, Usuario usuario2);
}
