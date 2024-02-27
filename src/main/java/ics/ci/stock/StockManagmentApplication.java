package ics.ci.stock;


import ics.ci.stock.dto.gache.GacheProjetDTO;
import ics.ci.stock.entity.*;
import ics.ci.stock.repository.*;
import ics.ci.stock.service.*;
import ics.ci.stock.utils.EncrytedPasswordUtils;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
//Ajout annotation pour gerer les methodes asyncrhones
@EnableAsync
public class StockManagmentApplication {

	@Autowired
	private JavaMailSender javaMailSender;
	public static void main(String[] args) throws InterruptedException {


		ApplicationContext ctx = SpringApplication.run(StockManagmentApplication.class, args);


	/*	ValidationTransfertService service = ctx.getBean(ValidationTransfertService.class);

		String ss = service.getReference();

		System.out.println("reference  : " + ss);*/


/*		UserService userService = ctx.getBean(UserService.class);
		NotificationService notificationService = ctx.getBean(NotificationService.class);
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		UserRepository userRepository = ctx.getBean(UserRepository.class);


		AppUser user = userRepository.findByUserName("nkghis");
		Projet projet = projetRepository.getOne(1L);

		String subject = "test validation";
		String message = "test validation";


		String roleName = "ROLE_RESPONSABLESTOCK";

		String[] emails = userService.getEmails(roleName);

		notificationService.sendEmailValidation(subject, message, user, projet, emails);


		String s = "";*/

/*		StockService stockService = ctx.getBean(StockService.class);
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		Projet projet = projetRepository.getOne(270265L);
		List<Stock> stocks = stockService.getListStockByProjet(projet);
		int qte = stockService.totalStockByProjet(stocks);
		String s = "";*/

/*		NotificationService notificationService = ctx.getBean(NotificationService.class);
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user = userRepository.getOne(1L);
		Projet projet = projetRepository.getOne(1L);


		String subject = "Notification Stock | Seuil de disponibilité atteint";


		String message = "A tous" + System.lineSeparator() +
				"Le stock relatif au projet : OMIS emmagasiné a l'entrepôt : PERSO COFFRE-FORT a depassé le seuil de sécurité." + System.lineSeparator()+
				"  - Quantité seuil: 100 "  + System.lineSeparator() +
				"  - Stock disponible: 50 "  + System.lineSeparator() +
				System.lineSeparator() +
				"L'Administrateur" + System.lineSeparator() +
				System.lineSeparator() +
				"Ceci est un message generé automatiquement. Nous vous prions de ne pas repondre à ce message";

		notificationService.sendEmail( subject, message, user, projet);*/

		String d ="";

/*		StockService stockService = ctx.getBean(StockService.class);
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		EntrepotRepository entrepotRepository = ctx.getBean(EntrepotRepository.class);
		Entrepot entrepot = entrepotRepository.findByEntrepotNom("CP ICS STOCK");
		List<Entrepot> entrepots = new ArrayList<>();
		entrepots.add(entrepot);
		Projet projet = projetRepository.getOne(160133L);
		//List<Stock> stocks = stockService.getListStockByProjetWithoutEntrepot(entrepots, projet);
		List<Stock> stocks = stockService.getListStockByProjetWithoutEntrepot(projet);
		int qte = stockService.totalStockByProjet(projet);
		String n = "";*/


	/*	StockService stockService = ctx.getBean(StockService.class);
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		EntrepotRepository entrepotRepository = ctx.getBean(EntrepotRepository.class);
		GacheService gacheService = ctx.getBean(GacheService.class);
*/

		/*Projet projet = projetRepository.getOne(1L);
		Entrepot entrepot = entrepotRepository.getOne(1L);
		int qte = 286;

		Boolean test = stockService.checkIfStockAvailable(projet, entrepot, qte);

		String o = "";*/
		//UserRepository userRepository = ctx.getBean(UserRepository.class);
		//AppUser user = userRepository.getOne(1L);




		/*System.out.println("===============DEBUT TRANSACTION=======================");
		String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser userInventaire = userRepository.save(new AppUser("inventaire",password,true));
		System.out.println("===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleInventaire = roleRepository.save(new AppRole("ROLE_INVENTAIRE"));

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(userInventaire,roleInventaire));*/


		String server ="Server start on http://localhost:8089";
		System.out.println(server);
		/*String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user1 = userRepository.save(new AppUser("admin",password,true));
		AppUser user2 = userRepository.save(new AppUser("user",password,true));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(user1,roleadmin));
		userRoleRepository.save(new UserRole(user1,roleuser));
		userRoleRepository.save(new UserRole(user2,roleuser));

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");
		*/
		/*ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(ics.ci.stock.StockManagmentApplication.class, args);
		System.out.println("Sending Email...".toUpperCase());
		String pa = "azerty";
		String s = pa.toUpperCase();
		System.out.println(s);
*/

/*
	ApplicationContext ctx = SpringApplication.run(AuthentificationApplication.class, args);
		String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user1 = userRepository.save(new AppUser("admin",password,true));
		AppUser user2 = userRepository.save(new AppUser("user",password,true));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(user1,roleadmin));
		userRoleRepository.save(new UserRole(user1,roleuser));
		userRoleRepository.save(new UserRole(user2,roleuser));

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");*/

		/*//<editor-fold desc ="my app">

		String p = "123";
		String password = EncrytedPasswordUtils.encrytePassword(p);

		System.out.println("===============DEBUT TRANSACTION=======================");
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user1 = userRepository.save(new AppUser("admin",password,true));
		AppUser user2 = userRepository.save(new AppUser("user",password,true));
		System.out.println("===============AJOUT CLIENT=======================");
		userRepository.findAll().forEach(u->System.out.println(u.getUserName()));
		System.out.println("Utilisateurs ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT ROLE=======================");
		RoleRepository roleRepository = ctx.getBean(RoleRepository.class);
		AppRole roleadmin = roleRepository.save(new AppRole("ROLE_ADMIN"));
		AppRole roleuser = roleRepository.save(new AppRole("ROLE_USER"));
		roleRepository.findAll().forEach(u->System.out.println(u.getRoleName()));
		System.out.println("Roles ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AFFECTATION ROLE PAR USER=======================");
		UserRoleRepository userRoleRepository = ctx.getBean(UserRoleRepository.class);
		userRoleRepository.save(new UserRole(user1,roleadmin));
		userRoleRepository.save(new UserRole(user1,roleuser));
		userRoleRepository.save(new UserRole(user2,roleuser));

		System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&FIN DE LA TRANSACTION&&&&&&&&&&&&&&&&&&&");



		//ApplicationContext ctx = SpringApplication.run(AuthentificationApplication.class, args);
		ClientRepository clientRepository = ctx.getBean(ClientRepository.class);
		Client c1 = clientRepository.save(new Client("Société Générale","0000000"));
		Client c2 = clientRepository.save(new Client("BNI","02020202"));
		Client c3 = clientRepository.save(new Client("MUGEFCI","03030303"));
		System.out.println("===============AJOUT CLIENT=======================");
		clientRepository.findAll().forEach(v->System.out.println(v.getClient_nom()));
		System.out.println("Clients ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT PROJET=======================");
		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);

		Projet projet1 = projetRepository.save(new Projet("Assurance MUGEFCI", c3));
		Projet projet2 = projetRepository.save(new Projet("Prestige Personnalisation", c1));
		Projet projet3 = projetRepository.save(new Projet("Cartes Fonctionnaires", c2));
		Projet projet4 = projetRepository.save(new Projet("Olapi", c1));
		projetRepository.findAll().forEach(t->System.out.println(t.getProjetNom()));
		System.out.println("Projets ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT PRODUIT=======================");
		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		Produit p1 = produitRepository.save(new Produit("Papier A4",100));
		Produit p2 = produitRepository.save(new Produit("MIFAIRE 4K",100));
		Produit p3 = produitRepository.save(new Produit("MIFARE DESFire",100));
		Produit p4 = produitRepository.save(new Produit("Enveloppe",100));
		produitRepository.findAll().forEach(a->System.out.println(a.getProduit_nom()));
		System.out.println("Produits ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT CONDITIONNEMENT=======================");
		ConditionnementRepository conditionnementRepository = ctx.getBean(ConditionnementRepository.class);
		conditionnementRepository.save(new Conditionnement("Paquet Papier 500 ",500,p1));
		conditionnementRepository.save(new Conditionnement("Paquet Carte 100 ",100,p2));
		conditionnementRepository.save(new Conditionnement("Paquet Carte 100 ",100,p3));
		conditionnementRepository.save(new Conditionnement("Paquet Enveloppe 100 ",100,p4));
		conditionnementRepository.findAll().forEach(b->System.out.println(b.getConditionnement_nom()));
		System.out.println("Conditionnements ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT RESSOURCE=======================");
		RessourceRepository ressourceRepository = ctx.getBean(RessourceRepository.class);
		Ressource r1 = ressourceRepository.save(new Ressource("CALIXTE","ANDRE"));
		Ressource r2 = ressourceRepository.save(new Ressource("YEO","MARTIAL"));
		Ressource r3 =ressourceRepository.save(new Ressource("KOUAME","EDWIGE"));
		ressourceRepository.findAll().forEach(q->System.out.println(q.getRessource_nom()));
		System.out.println("Ressources ajoutées avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT FOURNISSEUR=======================");
		FournisseurRepository fournisseurRepository = ctx.getBean(FournisseurRepository.class);
		Fournisseur f1 = fournisseurRepository.save(new Fournisseur("DATACARD"));
		Fournisseur f2 = fournisseurRepository.save(new Fournisseur("RFID LAB"));
		Fournisseur f3 =fournisseurRepository.save(new Fournisseur("CARDALIS"));
		Fournisseur f4 =fournisseurRepository.save(new Fournisseur("LIBRAIRIE FRANCE"));

		fournisseurRepository.findAll().forEach(f->System.out.println(f.getFournisseur_nom()));
		System.out.println("fournisseur ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");

		System.out.println("===============AJOUT ENTREPOT=======================");
		EntrepotRepository entrepotRepository = ctx.getBean(EntrepotRepository.class);
		Entrepot e1 = entrepotRepository.save((new Entrepot(("PERSO COFFRE-FORT"))));
		Entrepot e2 = entrepotRepository.save((new Entrepot(("ICS PRODUCTION"))));
		Entrepot e3 = entrepotRepository.save((new Entrepot(("ICS SIEGE"))));
		entrepotRepository.findAll().forEach(r->System.out.println(r.getEntrepotNom()));
		System.out.println("Entrepot ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT MOTIFS=======================");
		MotifRepository motifRepository = ctx.getBean(MotifRepository.class);
		Motif m1 = motifRepository.save(new Motif("PRODUCTION"));
		Motif m2 = motifRepository.save(new Motif("TEST"));
		motifRepository.findAll().forEach(motif -> System.out.println(motif.getMotifNom()));
		System.out.println("Motif ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("===============AJOUT TYPE DE GACHE=======================");
		TypegacheRepository typegacheRepository = ctx.getBean(TypegacheRepository.class);
		Typegache g1 = typegacheRepository.save(new Typegache("FABRICATION"));
		Typegache g2 = typegacheRepository.save(new Typegache("PRODUCTION"));
		typegacheRepository.findAll().forEach((typegache -> System.out.println(typegache.getTypegacheNom())));
		System.out.println("Type de gache ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");



		//</editor-fold>*/
		//<editor-fold desc = Heritage>


		/*EnlevementRepository livraisonRepository = ctx.getBean(EnlevementRepository.class);
		livraisonRepository.save(new Enlevement(10,dateTime,true,projet1,p1,user,5));

		System.out.println("Enlevement ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/

		//<editor-fold desc = ADD RECEPTION>
		/*ApplicationContext ctx = SpringApplication.run(AuthentificationApplication.class, args);
		ClientRepository clientRepository = ctx.getBean(ClientRepository.class);
		Client c1 = clientRepository.save(new Client("Orange","0000000"));

		ProjetRepository projetRepository = ctx.getBean(ProjetRepository.class);
		Projet projet1 = projetRepository.save(new Projet("Embossage", c1));

		ProduitRepository produitRepository = ctx.getBean(ProduitRepository.class);
		Produit p1 = produitRepository.save(new Produit("Papier A3"));

		RessourceRepository ressourceRepository = ctx.getBean(RessourceRepository.class);
		Ressource r1 = ressourceRepository.save(new Ressource("ABDOUL","COULIBALY"));

		FournisseurRepository fournisseurRepository = ctx.getBean(FournisseurRepository.class);
		Fournisseur f1 = fournisseurRepository.save(new Fournisseur("OKI"));


		LocalDateTime dateTime = LocalDateTime.now();
		UserRepository userRepository = ctx.getBean(UserRepository.class);
		AppUser user = userRepository.findByUserName("user");

		String ref = "REC-11072019-1";
		String refFournisseur = "test1";
		int dispo = 19;

		ReceptionRepository receptionRepository = ctx.getBean(ReceptionRepository.class);
		receptionRepository.save(new Reception(ref,refFournisseur,19,dateTime,true,projet1,p1,user,dispo,f1,r1));
		System.out.println("Reception ajoutés avec succes");
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/
		//</editor-fold>
		//</editor-fold>


		/*ReceptionRepository receptionRepository = context.getBean(ReceptionRepository.class);
		Reception r1 = receptionRepository.findFirstByOrderByOperation_idDesc();*/

		/*@Query(value = "SELECT TOP 1\n" +
				"dbo.operations.typeoperation,\n" +
				"dbo.operations.operation_id,\n" +
				"dbo.operations.dispo_operation,\n" +
				"dbo.operations.operation_date,\n" +
				"dbo.operations.operation_qte,\n" +
				"dbo.operations.produit_id,\n" +
				"dbo.operations.projet_id,\n" +
				"dbo.operations.appuser_id,\n" +
				"dbo.operations.fournisseur_id,\n" +
				"dbo.operations.ressource_id\n" +
				"\n" +
				"FROM\n" +
				"dbo.operations\n" +
				"WHERE\n" +
				"dbo.operations.typeoperation = 'rec'\n" +
				"ORDER BY\n" +
				"dbo.operations.operation_id DESC\n")
				Operation operation();*/
		/*System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println(r1.getOperationId());
		System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++");*/


	}


}
