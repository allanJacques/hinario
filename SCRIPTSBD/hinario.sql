USE `hinario`;
INSERT INTO `irmao` VALUES (1,CURRENT_TIMESTAMP(),'Allan Jacques Neves de Oliveira','Developer',0);
INSERT INTO `usuario` VALUES (1,'allanjnofs@gmail.com','hvfkN/qlp/zhXR3cuerq6jd2Z7g=','hvfkN/qlp/zhXR3cuerq6jd2Z7g=',1);

INSERT INTO `configuracao` VALUES('email','allanjnofs@gmail.com');
INSERT INTO `configuracao` VALUES('email.aoEditar','false');
INSERT INTO `configuracao` VALUES('email.aoInserir','false');
INSERT INTO `configuracao` VALUES('email.debug','true');
INSERT INTO `configuracao` VALUES('email.nome','Hinario da Sã Doutrina');
INSERT INTO `configuracao` VALUES('email.senha','3037333034323039303037383130343131343130353131393038313037333031373031373037363038383030383030302B2B2D2D2B2B2B2B2B2D2B2D2B2D2D2B');
INSERT INTO `configuracao` VALUES('email.servico','false');
INSERT INTO `configuracao` VALUES('email.servico.frequenciaEmMinutos','1');
INSERT INTO `configuracao` VALUES('email.smtp.host','smtp.googlemail.com');
INSERT INTO `configuracao` VALUES('email.smtp.porta','465');
INSERT INTO `configuracao` VALUES('email.tipoSeguranca','SSL');
UPDATE `configuracao` SET VALOR = 'false' WHERE NOME = 'email.servico';
UPDATE `configuracao` SET VALOR = '60' WHERE NOME = 'email.servico.frequenciaEmMinutos';