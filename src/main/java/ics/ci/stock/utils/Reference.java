package ics.ci.stock.utils;

import ics.ci.stock.entity.Transfert;
import ics.ci.stock.repository.TransfertRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.Random;

public class Reference {


    final private TransfertRepository transfertRepository;

    public Reference(TransfertRepository transfertRepository) {
        this.transfertRepository = transfertRepository;
    }

    public static String getReferenceDemandeAppro(){
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

    public  String  getReferenceTransfert(){

        Transfert lastTransfert = transfertRepository.findTopByOrderByOperationIdDesc();
        LocalDate date = LocalDate.now();
        String masque = "TRA-";
        String d = date.toString().replaceAll("-","");
        String da = d.substring(2, d.length());
        if (lastTransfert == null)
        {
            int id = 1;
            return masque + da + "-" + id;
        }
        else {
            Long l = lastTransfert.getOperationId();
            int id = l.intValue()+1;
            return masque + da + "-" + id;
        }
    }
}
