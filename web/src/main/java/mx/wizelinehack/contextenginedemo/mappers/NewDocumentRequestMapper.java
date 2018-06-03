package mx.wizelinehack.contextenginedemo.mappers;

import mx.wizelinehack.contextenginedemo.domain.document.Document;
import mx.wizelinehack.contextenginedemo.dto.DocumentResponseDto;
import mx.wizelinehack.contextenginedemo.dto.NewDocumentRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * The interface New document request mapper.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@Mapper(componentModel = "spring")
public interface NewDocumentRequestMapper {

    /**
     * To document response dto.
     *
     * @param document the document
     * @return the document response dto
     */
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "subject", target = "subject"),
            @Mapping(source = "url", target = "link")
    })
    DocumentResponseDto to(Document document);

    /**
     * To entity document.
     *
     * @param dto the dto
     * @return the document
     */
    @Mappings({
            @Mapping(source = "subject", target = "subject")
    })
    Document toEntity(NewDocumentRequestDto dto);

    /**
     * To iterable.
     *
     * @param iterable the iterable
     * @return the iterable
     */
    Iterable<DocumentResponseDto> to(Iterable<Document> iterable);
}
