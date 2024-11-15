INSERT INTO  tipos.permissoes (id, nome)
VALUES (1, 'administrador'),
       (2, 'usuario')
    ON CONFLICT DO NOTHING;

INSERT INTO entidades.usuarios(usuario, senha, permissao_id)
VALUES ('admin', '$2a$10$mDjXyTb.nTnvz/IBfUJGOO1/6r1XVX/X5.pv.7wNNeDVW9XgZP.9O', 1),
       ('usuario.comum', '$2a$10$mDjXyTb.nTnvz/IBfUJGOO1/6r1XVX/X5.pv.7wNNeDVW9XgZP.9O', 2)
    ON CONFLICT DO NOTHING;