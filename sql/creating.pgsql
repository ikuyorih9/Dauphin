DROP TABLE IF EXISTS Nivel;
CREATE TABLE Nivel(
    classificacao VARCHAR(255),
    minimo_pontos INTEGER NOT NULL,
    descricao VARCHAR(1024) NOT NULL,
    CONSTRAINT PK_NIVEL PRIMARY KEY (classificacao)
);

DROP TABLE IF EXISTS Usuario;
CREATE TABLE Usuario(
    username VARCHAR(255),
    email VARCHAR(255) NOT NULL,
    nome VARCHAR(255) NOT NULL,
    senha VARCHAR(255) NOT NULL,
    sexo CHAR,
    data_nascimento DATE,
    data_cadastro DATE,
    foto VARCHAR(255),
    pontuacao INTEGER,
    nivel VARCHAR(255),
    CONSTRAINT PK_USUARIO PRIMARY KEY (username),
    CONSTRAINT SK_USUARIO UNIQUE(email),
    CONSTRAINT FK_USUARIO FOREIGN KEY (nivel) REFERENCES Nivel(classificacao) ON DELETE CASCADE,
    CONSTRAINT CK_SEXO CHECK (sexo = 'M' OR sexo = 'F')
);

DROP TABLE IF EXISTS Tem_Amizade;
CREATE TABLE Tem_Amizade(
    usuario1 VARCHAR(255),
    usuario2 VARCHAR(255),
    data_inicio DATE NOT NULL,
    CONSTRAINT PK_AMIZADE PRIMARY KEY(usuario1, usuario2),
    CONSTRAINT FK_USUARIO1 FOREIGN KEY (usuario1) REFERENCES Usuario(username) ON DELETE CASCADE,
    CONSTRAINT FK_USUARIO2 FOREIGN KEY (usuario2) REFERENCES Usuario(username) ON DELETE CASCADE
);

DROP INDEX IF EXISTS unique_friendship_idx;
CREATE UNIQUE INDEX unique_friendship_idx
ON tem_amizade (LEAST(usuario1, usuario2), GREATEST(usuario1, usuario2));

CREATE SEQUENCE grupo_id_seq START 1; -- Criando uma sequência manual

DROP TABLE IF EXISTS Grupo;
CREATE TABLE Grupo (
    usuario_host VARCHAR(255),
    id INTEGER DEFAULT nextval('grupo_id_seq'), -- Usando a sequência para gerar o ID
    data_criacao DATE NOT NULL,
    nome VARCHAR(512) NOT NULL,
    qtd_maxima INTEGER DEFAULT 20,
    CONSTRAINT PK_GRUPO PRIMARY KEY (usuario_host, id),
    CONSTRAINT FK_HOST FOREIGN KEY (usuario_host) REFERENCES Usuario(username) ON DELETE CASCADE
);
