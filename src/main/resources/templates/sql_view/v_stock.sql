SELECT
dbo.clients.client_nom,
dbo.projets.projet_nom,
dbo.entrepots.entrepot_nom,
dbo.stocks.stock_quantite,
dbo.stocks.stock_id,
dbo.projets.seuil_projet

FROM
dbo.stocks
INNER JOIN dbo.projets ON dbo.stocks.projet_id = dbo.projets.projet_id
  INNER JOIN dbo.clients ON dbo.projets.client_id = dbo.clients.client_id
  INNER JOIN dbo.entrepots ON dbo.stocks.entrepot_id = dbo.entrepots.entrepot_id