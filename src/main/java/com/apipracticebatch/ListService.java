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

            bufferedWriter.write("행이름\n");
            for(ListDTO listDTO : listDTOS){
                bufferedWriter.write(listDTO.getLanguage());
                bufferedWriter.write("\n");
            }
            System.out.println("파일 생성완료");
        } catch (Exception e){
            System.out.println("오류 발생");
        }
    }
}
