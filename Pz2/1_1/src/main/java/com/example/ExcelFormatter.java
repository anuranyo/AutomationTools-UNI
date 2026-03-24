package com.example;

import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.regex.Pattern;

public class ExcelFormatter {
    public static void main(String[] args) {
        // Директорії для вхідних та вихідних файлів
        File inputDir = new File("exel_files");
        File outputDir = new File("exel_changed");

        if (!inputDir.exists()) {
            System.out.println("Директорія 'exel_files' не існує або не знайдена.");
            return;
        }

        // Створюємо папку для вихідних файлів, якщо її ще немає
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File[] files = inputDir.listFiles((dir, name) -> name.endsWith(".xlsx") || name.endsWith(".xls"));
        if (files == null || files.length == 0) {
            System.out.println("В папці exel_files не знайдено жодного Excel файлу.");
            return;
        }

        // Регулярні вирази для перевірки наявності літер та цифр
        // Враховуємо латинські та кириличні(зокрема українські) літери
        Pattern letterPattern = Pattern.compile("[a-zA-Zа-яА-ЯіІїЇєЄґҐ]");
        Pattern digitPattern = Pattern.compile("\\d");

        for (File file : files) {
            System.out.println("Обробка файлу: " + file.getName());

            try (FileInputStream fis = new FileInputStream(file);
                    Workbook workbook = WorkbookFactory.create(fis)) {

                // Створюємо 4 стилі, як того вимагають умови

                // 1. Лише число – зелений фон
                CellStyle styleOnlyNumber = workbook.createCellStyle();
                styleOnlyNumber.setFillForegroundColor(IndexedColors.GREEN.getIndex());
                styleOnlyNumber.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 2. Лише літери – синій фон
                CellStyle styleOnlyLetter = workbook.createCellStyle();
                styleOnlyLetter.setFillForegroundColor(IndexedColors.BLUE.getIndex());
                styleOnlyLetter.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 3. І літери, і цифри – жовтий фон
                CellStyle styleBoth = workbook.createCellStyle();
                styleBoth.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
                styleBoth.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // 4. Ні літер, ні цифр – помаранчевий фон
                CellStyle styleNeither = workbook.createCellStyle();
                styleNeither.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
                styleNeither.setFillPattern(FillPatternType.SOLID_FOREGROUND);

                // Використовуємо DataFormatter для отримання вмісту комірки у вигляді тексту
                DataFormatter dataFormatter = new DataFormatter();

                // Проходимось по всіх аркушах, рядках і комірках
                for (Sheet sheet : workbook) {
                    for (Row row : sheet) {
                        for (Cell cell : row) {
                            String cellValue = dataFormatter.formatCellValue(cell);

                            boolean hasLetter = letterPattern.matcher(cellValue).find();
                            boolean hasDigit = digitPattern.matcher(cellValue).find();

                            // Застосовуємо стилі відповідно до умов
                            if (hasDigit && !hasLetter) {
                                // Лише число
                                cell.setCellStyle(styleOnlyNumber);
                            } else if (!hasDigit && hasLetter) {
                                // Лише літери
                                cell.setCellStyle(styleOnlyLetter);
                            } else if (hasDigit && hasLetter) {
                                // І літери, і цифри
                                cell.setCellStyle(styleBoth);
                            } else {
                                // Ні літер, ні цифр (або пуста комірка, або лише спецсимволи/пробіли)
                                cell.setCellStyle(styleNeither);
                            }
                        }
                    }
                }

                // Зберігаємо змінений файл з додаванням _changed до назви
                String origName = file.getName();
                int dotIndex = origName.lastIndexOf('.');
                String newName = (dotIndex == -1) 
                        ? origName + "_changed" 
                        : origName.substring(0, dotIndex) + "_changed" + origName.substring(dotIndex);
                
                File outputFile = new File(outputDir, newName);
                try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                    workbook.write(fos);
                }

                System.out.println("Успішно збережено в: " + outputFile.getPath());

            } catch (Exception e) {
                System.err.println("Помилка під час обробки файлу " + file.getName());
                e.printStackTrace();
            }
        }
        System.out.println("Готово!");
    }
}
