SELECT
dbo.operations.typeoperation,
dbo.operations.operation_id,
dbo.operations.dispo_operation,
dbo.operations.operation_qte,
dbo.operations.operation_reference,
dbo.operations.reference_fournisseur,
dbo.operations.operation_date,
dbo.operations.disponibilite,
dbo.fournisseurs.fournisseur_nom,
dbo.ressources.ressource_nom + ' ' + dbo.ressources.ressource_prenoms AS ressource_nom,
dbo.projets.projet_nom

FROM
dbo.operations
INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
  INNER JOIN dbo.fournisseurs ON dbo.operations.fournisseur_id = dbo.fournisseurs.fournisseur_id
  INNER JOIN dbo.ressources ON dbo.operations.ressource_id = dbo.ressources.ressource_id
WHERE
(dbo.operations.typeoperation = 'rec') AND
(dbo.operations.dispo_operation = 'true')