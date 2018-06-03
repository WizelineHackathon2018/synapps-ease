package mx.wizelinehack.contextenginedemo.services;

import mx.wizelinehack.contextenginedemo.dto.NewDocumentRequestDto;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.springframework.stereotype.Service;

/**
 * The type Word document creator service.
 *
 * @author Armando Montoya, created on: 6/2/18
 */
@Service
public class WordDocumentCreatorService {

    /**
     * The constant TITLE_FONT_SIZE.
     */
    public static final int TITLE_FONT_SIZE = 26;
    /**
     * The constant SUBTITLE_FONT_SIZE.
     */
    public static final int SUBTITLE_FONT_SIZE = 16;

    /**
     * Build document xwpf document.
     *
     * @param dto the dto
     * @return the xwpf document
     */
    public XWPFDocument buildDocument(final NewDocumentRequestDto dto) {
        XWPFDocument document = new XWPFDocument();

        // create document title
        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.LEFT);

        // wrap the title
        XWPFRun titleRun = title.createRun();
        titleRun.setText(dto.getSubject());
        titleRun.setColor("1F497D");
        titleRun.setFontFamily("Calibri");
        titleRun.setFontSize(TITLE_FONT_SIZE);

        // add a blank paragraph
        createParagraph(document, "");

        // add components subtitle
        createSubtitle(document, "Indicated components");

        // add components paragraph
        createParagraph(document, dto.getComponents());

        // add a blank paragraph
        createParagraph(document, "");

        // add process subtitle
        createSubtitle(document, "Indicated procedure");

        // add process paragraph
        createParagraph(document, dto.getProcess());

        return document;
    }

    /**
     * Create paragraph.
     *
     * @param document   the document
     * @param components the components
     */
    private void createParagraph(final XWPFDocument document,
                                 final String components) {

        XWPFParagraph componentsParagraph = document.createParagraph();
        componentsParagraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun componentsParagraphRun = componentsParagraph.createRun();
        componentsParagraphRun.setText(components);
    }

    /**
     * Create subtitle.
     *
     * @param document the document
     * @param text     the text
     */
    private void createSubtitle(final XWPFDocument document,
                                final String text) {
        XWPFParagraph subTitle = document.createParagraph();
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText(text);
        subTitleRun.setColor("4F81BD");
        subTitleRun.setFontFamily("Calibri");
        subTitleRun.setFontSize(SUBTITLE_FONT_SIZE);
    }

}
