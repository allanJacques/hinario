-- PARA CRIAR CHAVES QUE NAO SEI CRIAR COM O JPA 
ALTER TABLE `USUARIO` ADD UNIQUE INDEX `USUARIO_UNICO_IRMAO` (`IRMAO_ID`);
ALTER TABLE `CONSOLADOR` ADD UNIQUE INDEX `CONSOLADOR_UNICO_IRMAO` (`IRMAO_ID`);
ALTER TABLE `RECEBEDOR` ADD UNIQUE INDEX `RECEBEDOR_UNICO_IRMAO` (`IRMAO_ID`);
