USE `hinario`;
INSERT INTO `irmao` VALUES (1,CURRENT_TIMESTAMP(),'Allan Jacques Neves de Oliveira','Developer',0);
INSERT INTO `usuario` VALUES (1,'allanjnofs@gmail.com','hvfkN/qlp/zhXR3cuerq6jd2Z7g=','hvfkN/qlp/zhXR3cuerq6jd2Z7g=',1);

INSERT INTO `configuracao` VALUES('email','allanjnofs@gmail.com');
INSERT INTO `configuracao` VALUES('email.aoEditar','true');
INSERT INTO `configuracao` VALUES('email.aoInserir','true');
INSERT INTO `configuracao` VALUES('email.debug','true');
INSERT INTO `configuracao` VALUES('email.nome','Hinario da Sã Doutrina');
INSERT INTO `configuracao` VALUES('email.senha','');
INSERT INTO `configuracao` VALUES('email.servico','true');
INSERT INTO `configuracao` VALUES('email.servico.frequenciaEmMinutos','1');
INSERT INTO `configuracao` VALUES('email.smtp.host','smtp.googlemail.com');
INSERT INTO `configuracao` VALUES('email.smtp.porta','465');
INSERT INTO `configuracao` VALUES('email.tipoSeguranca','SSL');
