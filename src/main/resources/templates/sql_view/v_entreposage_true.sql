SELECT
dbo.clients.client_nom,
dbo.projets.projet_nom,
dbo.produits.produit_nom,
dbo.emetteurs.emetteur_nom,
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
  INNER JOIN dbo.produits ON dbo.projets.produit_id = dbo.produits.produit_id
  INNER JOIN dbo.emetteurs ON dbo.projets.emetteur_id = dbo.emetteurs.emetteur_id
  INNER JOIN dbo.clients ON dbo.projets.client_id = dbo.clients.client_id
WHERE
(dbo.operations.est_livrable = 'true') AND
dbo.operations.typeoperation = 'dis'