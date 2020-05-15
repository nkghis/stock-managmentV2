SELECT
dbo.clients.client_nom,
dbo.projets.projet_nom,
dbo.produits.produit_nom,
dbo.emetteurs.emetteur_nom,
dbo.operations.typeoperation,
dbo.operations.operation_id,
dbo.operations.dispo_operation,
dbo.operations.operation_qte,
dbo.operations.operation_reference,
dbo.operations.reference_fournisseur,
dbo.operations.operation_date,
dbo.operations.disponibilite,
dbo.fournisseurs.fournisseur_nom,
dbo.ressources.ressource_nom + ' ' + dbo.ressources.ressource_prenoms AS ressource_nom


FROM
dbo.operations
INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
       INNER JOIN dbo.fournisseurs ON dbo.operations.fournisseur_id = dbo.fournisseurs.fournisseur_id
       INNER JOIN dbo.ressources ON dbo.operations.ressource_id = dbo.ressources.ressource_id
       INNER JOIN dbo.emetteurs ON dbo.projets.emetteur_id = dbo.emetteurs.emetteur_id
       INNER JOIN dbo.clients ON dbo.projets.client_id = dbo.clients.client_id
       INNER JOIN dbo.produits ON dbo.projets.produit_id = dbo.produits.produit_id
WHERE
(dbo.operations.typeoperation = 'rec')