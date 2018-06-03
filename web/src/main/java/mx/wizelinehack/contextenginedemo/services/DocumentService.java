package mx.wizelinehack.contextenginedemo.services;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;
import mx.wizelinehack.contextenginedemo.domain.document.Document;
import mx.wizelinehack.contextenginedemo.domain.document.DocumentRepo;
import mx.wizelinehack.contextenginedemo.dto.DocumentResponseDto;
import mx.wizelinehack.contextenginedemo.dto.NewDocumentRequestDto;
import mx.wizelinehack.contextenginedemo.mappers.NewDocumentRequestMapper;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * The type Document service.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@Service
public class DocumentService {

    /**
     * The Repo.
     */
    @Autowired
    private DocumentRepo repo;

    /**
     * The Word document creator service.
     */
    @Autowired
    private WordDocumentCreatorService wordDocumentCreatorService;

    /**
     * The Mapper.
     */
    @Autowired
    private NewDocumentRequestMapper mapper;

    /**
     * The Bucket name.
     */
    @Value("${app.s3.bucket}")
    private String bucketName;

    @Value("${app.s3.prefix}")
    private String s3Prefix;

    /**
     * The Transfer manager.
     */
    @Autowired
    private TransferManager transferManager;

    /**
     * List iterable.
     *
     * @return the iterable
     */
    public Iterable<DocumentResponseDto> list() {
        return mapper.to(repo.findAll());
    }

    /**
     * Save document response dto.
     *
     * @param documentRequest the document request
     * @return the document response dto
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    @Transactional
    public DocumentResponseDto save(final NewDocumentRequestDto documentRequest)
            throws IOException, InterruptedException {
        byte[] fileBytes = this.dumpToFileInTheFly(documentRequest);

        try (InputStream inputStream = new ByteArrayInputStream(fileBytes)) {
            String url = this.storeInAws(inputStream);

            final Document entity = mapper.toEntity(documentRequest);
            entity.setUrl(url);
            final DocumentResponseDto result =
                    mapper.to(repo.save(entity));
            return result;
        }
    }

    /**
     * Dump to file in the fly byte [ ].
     *
     * @param requestDto the request dto
     * @return the byte [ ]
     * @throws IOException the io exception
     */
    public byte[] dumpToFileInTheFly(
            final NewDocumentRequestDto requestDto) throws IOException {
        try (XWPFDocument result =
                     wordDocumentCreatorService.buildDocument(requestDto)) {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            result.write(outputStream);
            return outputStream.toByteArray();
        }
    }

    /**
     * Store file in aws.
     *
     * @param stream the stream
     * @return the file path url
     * @throws IOException          the io exception
     * @throws InterruptedException the interrupted exception
     */
    public String storeInAws(final InputStream stream) throws IOException, InterruptedException {
        final ObjectMetadata metadata = new ObjectMetadata();
        final String filePath =
                UUID.randomUUID().toString() + ".docx";

        metadata.setSSEAlgorithm(ObjectMetadata.AES_256_SERVER_SIDE_ENCRYPTION);
        metadata.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        metadata.setContentLength(stream.available());

        PutObjectRequest request =
                new PutObjectRequest(
                        bucketName,
                        filePath,
                        stream,
                        metadata
                );
        request.withCannedAcl(CannedAccessControlList.PublicRead);
        Upload upload = transferManager.upload(request);
        upload.waitForUploadResult();
        if (upload.isDone()) {
            return this.s3Prefix + filePath;
        }
        return null;
    }

}
