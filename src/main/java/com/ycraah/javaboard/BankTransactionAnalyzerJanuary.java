package com.ycraah.javaboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class BankTransactionAnalyzerJanuary {
  private static final String RESOURCES = "src/main/resources/";
  public static void main(final String...args) throws IOException {
    final Path path = Paths.get(RESOURCES + args[0]);
    final List<String> lines = Files.readAllLines(path);
    double total = 0d;
    for(final String line: lines){ //예시 2024-01-30, -10, 교통비
      final String[] columns = line.split(",");
      final DateTimeFormatter formatted = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate date = LocalDate.parse(columns[0], formatted);
      if(date.getMonthValue() == 1){
        double amount = Double.parseDouble(columns[1]);
        total += amount;
      }
    }
    System.out.println("은행 입출금 내역의 총합은 " + total + "(만원)입니다.");
  }
}
