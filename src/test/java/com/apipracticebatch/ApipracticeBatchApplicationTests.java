package com.apipracticebatch;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootTest
class ApipracticeBatchApplicationTests {
@Autowired
ListService listService;

    @Autowired
    ListMapper listMapper;

    @Scheduled(cron = "* * * * * ?")
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
//                bufferedWriter.write(listDTO.getCount());
//                bufferedWriter.write("\n");
            }
            System.out.println("파일 생성완료");
        } catch (Exception e){
            System.out.println(e);
            System.out.println("오류 발생");
        }
    }

    @Test
    void contextLoads() {
        listService.create_csv();

    }

}
