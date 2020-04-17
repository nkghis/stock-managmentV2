SELECT
dbo.operations.typeoperation,
dbo.operations.operation_id,
dbo.operations.operation_qte,
dbo.operations.operation_reference,
dbo.operations.operation_date,
dbo.projets.projet_nom,
dbo.motifs.motifs,
dbo.ressources.ressource_nom + ' ' + dbo.ressources.ressource_prenoms AS ressource_nom

FROM
dbo.operations
INNER JOIN dbo.projets ON dbo.operations.projet_id = dbo.projets.projet_id
INNER JOIN dbo.ressources ON dbo.operations.ressource_id = dbo.ressources.ressource_id
INNER JOIN dbo.motifs ON dbo.operations.motif_id = dbo.motifs.motif_id
WHERE
(dbo.operations.typeoperation = 'enl') AND
(dbo.operations.est_gache = 'false')