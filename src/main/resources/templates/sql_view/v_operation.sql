SELECT
dbo.clients.client_nom AS client,
dbo.projets.projet_nom as projet,
dbo.produits.produit_nom as produit,
dbo.emetteurs.emetteur_nom as emetteur,
dbo.operations.stock_initial AS initial,
dbo.operations.operation_qte as quantite,
dbo.operations.operation_date as date,
dbo.operations.typeoperation as operation,
dbo.gaches.gache_qte AS gache,
dbo.retours.retour_qte as retour,
dbo.operations.stock_final as final,
dbo.entrepots.entrepot_nom as entrepot
FROM
dbo.operations
  LEFT JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
  LEFT JOIN dbo.emetteurs ON dbo.projets.emetteur_id = dbo.emetteurs.emetteur_id
  LEFT JOIN dbo.clients ON dbo.projets.client_id = dbo.clients.client_id
  LEFT JOIN dbo.produits ON dbo.projets.produit_id = dbo.produits.produit_id
  LEFT JOIN dbo.retours ON dbo.retours.operation_id = dbo.operations.operation_id
  LEFT JOIN dbo.gaches ON dbo.gaches.operation_id = dbo.operations.operation_id
  INNER JOIN dbo.entrepots ON dbo.operations.entrepot_id = dbo.entrepots.entrepot_id
WHERE
dbo.operations.typeoperation = 'enl' OR
dbo.operations.typeoperation = 'dis'