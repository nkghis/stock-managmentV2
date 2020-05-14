SELECT
dbo.projets.projet_nom,
dbo.operations.operation_date,
dbo.operations.quantite_restante,
dbo.operations.quantite_verse,
dbo.operations.est_livrable,
dbo.entrepots.entrepot_nom,
dbo.operations.operation_reference,
dbo.operations.operation_id

FROM
dbo.operations
INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
  INNER JOIN dbo.entrepots ON dbo.operations.entrepot_id = dbo.entrepots.entrepot_id
WHERE
(dbo.operations.est_livrable = 'true') AND
dbo.operations.typeoperation = 'dis'