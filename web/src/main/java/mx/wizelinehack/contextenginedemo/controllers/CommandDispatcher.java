package mx.wizelinehack.contextenginedemo.controllers;

import mx.wizelinehack.contextenginedemo.dto.DocumentResponseDto;
import mx.wizelinehack.contextenginedemo.dto.NewDocumentRequestDto;
import mx.wizelinehack.contextenginedemo.services.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * The type Command dispatcher.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@RestController
@RequestMapping(value = "/demo")
public class CommandDispatcher {

    /**
     * The Document service.
     */
    @Autowired
    private DocumentService documentService;

    /**
     * List documents response entity.
     *
     * @return the response entity
     */
    @RequestMapping(
            produces = MediaType.APPLICATION_JSON_VALUE //,
            //headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<Iterable<DocumentResponseDto>> listDocuments() {
        return new ResponseEntity<>(documentService.list(), HttpStatus.OK);
    }

    /**
     * Save document response entity.
     *
     * @param document the document
     * @return the response entity
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    @PostMapping(
            headers = "Accept=" + MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<DocumentResponseDto> saveDocument(
            @Validated @RequestBody final NewDocumentRequestDto document)
            throws IOException, InterruptedException {

        final DocumentResponseDto result = documentService.save(document);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }
}
