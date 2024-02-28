package ics.ci.stock.service.impl;

import ics.ci.stock.dto.validationtransfert.ValidationTransfertResponse;
import ics.ci.stock.entity.AppUser;
import ics.ci.stock.entity.Transfert;
import ics.ci.stock.entity.ValidationTransfert;
import ics.ci.stock.enums.Statut;
import ics.ci.stock.repository.ValidationTransfertRepository;
import ics.ci.stock.service.ValidationTransfertService;
import ics.ci.stock.utils.DateConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@Transactional
public class ValidationTransfertServiceImpl implements ValidationTransfertService {


    private final ValidationTransfertRepository validationTransfertRepository;

    public ValidationTransfertServiceImpl(ValidationTransfertRepository validationTransfertRepository) {
        this.validationTransfertRepository = validationTransfertRepository;
    }

    @Override
    public List<ValidationTransfertResponse> all() {
        return this.TODTO(validationTransfertRepository.findAll());
    }

    @Override
    public List<ValidationTransfertResponse> allByStatut(Statut statut) {

        return this.TODTO(this.findByStatut(statut));
    }

    public ValidationTransfertResponse toDTO(ValidationTransfert validationTransfert){

        ValidationTransfertResponse v = new ValidationTransfertResponse();
        v.setDateSaisie(DateConvert.getStringDate(validationTransfert.getDateSaisie()));
        v.setId(validationTransfert.getId());
        v.setEntrepotSource(validationTransfert.getEntrepotSource().getEntrepotNom());
        v.setEntrepotDestination(validationTransfert.getEntrepotDestination().getEntrepotNom());
        v.setProjet(validationTransfert.getProjet().getProjetNom());
        v.setQte(validationTransfert.getTransfertQte());
/*      v.setStockInitialSource(validationTransfert.getStockInitialSource());
        v.setStockInitialDestination(validationTransfert.getStockInitialDestination());
        v.setStockFinalSource(validationTransfert.getStockFinalSource());
        v.setStockFinalDestination(validationTransfert.getStockFinalDestination());*/
        v.setStatut(validationTransfert.getStatut().name());
        v.setUserName(validationTransfert.getUser().toNomComplet());
        return v;
    }

    @Override
    public List<ValidationTransfertResponse> TODTO(List<ValidationTransfert> validationTransferts) {

        List<ValidationTransfertResponse> DTO = new ArrayList<>();
        for (ValidationTransfert validationTransfert : validationTransferts){
            ValidationTransfertResponse dto = this.toDTO(validationTransfert);
            DTO.add(dto);
        }
        return DTO;
    }

    @Override
    public ValidationTransfert createValidationTransfert(Transfert transfert, AppUser user) {

        ValidationTransfert validationTransfert = new ValidationTransfert();
        validationTransfert.setReference(this.getReference());
        validationTransfert.setDateSaisie(transfert.getOperationDateSaisie());
        validationTransfert.setEntrepotSource(transfert.getEntrepotSource());
        validationTransfert.setEntrepotDestination(transfert.getEntrepotDestination());
        validationTransfert.setEntreposer(transfert.getEntreposer());
        validationTransfert.setProjet(transfert.getProjet());
        validationTransfert.setTransfertQte(transfert.getOperation_qte());
       /* validationTransfert.setStockInitialSource(transfert.getStockInitialSource());
        validationTransfert.setStockInitialDestination(transfert.getStockInitialDestination());
        validationTransfert.setStockFinalSource(transfert.getStockFinalSource());
        validationTransfert.setStockFinalDestination(transfert.getStockFinalDestination());*/
        validationTransfert.setStatut(Statut.EN_COURS);
        validationTransfert.setUser(user);
        return validationTransfertRepository.save(validationTransfert);
        //return this.toDTO(validationTransfertRepository.save(validationTransfert));
    }

    @Override
    public ValidationTransfert transfertToValidation(Transfert transfert) {

        ValidationTransfert validationTransfert = new ValidationTransfert();
        validationTransfert.setReference(this.getReference());
        validationTransfert.setDateSaisie(transfert.getOperationDateSaisie());
        validationTransfert.setEntrepotSource(transfert.getEntrepotSource());
        validationTransfert.setEntrepotDestination(transfert.getEntrepotDestination());
        validationTransfert.setEntreposer(transfert.getEntreposer());
        validationTransfert.setProjet(transfert.getProjet());
        validationTransfert.setTransfertQte(transfert.getOperation_qte());
        /*validationTransfert.setStockInitialSource(transfert.getStockInitialSource());
        validationTransfert.setStockInitialDestination(transfert.getStockInitialDestination());
        validationTransfert.setStockFinalSource(transfert.getStockFinalSource());
        validationTransfert.setStockFinalDestination(transfert.getStockFinalDestination());*/
        //validationTransfert.setStatut(Statut.EN_COURS);
        return validationTransfert;
    }

    @Override
    public ValidationTransfert byId(Long id) {

        return validationTransfertRepository.getOne(id);
    }

    @Override
    public void updateStatutValidationTransfert(ValidationTransfert validationTransfert, Statut statut) {
        validationTransfert.setStatut(statut);
        validationTransfertRepository.save(validationTransfert);
    }

    @Override
    public List<ValidationTransfert> findByStatut(Statut statut) {
        return validationTransfertRepository.findByStatut(statut);
    }

    @Override
    public boolean isPresent(Long id) {
        return validationTransfertRepository.findById(id).isPresent();
    }

    @Override
    public String getReference() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();
       return  random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

    }
}
