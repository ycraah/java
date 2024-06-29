package com.ycraah.javaboard;

import jdk.jshell.execution.Util;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Main {
  public static void main(String[] args) {
    System.out.println("==프로그램 시작==");
    Scanner sc = new Scanner(System.in);
    List<Article> articles = new ArrayList<>(); //게시물 번호, 제목, 내용를 담은 객체 articles

    IntStream.rangeClosed(1, 3).forEach(
        i -> articles.add(new Article("테스트 제목" + i, "테스트 내용" + i, i))); //test용 데이터

    while(true){ //명령어 무한 입력을 위한 반복문
      System.out.print("명령) ");
      String cmd = sc.nextLine();
      Rq rq = new Rq(cmd); //Url 파싱을 위한 Rq 객체 형성

      if(rq.getUrlPath().equalsIgnoreCase("write")){
        System.out.println("게시물 등록");
        System.out.print("제목) ");
        String title = sc.nextLine();

        if(title.trim().isEmpty()){
          System.out.println("제목을 입력해주세요.");
          continue;
        }

        System.out.print("내용) ");
        String content = sc.nextLine();

        if(content.trim().isEmpty()){
          System.out.println("내용을 입력해주세요.");
          continue;
        }

        int id = articles.size() + 1; //articles의 배열 길이 + 1을 하여 게시물 번호로 지정
        Article article = new Article(title, content, id); // 게시판 정보를 담은 article 형성
        articles.add(article); //articles에 article을 저장

        System.out.printf("%d번 게시물이 등록되었습니다.\n", article.id);
        System.out.println(article.toString());

      } else if(rq.getUrlPath().equalsIgnoreCase("detail")){
        if(articles.isEmpty()){
          System.out.println("게시물이 존재하지 않습니다.");
          continue;
        }

        System.out.println("-게시물 상세보기-");
        int lastId = articles.size() + 1; //최근 게시물 번호
        Article lastArticle = articles.get(lastId-1); //lastId에 의거해서 마지막 게시물 정보 불러오기
        System.out.printf("번호 : %d \n제목 : %s \n내용 : %s\n", lastArticle.id, lastArticle.title, lastArticle.content);

      } else if(rq.getUrlPath().equalsIgnoreCase("list")){
        if(articles.isEmpty()){
          System.out.println("게시물이 없습니다.");
          continue;
        }

        System.out.println("-게시물 리스트-");
        System.out.println("---------------");
        System.out.println("번호 / 제목");

       /* for(Article article : articles) {//articles에 저장된 내용을 article객체에 저장하여 for문으로 불러내어 출력
          System.out.printf("%d / %s\n", article.id, article.title);
        } 순차 출력*/
        for(int i = articles.size()-1; i >= 0; i--) { //Arraylist는 배열 길이를 size()로 확인!!
          Article article = articles.get(i);
          System.out.printf("%d / %s\n", article.id, article.title); //역순 출력
        }
        System.out.println("---------------");

      } else if(rq.getUrlPath().equalsIgnoreCase("exit")) {
        System.out.println("프로그램을 종료합니다.");
        break;

      } else {
        System.out.println("잘못된 명령어 입니다.");
      }
    }//end of while

    sc.close(); //스캐너 종료
    System.out.println("==프로그램 종료==");
  } //end of main
} //end of Main

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

class Rq {
  String url;   ///URL의 구성 : protocol://Domain/Path?Parameter(or QueryString)#Fragment
  Map<String, String> params;
  String urlPath;

  public Rq(String url) {
    this.url = url; // ex) datail?page=2&searchKeyword=안녕? 나는
    params = Util.getParamsFromUrl(this.url); //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
    urlPath = Util.getUrlPathFromUrl(this.url); //detail
  }

  public String getUrlPath(){ //detail
    return Util.getUrlPathFromUrl(url);
  }

  public Map<String, String> getParams(){ //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
    return Util.getParamsFromUrl(url);
  }
  class Util{
    static String getUrlPathFromUrl(String url){ // ex) datail?page=2&searchKeyword=안녕? 나는
      return url.split("\\?", 2)[0]; //detail
    }

    static Map<String, String> getParamsFromUrl(String url){
      Map<String, String> params = new HashMap<>();
      String[] urlBits = url.split("\\?", 2); //[0]detail [1]page=2&searchKeyword=안녕? 나는

      if(urlBits.length == 1){
        return params;
      }

      String queryStr = urlBits[1]; //page=2&searchKeyword=안녕? 나는
      for(String bit : queryStr.split("&")){ //[0]page=2 [1]searchKeyword=안녕? 나는
        String[] bits = bit.split("=", 2); //[0]page [1]2 //[0]searchKeyword [1]안녕? 나는

        if(bits.length == 1){
          continue;
        }
        params.put(bits[0], bits[1]); //(page, 2)를 세트로 묶어서 params에 저장
      }
      return params; //[0] (page, 2) [1] (searchKeyword, 안녕? 나는)
    }
  }
}

