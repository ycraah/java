package com.ycraah.javaboard;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class BankTransactionAnalyzerSimple {
  private static final String RESOURCES = "src/main/resources/";
  public static void main(final String...args) throws IOException {
    final Path path = Paths.get(RESOURCES + args[0]);
    final List<String> lines = Files.readAllLines(path);
    double total = 0d;
    for(final String line: lines){ //예시 2024-01-30, -10, 교통비
      final String[] columns = line.split(",");
      final double amount = Double.parseDouble(columns[1]);
      total += amount;
    }
    System.out.println("은행 입출금 내역의 총합은 " + total + "(만원)입니다.");
  }
}