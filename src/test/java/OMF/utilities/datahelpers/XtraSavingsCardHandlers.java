package OMF.utilities.datahelpers;

import OMF.models.XtraSavingsCardModel;
import OMF.utilities.ExcelDataHandlers;
import OMF.utilities.FilePathHandler;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.ArrayList;
import java.util.Properties;

public class XtraSavingsCardHandlers {

    private static String resourceFolderPath = FilePathHandler.GetResourceFolderPath();
    private ExcelDataHandlers excelDataHandlers;
    private String cardWorkBookName = "prodXtraSavingsCards.xlsx";
    private Workbook workbook;

    public XtraSavingsCardHandlers() {
        excelDataHandlers = new ExcelDataHandlers();
    }

    public ArrayList<XtraSavingsCardModel> CreateArrayFromCardList(Workbook workbook, String cardPlatformSheet) throws Exception {
        ArrayList<XtraSavingsCardModel> cardList = new ArrayList<>();
        Sheet cardSheet = excelDataHandlers.GetExcelSheetFromWorkbook(workbook, cardPlatformSheet);
        int rowCount = cardSheet.getPhysicalNumberOfRows();
        for (int i = 1; i < rowCount; i++) {
            XtraSavingsCardModel singleCard = new XtraSavingsCardModel();
            singleCard.RowNumber = i;
            singleCard.CardNumber = excelDataHandlers.GetCellData("Card Number", i, cardSheet);
            singleCard.UsedCard = excelDataHandlers.GetCellData("Used Card", i, cardSheet);
            singleCard.LinkedUser = excelDataHandlers.GetCellData("Linked User", i, cardSheet);
            if (!singleCard.CardNumber.equalsIgnoreCase("")) cardList.add(singleCard);
        }
        return cardList;
    }

    public void WriteUpdateUsedCards(Workbook workbook) throws Exception {

            File cardWorkbookPath = new File(resourceFolderPath + cardWorkBookName);
            FileOutputStream outFile = new FileOutputStream(cardWorkbookPath);
            workbook.write(outFile);
            outFile.close();
    }

    public XtraSavingsCardModel GetNewCard(ArrayList<XtraSavingsCardModel> cardList) {
        XtraSavingsCardModel newCard = new XtraSavingsCardModel();
        for (XtraSavingsCardModel card : cardList) {
            if (card.UsedCard.equalsIgnoreCase("false")
                    && !card.CardNumber.equalsIgnoreCase("")
                    && card.LinkedUser.equalsIgnoreCase("")) {
                newCard = card;
                break;
            }
        }
        return newCard;
    }

    public Workbook GetCardWorkBook() throws Exception {
        Workbook cardWorkBook = excelDataHandlers.GetExcelWorkbook(cardWorkBookName);
        return cardWorkBook;
    }

    public Workbook UpdateWorkbookCardUsed(Workbook cardWorkbook, int rowNumber, String cardNumber, String usedCard, String linkedUser, String platformName) throws Exception {
        Sheet platformSheet = cardWorkbook.getSheet(platformName);
        String cellCardNumber = excelDataHandlers.GetCellData("Card Number", rowNumber, platformSheet);
        if (cardNumber.equals(cellCardNumber)) {
            workbook = excelDataHandlers.SetCellData("Used Card", rowNumber, platformName, usedCard, cardWorkbook);
            workbook = excelDataHandlers.SetCellData("Linked User", rowNumber, platformName, linkedUser, cardWorkbook);
        } else {
            throw new Exception("Card number does not match row to be updated");
        }
        return workbook;
    }

    public static Properties readPropertiesFile(String fileName) throws IOException {
        FileInputStream fis = null;
        Properties prop = null;
        try {
            fis = new FileInputStream(fileName);
            prop = new Properties();
            prop.load(fis);
        } catch(FileNotFoundException fnfe) {
            fnfe.printStackTrace();
        } catch(IOException ioe) {
            ioe.printStackTrace();
        } finally {
            fis.close();
        }
        return prop;
    }
}
