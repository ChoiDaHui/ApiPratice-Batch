package com.apipracticebatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import  java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ListService {
    @Autowired
    ListMapper listMapper;

    @Scheduled(cron = "* 40 5 * * ?")
    public void create_csv(){
        List<ListDTO> listDTOS = listMapper.read_data();
        LocalDateTime localDateTime = LocalDateTime.now();
        String dateTime = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmm"));
        try(
                FileOutputStream fileOutputStream = new FileOutputStream(dateTime+"_통계.csv");
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, "MS949");
                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        ){
            long english = listDTOS.stream().filter(listDTO -> {
                return listDTO.getLanguage().contains("영어");
            }).count();

            long japen = listDTOS.stream().filter(listDTO -> {
                return listDTO.getLanguage().contains("일본어");
            }).count();

            long china = listDTOS.stream().filter(listDTO -> {
                return listDTO.getLanguage().contains("중국어");
            }).count();

            bufferedWriter.write("외국어,총조회수\n");
            bufferedWriter.write("영어," + english+"\n");
            bufferedWriter.write("일본어," + japen + "\n");
            bufferedWriter.write("중국어," + china + "\n");

            System.out.println("파일 생성완료");
        } catch (Exception e){
            System.out.println("오류 발생");
        }
    }
}
