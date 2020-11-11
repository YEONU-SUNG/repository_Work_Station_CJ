package com.neo.visitor.util;

import org.apache.poi.ss.usermodel.*;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by KaSha on 2018. 9. 10..
 */
public class ExcelDownload extends AbstractXlsxView {

    @Override
    protected void buildExcelDocument(Map<String, Object> modelMap, Workbook workbook, HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        String title = modelMap.containsKey("title") ? (String) modelMap.get("title") : "전체";
        System.out.println(title);
        // 새로운 sheet를 생성한다.
        Sheet worksheet = workbook.createSheet(title);
        Row row = worksheet.createRow(0);   // 가장 첫번째 줄에 제목을 만든다.

        String[][] excelData = modelMap.containsKey("excelData") ? (String[][]) modelMap.get("excelData") : null;
        int[] columnLength = modelMap.containsKey("columnLength") ? (int[]) modelMap.get("columnLength") : null;
        if(excelData == null) {
            return;
        }

        if(columnLength != null) {  // 칼럼 길이 설정
            int columnIndex = 0;
            for(int length : columnLength) {
                worksheet.setColumnWidth(columnIndex, length);
                columnIndex++;
            }
        }

        CellStyle headerStyle = getHeaderStyle(workbook), bodyStyle = getBodyStyle(workbook);
        for(int i=0; i < excelData.length; i++) {
            row = worksheet.createRow(i);
            for(int j=0; j<excelData[i].length; j++) {
                row.createCell(j).setCellValue(excelData[i][j]);
                row.getCell(j).setCellType(CellType.STRING);
                row.getCell(j).setCellStyle(i == 0 ? headerStyle : bodyStyle);
            }
        }

        // 셀 병합 CellRangeAddress(시작 행, 끝 행, 시작 열, 끝 열)
//        worksheet.addMergedRegion(
//                new CellRangeAddress(listExcel.size() + 1, listExcel.size() + 1, 0, 6));

        // 병합 테스트를 위한 설정
//        row = worksheet.createRow(listExcel.size() + 1);
//        row.createCell(0).setCellValue("셀 병합 테스트");
//        row.getCell(0).setCellStyle(style); // 지정한 스타일을 입혀준다.

        try {
            String excelName = title+".xlsx";
            response.setHeader("Content-Disposition", "attachement; filename=\""
                    + java.net.URLEncoder.encode(excelName, "UTF-8") + "\";charset=\"UTF-8\"");
            // 엑셀파일명 한글깨짐 조치
            response.setHeader("Content-Type", "application/octet-stream");
            response.setHeader("Content-Transfer-Encoding", "binary;");
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private CellStyle getHeaderStyle(Workbook workbook) {
        // 폰트
        Font headFont = workbook.createFont();
        headFont.setFontHeightInPoints((short) 13);
        headFont.setFontName("돋움");
        headFont.setBold(true);

        // 셀 스타일
        CellStyle headStyle = workbook.createCellStyle();
        headStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.index);
        headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        headStyle.setAlignment(HorizontalAlignment.CENTER); // 글 위치를 중앙으로 설정
        headStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headStyle.setFont(headFont);
        headStyle.setBorderBottom(BorderStyle.THIN);
        headStyle.setBorderLeft(BorderStyle.THIN);
        headStyle.setBorderRight(BorderStyle.THIN);
        headStyle.setBorderTop(BorderStyle.THIN);

        return headStyle;
    }

    private CellStyle getBodyStyle(Workbook workbook) {
        // 폰트
        Font bodyFont = workbook.createFont();
        bodyFont.setFontHeightInPoints((short) 12);
        bodyFont.setFontName("돋움");

        // 셀 스타일
        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setFont(bodyFont);
        bodyStyle.setWrapText(true);
        bodyStyle.setAlignment(HorizontalAlignment.CENTER); // 글 위치를 중앙으로 설정
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setBorderBottom(BorderStyle.THIN);
        bodyStyle.setBorderLeft(BorderStyle.THIN);
        bodyStyle.setBorderRight(BorderStyle.THIN);
        bodyStyle.setBorderTop(BorderStyle.THIN);

        return bodyStyle;
    }
}

