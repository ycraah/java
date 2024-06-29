package com.ycraah.javaboard;

import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in); //스캐너
    int id = 0; //게시물 번호

    while(true){
      System.out.print("명령) ");
      String cmd = sc.nextLine();

      if(cmd.equalsIgnoreCase("write")){
        ++id;
        System.out.println("게시물 등록");
        System.out.print("제목) ");
        String title = sc.nextLine();

        System.out.print("내용) ");
        String content = sc.nextLine();

        System.out.printf("%d번 게시물이 등록되었습니다.\n", id);
      } else if(cmd.equalsIgnoreCase("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;
      }
      else {
        System.out.println("잘못된 명령어 입니다.");
      }
    }

    sc.close(); //스캐너 종료
    System.out.println("==프로그램 종료==");
  }
}