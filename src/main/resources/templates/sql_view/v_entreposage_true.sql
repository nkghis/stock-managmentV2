SELECT
dbo.projets.projet_nom,
dbo.operations.operation_id,
dbo.entreposages.entreposage_id,
dbo.entreposages.entreposage_date,
dbo.entreposages.entreposage_reference,
dbo.entreposages.est_livrable,
dbo.entreposages.quantite_restante,
dbo.entreposages.quantite_verse,
dbo.entrepots.entrepot_nom

FROM
dbo.operations
INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
INNER JOIN dbo.entreposages ON dbo.entreposages.operation_id = dbo.operations.operation_id
INNER JOIN dbo.entrepots ON dbo.entreposages.entrepot_id = dbo.entrepots.entrepot_id
WHERE
(dbo.entreposages.est_livrable = 'true')