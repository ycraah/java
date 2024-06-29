package com.ycraah.javaboard;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in);

    while(true){
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equalsIgnoreCase("exit")) break;
    }

    sc.close();
    System.out.println("==프로그램 종료==");
  }
}