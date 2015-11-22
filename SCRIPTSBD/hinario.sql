USE `hinario`;
INSERT INTO `IRMAO` VALUES (1,CURRENT_TIMESTAMP(),'Allan Jacques Neves de Oliveira','Developer',0);
INSERT INTO `USUARIO` VALUES (1,'allanjnofs@gmail.com','hvfkN/qlp/zhXR3cuerq6jd2Z7g=',1,'hvfkN/qlp/zhXR3cuerq6jd2Z7g=',1);
UPDATE `USUARIO` SET `SENHA` = 'hvfkN/qlp/zhXR3cuerq6jd2Z7g=' WHERE `ID` = 1;


INSERT INTO `CONFIGURACAO` VALUES('email','allanjnofs@gmail.com');
INSERT INTO `CONFIGURACAO` VALUES('email.aoEditar','false');
INSERT INTO `CONFIGURACAO` VALUES('email.aoInserir','false');
INSERT INTO `CONFIGURACAO` VALUES('email.debug','true');
INSERT INTO `CONFIGURACAO` VALUES('email.nome','Hinario da Sã Doutrina');
INSERT INTO `CONFIGURACAO` VALUES('email.senha','3037333034323039303037383130343131343130353131393038313037333031373031373037363038383030383030302B2B2D2D2B2B2B2B2B2D2B2D2B2D2D2B');
INSERT INTO `CONFIGURACAO` VALUES('email.servico','false');
INSERT INTO `CONFIGURACAO` VALUES('email.servico.frequenciaEmMinutos','1');
INSERT INTO `CONFIGURACAO` VALUES('email.smtp.host','smtp.googlemail.com');
INSERT INTO `CONFIGURACAO` VALUES('email.smtp.porta','465');
INSERT INTO `CONFIGURACAO` VALUES('email.tipoSeguranca','SSL');
INSERT INTO `CONFIGURACAO` VALUES('lista.CanticosRecebidosDias','90');
INSERT INTO `CONFIGURACAO` VALUES('lista.CanticosInseridosDias','30');
UPDATE `CONFIGURACAO` SET `VALOR` = 'false' WHERE `NOME` = 'email.servico';
UPDATE `CONFIGURACAO` SET `VALOR` = '60' WHERE `NOME` = 'email.serSvico.frequenciaEmMinutos';