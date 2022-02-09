USE [stock-managment]
GO

UPDATE [dbo].[operations]
SET
[transfert_dispo] = 0
WHERE entrepot_id = 1 and typeoperation = 'dis'
GO


-----------Update dispoTransfert where entrepot not perso coffre fort------------------------------------------

UPDATE operations
SET  transfert_dispo = mesoperations.operation_qte

FROM (
SELECT operation_id, typeoperation, entrepot_id, operation_qte
FROM operations where typeoperation = 'dis' and entrepot_id NOT IN (1)) AS mesoperations
WHERE
mesoperations.operation_id = operations.operation_id

----------------------------------Selet all ------------------------------------

select * from operations where typeoperation = 'dis' and entrepot_id NOT IN (1);