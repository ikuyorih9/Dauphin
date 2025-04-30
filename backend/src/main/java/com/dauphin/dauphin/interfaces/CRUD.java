package com.dauphin.dauphin.interfaces;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

public interface CRUD<T, ID, DTO> {
    public T buscar(ID id);
    public T criar(DTO id);
    public void remover(ID id, String currentUserUsername);
}
