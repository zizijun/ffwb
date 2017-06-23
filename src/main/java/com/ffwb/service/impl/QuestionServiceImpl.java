package com.ffwb.service.impl;

import com.ffwb.dao.ManagerDao;
import com.ffwb.dao.QuestionDao;
import com.ffwb.entity.Manager;
import com.ffwb.entity.Question;
import com.ffwb.service.QuestionService;
import com.ffwb.utils.ExcelReader;
import com.ffwb.utils.JsonType;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jinchuyang on 2017/6/22.
 */
@Service("QuestionService")
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private ManagerDao managerDao;
    /**
     * 上传试卷 试题
     * @param multipartFile
     * @param managerId
     * @return
     */
    @Override
    public int upload(MultipartFile multipartFile, Long managerId) throws IOException {
        Manager manager = managerDao.findOne(managerId);
        if (manager == null){
            return -1;
        }
        List<Question> questions = new ArrayList<Question>();
        File file = File.createTempFile("./doc","temp");
        multipartFile.transferTo(file);
        ExcelReader excelReader = new ExcelReader(file);
        XSSFSheet sheet = excelReader.getWb().getSheetAt(0);
        //获得当前sheet的结束行
        int lastRowNum = sheet.getLastRowNum();
        for (int i = 1; i < lastRowNum; i++){
            Question question = new Question();
            question.setManager(manager);
            XSSFRow row = sheet.getRow(i);
            XSSFCell cell;
            cell = row.getCell(0);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            //题干
            question.setDescription(cell.getStringCellValue());
            //答案
            cell = row.getCell(1);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            question.setSolution(cell.getStringCellValue());
            //标签
            cell = row.getCell(2);
            cell.setCellType(Cell.CELL_TYPE_STRING);
            question.setLabel(cell.getStringCellValue());
            //option
            cell = row.getCell(3);
            if (cell!= null && cell.getStringCellValue() != null){
                Map<String, String> options = new HashMap<String, String>();
                for (int j = 3; j < 4;j++){
                    cell = row.getCell(3);
                    cell.setCellType(Cell.CELL_TYPE_STRING);
                    Character ch  = (char) (62+j);
                    options.put(ch.toString(),cell.getStringCellValue());
                }
                question.setOptionJson(JsonType.simpleMapToJsonStr(options));
            }
            question.setAlive(1);
            questions.add(question);
            question = questionDao.save(question);
            System.out.println(question.getId()+"============");
        }
        //questionDao.save(questions);
        return questionDao.save(questions).size();
    }
}
