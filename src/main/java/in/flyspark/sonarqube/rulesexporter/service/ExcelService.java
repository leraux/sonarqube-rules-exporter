package in.flyspark.sonarqube.rulesexporter.service;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import in.flyspark.sonarqube.rulesexporter.entities.Rule;
import in.flyspark.sonarqube.rulesexporter.util.Utils;

public class ExcelService {
	private static final Logger logger = LoggerFactory.getLogger(ExcelService.class.getSimpleName());

	private ExcelService() {
	}

	public static boolean exportExcel(Map<String, List<Rule>> rulesMap, String fileName) throws IOException {
		logger.debug("Exporting Excel");

		File file = new File(fileName);

		try (Workbook workbook = new XSSFWorkbook(); FileOutputStream fileOut = new FileOutputStream(file);) {

			CellStyle commonStyle = workbook.createCellStyle();
			commonStyle.setAlignment(HorizontalAlignment.CENTER);
			commonStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			commonStyle.setWrapText(true);
			setFont(workbook.createFont(), commonStyle, false, 11, IndexedColors.BLACK.getIndex());
			setBorder(commonStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, commonStyle, new Color(255, 255, 255), FillPatternType.SOLID_FOREGROUND);

			CellStyle ruletHeaderStyle = workbook.createCellStyle();
			ruletHeaderStyle.setAlignment(HorizontalAlignment.CENTER);
			ruletHeaderStyle.setVerticalAlignment(VerticalAlignment.CENTER);
			ruletHeaderStyle.setWrapText(true);
			setFont(workbook.createFont(), ruletHeaderStyle, true, 12, IndexedColors.WHITE.getIndex());
			setBorder(ruletHeaderStyle, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN, BorderStyle.THIN);
			setBGColor(workbook, ruletHeaderStyle, new Color(32, 55, 100), FillPatternType.SOLID_FOREGROUND);

			for (Map.Entry<String, List<Rule>> obj : rulesMap.entrySet()) {
				logger.debug("Language : {}", obj.getKey());
				Sheet sheet = workbook.createSheet(obj.getKey());

				Row ruleHeaderRow = sheet.createRow(0);

				String ruleColumns[] = { "#", "Language", "Rule Key", "Rule", "Issue Severity", "Issue Type", "Issue Category Tags/SysTags" };
				for (int i = 0; i < ruleColumns.length; i++) {
					Cell cell = ruleHeaderRow.createCell(i);
					cell.setCellValue(ruleColumns[i]);
					cell.setCellStyle(ruletHeaderStyle);
				}

				int ruleRowNumber = 1;
				int ruleColumnNumber = 0;
				for (Rule rule : obj.getValue()) {
					ruleColumnNumber = 0;
					Row ruleRow = sheet.createRow(ruleRowNumber);

					Cell cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(ruleRowNumber);
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(rule.getLang());
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(rule.getKey());
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(rule.getName());
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(rule.getSeverity());
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					cell.setCellValue(rule.getType());
					cell.setCellStyle(commonStyle);

					cell = ruleRow.createCell(ruleColumnNumber++);
					String sysTags = "";
					for (String s : rule.getSysTags()) {
						sysTags = s + "," + sysTags;
					}
					if (!Utils.isBlank(sysTags))
						sysTags = sysTags.substring(0, sysTags.length() - 1);

					cell.setCellValue(sysTags);
					cell.setCellStyle(commonStyle);

					ruleRowNumber++;
				}

				for (int i = 1; i < 10; i++) {
					sheet.autoSizeColumn(i);
				}
			}

			workbook.write(fileOut);
			return true;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	private static void setFont(Font font, CellStyle cellStyle, boolean isBold, int height, short color) {
		font.setBold(isBold);
		font.setFontHeightInPoints((short) height);
		font.setColor(color);
		cellStyle.setFont(font);
	}

	private static void setBGColor(Workbook workbook, CellStyle cellStyle, Color color, FillPatternType fp) {
		byte[] rgb = new byte[] { (byte) color.getRed(), (byte) color.getGreen(), (byte) color.getBlue() };
		if (cellStyle instanceof XSSFCellStyle) {
			XSSFCellStyle xssfreportHeaderCellStyle = (XSSFCellStyle) cellStyle;
			xssfreportHeaderCellStyle.setFillForegroundColor(new XSSFColor(rgb, null));
		} else if (cellStyle instanceof HSSFCellStyle) {
			cellStyle.setFillForegroundColor(HSSFColor.HSSFColorPredefined.LIME.getIndex());
			HSSFWorkbook hssfworkbook = (HSSFWorkbook) workbook;
			HSSFPalette palette = hssfworkbook.getCustomPalette();
			palette.setColorAtIndex(HSSFColor.HSSFColorPredefined.LIME.getIndex(), rgb[0], rgb[1], rgb[2]);
		}
		cellStyle.setFillPattern(fp);

	}

	private static void setBorder(CellStyle cellStyle, BorderStyle left, BorderStyle top, BorderStyle right, BorderStyle bottom) {
		cellStyle.setBorderLeft(left);
		cellStyle.setBorderTop(top);
		cellStyle.setBorderRight(right);
		cellStyle.setBorderBottom(bottom);
	}

}
