USE [stock-managment]
GO

UPDATE [dbo].[app_user] SET email = 'admin@ics.ci' WHERE user_id = 1;
UPDATE [dbo].[app_user] SET email = 'charles.kouadio@ics.ci' WHERE user_id = 3;
UPDATE [dbo].[app_user] SET email = 'calixte.brye@ics.ci' WHERE user_id = 4;
UPDATE [dbo].[app_user] SET email = 'ghislain.nkagou@ics.ci' WHERE user_id = 5;
UPDATE [dbo].[app_user] SET email = 'ics@ics.ci' WHERE user_id = 6;
UPDATE [dbo].[app_user] SET email = 'max-olivier.ello@ics.ci' WHERE user_id = 7;
UPDATE [dbo].[app_user] SET email = 'abdoul.coulibaly@ics.ci' WHERE user_id = 8;
UPDATE [dbo].[app_user] SET email = 'jeannette.kacou@ics.ci' WHERE user_id = 9;
UPDATE [dbo].[app_user] SET email = 'alain.lekadou@ics.ci' WHERE user_id = 10;
UPDATE [dbo].[app_user] SET email = 'lanzeni.ouattara@ics.ci' WHERE user_id = 10010;
UPDATE [dbo].[app_user] SET email = 'yacou.cisse@ics.ci' WHERE user_id = 10011;
UPDATE [dbo].[app_user] SET email = 'mamadou.silue@ics.ci' WHERE user_id = 10012;
UPDATE [dbo].[app_user] SET email = 'eric-didier.gouan@ics.ci' WHERE user_id = 10013;
UPDATE [dbo].[app_user] SET email = 'akhadir.haidara@ics.ci' WHERE user_id = 20013;
UPDATE [dbo].[app_user] SET email = 'sylvain.diffi@ics.ci' WHERE user_id = 30013;
UPDATE [dbo].[app_user] SET email = 'denis.assemien@ics.ci' WHERE user_id = 30014;
UPDATE [dbo].[app_user] SET email = 'beranger.boa@ics.ci' WHERE user_id = 30015;
UPDATE [dbo].[app_user] SET email = 'aziz.coulibaly@ics.ci' WHERE user_id = 40015;

GO


USE [stock-managment]
GO

INSERT INTO [dbo].[app_role] ([role_name])VALUES ('ROLE_GESTIONNAIRESTOCK');
INSERT INTO [dbo].[app_role] ([role_name])VALUES ('ROLE_RESPONSABLESTOCK')
    GO
