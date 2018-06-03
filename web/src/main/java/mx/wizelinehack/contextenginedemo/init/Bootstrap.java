package mx.wizelinehack.contextenginedemo.init;

import mx.wizelinehack.contextenginedemo.domain.document.Document;
import mx.wizelinehack.contextenginedemo.services.DocumentService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Armando Montoya,
 * created on: 6/2/18
 */
@Service
public class Bootstrap implements InitializingBean {

    @Autowired
    private DocumentService documentService;

    @Override
    public void afterPropertiesSet() throws Exception {

        // create a document in the DB
        /*final Document document = new Document();
        document.setSubject("First subject");
        documentService.save(document);*/


    }
}
