-- Victor Cintra
INSERT INTO users (name,username,password) VALUES ('%nome%','%usuario%','%senha%')
-- Ruben de Pontes Gomes
-- userID = Identificação do Usuário
-- userTarget = Identificação do Usuário na qual enviará a mensagem
INSERT INTO messages (id_src,id_dst,msg) VALUES (userID,userTarget,'Mensagem')
-- Felipe Henrique Moura
-- userTarget = Identificação na qual o contato irá apontar
-- userOwner = Identificação do usuário na qual registrou o contato
INSERT INTO contact (id_target,id_owner)
VALUES
(userTarget,userOwner),
(userOwner,userTarget)