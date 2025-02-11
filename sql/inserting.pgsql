INSERT INTO Nivel (classificacao, minimo_pontos, descricao) VALUES
('Iniciante', 0, 'Nadador iniciante, aprendendo os fundamentos básicos da natação.'),
('Intermediário', 500, 'Nadador com habilidades básicas, capaz de nadar diferentes estilos com técnica razoável.'),
('Avançado', 1500, 'Nadador experiente, com boa resistência e técnica refinada.'),
('Competitivo', 3000, 'Nadador treinado para competições, com alto desempenho e estratégia de prova.'),
('Elite', 6000, 'Nadador de alto nível, com excelente condicionamento físico e técnica apurada.'),
('Master', 10000, 'Nadador veterano com vasta experiência e participação em competições de alto nível.');

SELECT * FROM Nivel;

INSERT INTO Tem_Amizade(usuario1, usuario2, data_inicio) VALUES
('ikuyorih9', 'brunakitty', '2025-02-10');
INSERT INTO Tem_Amizade(usuario1, usuario2, data_inicio) VALUES
('brunakitty', 'ikuyorih9', '2025-02-10');

SELECT * FROM Usuario;