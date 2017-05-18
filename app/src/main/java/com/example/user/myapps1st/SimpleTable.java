package com.example.user.myapps1st;

/**
 * Created by Suraj on 2/1/2017.
 */

import android.os.Environment;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SimpleTable {
    public static final String FONT = "resources/fonts/FreeSans.ttf";

    public void createPdf() throws IOException, DocumentException {
        Document document = new Document();
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/MyPortfolio";
        File dir = new File(path);
        if (!dir.exists()) {
            Log.e("abc", "abcHome");
            dir.mkdirs();
        }
        File file = new File(dir, "myportfolio.pdf");
        FileOutputStream fOut = new FileOutputStream(file);

        PdfWriter.getInstance(document, fOut);
        document.open();
        Rectangle rect= new Rectangle(document.getPageSize());
        rect.enableBorderSide(1);
        rect.enableBorderSide(2);
        rect.enableBorderSide(4);
        rect.enableBorderSide(8);
        rect.setBorder(rect.BOX);
        rect.setBorderWidth(5);
        rect.setBorderColor(BaseColor.RED);
        document.add(rect);
//        PdfPTable table = new PdfPTable(1);
//        table.setWidthPercentage(100);
//        table.addCell("helooo");
        PdfPTable table = new PdfPTable(2);
        table.setSpacingBefore(10);
        PdfPCell cell = new PdfPCell();
        cell.addElement(new Paragraph("G"));
        cell.addElement(new Paragraph("R"));
        cell.addElement(new Paragraph("P"));
        cell.setRowspan(7);
        table.addCell(cell);
        table.addCell("row 1");
        //table.addCell("row 2");
//        String IMG = "resources/drawable/contact.png";
//        Image img = Image.getInstance(IMG);
//        table.addCell(new PdfPCell(img, true));
        table.addCell("A light bulb icon");
        table.addCell(getNormalCell("helooo", null, 15));
        document.add(new Paragraph("Table with setSplitLate(false):"));
        table.setSplitLate(false);
        document.add(table);
        table.getDefaultCell().setBorder(0);//removing the table border
        document.add(table);
        document.close();
    }

    //For changing the text and size...
    public static PdfPCell getNormalCell(String string, String language, float size)
            throws DocumentException, IOException {
        if(string != null && "".equals(string)){
            return new PdfPCell();
        }
        Font f  = getFontForThisLanguage(language);
        if(size < 0) {
            f.setColor(BaseColor.RED);
            size = -size;
        }
        f.setSize(size);
        PdfPCell cell = new PdfPCell(new Phrase(string, f));
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        return cell;
    }
    public static Font getFontForThisLanguage(String language) {
        if ("czech".equals(language)) {
            return FontFactory.getFont(FONT, "Cp1250", true);
        }
        if ("greek".equals(language)) {
            return FontFactory.getFont(FONT, "Cp1253", true);
        }
        return FontFactory.getFont(FONT, null, true);
    }

}
