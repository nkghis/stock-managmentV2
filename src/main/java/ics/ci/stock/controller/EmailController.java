package ics.ci.stock.controller;

import ics.ci.stock.repository.ProduitRepository;
import ics.ci.stock.repository.VstockRepository;
import ics.ci.stock.repository.VsumStockByProduitRepository;
import ics.ci.stock.service.NotificationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;

public class EmailController {

    private Logger logger = LoggerFactory.getLogger(ics.ci.stock.controller.EmailController.class);
    @Autowired
    private NotificationService notificationService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private ProduitRepository produitRepository;

    @Autowired
    private VsumStockByProduitRepository vsumStockByProduitRepository;

    @Autowired
    private VstockRepository vstockRepository;

    private static String[] columns = new String[] { "PRODUIT", "PROJET", "CLIENT", "ENTREPOT", "QUANTITE" };
}
