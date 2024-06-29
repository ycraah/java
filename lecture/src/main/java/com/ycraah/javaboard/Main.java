package com.ycraah.javaboard;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in); //스캐너
    int id = 0; //게시물 번호
    int lastId = 0;
    List<Article> articles = new ArrayList<>(); //게시물 번호, 제목, 내용

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
        Article article = new Article(title, content, id); // 게시판 정보를 담은 article 형성
        articles.add(article); //article을 articles에 저장

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        System.out.println(article.toString());

        lastId = id;

      } else if(cmd.equalsIgnoreCase("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;

      } else if(cmd.equalsIgnoreCase("detail")){
        System.out.println("-게시물 상세보기-");

        Article lastArticle = articles.get(lastId-1); //lastId에 의거해서 마지막 게시판 정보 불러오기
        System.out.printf("번호 : %d \n제목 : %s \n내용 : %s\n", lastArticle.id, lastArticle.title, lastArticle.content);
      } else if(cmd.equalsIgnoreCase("list")){
        System.out.println("-게시물 리스트-");
        System.out.println("---------------");
        System.out.println("번호 / 제목");

        for(Article article : articles){ //articles에 저장된 article객체를 향상된 for문으로 불러내어 출력
          System.out.printf("%d / %s\n", article.id, article.title);
        }

        System.out.println("---------------");
      }

      else {
        System.out.println("잘못된 명령어 입니다.");
      }
    }

    sc.close(); //스캐너 종료
    System.out.println("==프로그램 종료==");
  }
}

class Article {
  String title;
  String content;
  int id;

  public Article(String title, String content, int id) {
    this.title = title;
    this.content = content;
    this.id = id;
  }

  @Override
  public String toString(){
    return String.format("[번호:%d, 제목:%s, 본문:%s]", this.id, this.title, this.content);
  }
}